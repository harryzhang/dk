/*package com.hehenian.lend.manager.time.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.loan.ICommonService;
import com.hehenian.biz.common.loan.ILoanLogService;
import com.hehenian.biz.common.loan.IManagerLoanService;
import com.hehenian.biz.common.loan.dataobject.ContactVO;
import com.hehenian.biz.common.loan.dataobject.LoanDo;
import com.hehenian.biz.common.loan.dataobject.LoanLogDo;
import com.hehenian.biz.common.loan.dataobject.LoanPersonDo;
import com.hehenian.biz.common.system.ISettSchemeService;
import com.hehenian.biz.common.system.dataobject.SettSchemeDo;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.common.util.DateUtils;
import com.hehenian.biz.common.util.HttpClientUtils;

*//*
@Component
public class InitRepayPlanTask {
	
	private Logger logger = Logger.getLogger(InitRepayPlanTask.class);

	@Autowired
	private IManagerLoanService managerLoanService ;
	
	@Autowired
	private ILoanLogService loanLogService;
	
	@Autowired
	private ICommonService commonService;
	
	@Autowired
	private ISettSchemeService settSchemeService;
	
	@Value("#{sysconfig['loan.fk.url']}")
	private String loanFkUrl ;
	
	//@Scheduled(cron="0 0 22 * * ?")
	public void doJob(){
		logger.debug("生成还款计划表任务进行中。。。。。。。。。。。。");
		String key = "HHN&XD#$%CD%des$" ;
		String pushDataStr = pushData();
		if(StringUtils.isNotBlank(pushDataStr)){
			try {
				JSONObject data = JSONObject.fromObject(pushDataStr); 
				if("true".equals(data.get("success").toString())){
					JSONArray loanList = (JSONArray)data.get("loanList");
					String sign = data.get("sign").toString();
					String checkSign = DigestUtils.md5Hex(key+loanList.toString()+key);
					if(!(checkSign.equals(sign))){
						LoanLogDo log = new LoanLogDo();
						log.setLogType(2);
						log.setLogMsg("数据验证失败！");
						log.setLogStatus(1);
						loanLogService.addLoanLog(log);
					}else{
						initRepay(loanList);
					}
				//	initRepay(loanList);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				LoanLogDo log = new LoanLogDo();
				log.setLogType(2);
				log.setLogMsg("拉取数据生成还款计划表异常！");
				log.setLogStatus(1);
				loanLogService.addLoanLog(log);
			}
		}
	}
	
	
	private String pushData(){
		JSONObject data = new JSONObject();
		data.put("success",true);
		data.put("sign", "");
		JSONArray loanArr = new JSONArray();
		JSONObject loandata = new JSONObject();
		loandata.put("realName", "何鲁丽");
		loandata.put("idNo", "430621198809171818");
		loandata.put("loanDate", "2015/04/30");
		loandata.put("borrowAmount", "100000.00");
		loandata.put("loanPeriod", "10");
		loandata.put("businessNo", "D031505060001");
		loandata.put("platform", "合和年在线");
		loandata.put("annualRate", "19.20");
		loanArr.add(loandata);
		data.put("loanList", loanArr);
		
		String pushDataStr = null;
		try {
			pushDataStr = data.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return pushDataStr;
		
		String loanDate = DateUtils.getCurrentDateAsStringByBackSlant();
	//	loanDate="2015/05/12";
		String key = "HHN&XD#$%CD%des$" ;
		String sign = DigestUtils.md5Hex(key+loanDate+key);
		StringBuffer url = new StringBuffer(loanFkUrl+"?loanDate=");
		url.append(loanDate);
		url.append("&sign=").append(sign);
		System.out.println("====================================");
		System.out.println(url.toString());
		System.out.println("====================================");
		String res = null;
		try {
			res = HttpClientUtils.get(url.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res ;
	}
	
	private boolean checkData(JSONObject loan,Map<String,LoanPersonDo> map){
		String orderCode = null;
		try {
			orderCode = loan.get("businessNo").toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(StringUtils.isNotBlank(orderCode)){
			LoanLogDo log = new LoanLogDo();
			log.setLogType(2);
			log.setOrderCode(orderCode);
			try {
				LoanPersonDo loanPersonDo = managerLoanService.getLoanInfoForFkByOrderCode(orderCode);
				if(loanPersonDo == null){
					log.setLogMsg("生成订单还款计划表失败,订单信息不存在!");
					log.setLogStatus(1);
					loanLogService.addLoanLog(log);
					return false;
				}
				if(!(LoanDo.LoanStatus.SUBJECTED.toString().equals(loanPersonDo.getLoanDo().getLoanStatus().toString()))){
					log.setLogMsg("生成订单还款计划表失败,订单不是上标状态!");
					log.setLogStatus(1);
					loanLogService.addLoanLog(log);
					return false;
				}
				String realName = loan.get("realName").toString();
				String idNo = loan.get("idNo").toString();
				double borrowAmount = Double.valueOf(loan.get("borrowAmount").toString());
				int loanPeriod = Integer.parseInt(loan.get("loanPeriod").toString());
				double annualRate = Double.valueOf(loan.get("annualRate").toString());
				Date loanDate = DateUtils.parseDateOrNull(loan.get("loanDate").toString(), DateUtils.Format_Date_back_slant);
				int channel = Integer.parseInt(loan.get("channel").toString());
				loanPersonDo.setChannel(channel);
				if(!(realName.equals(loanPersonDo.getRealName()))){
					log.setLogMsg("生成订单还款计划表失败,订单用户姓名不一致!db["+loanPersonDo.getRealName()+"],pushData["+realName+"]");
					log.setLogStatus(1);
					loanLogService.addLoanLog(log);
					return false ;
				}
				if(!(idNo.equals(loanPersonDo.getIdNo()))){
					log.setLogMsg("生成订单还款计划表失败,订单用户身份证号码不一致!db["+loanPersonDo.getIdNo()+"],pushData["+idNo+"]");
					log.setLogStatus(1);
					loanLogService.addLoanLog(log);
					return false ;
				}
				if(!CalculateUtils.eq(CalculateUtils.round(borrowAmount, 2),CalculateUtils.round(loanPersonDo.getLoanDo().getApplyAmount(), 2))){
					log.setLogMsg("生成订单还款计划表失败,订单金额不一致!db["+loanPersonDo.getLoanDo().getApplyAmount()+"],pushData["+borrowAmount+"]");
					log.setLogStatus(1);
					loanLogService.addLoanLog(log);
					return false ;
				}
				if(loanPeriod != loanPersonDo.getLoanDo().getLoanPeriod()){
					log.setLogMsg("生成订单还款计划表失败,订单借款期限不一致!db["+loanPersonDo.getLoanDo().getLoanPeriod()+"],pushData["+loanPeriod+"]");
					log.setLogStatus(1);
					loanLogService.addLoanLog(log);
					return false ;
				}
				if(CalculateUtils.le(annualRate, 0d)){
					log.setLogMsg("生成订单还款计划表失败,投资年利率异常! annualRate["+annualRate+"]");
					log.setLogStatus(1);
					loanLogService.addLoanLog(log);
					return false ;
				}
				if(loanDate == null){
					log.setLogMsg("生成订单还款计划表失败,放款日期异常! loanDate["+loan.get("loanDate").toString()+"]");
					log.setLogStatus(1);
					loanLogService.addLoanLog(log);
					return false ;
				}
				loanPersonDo.getLoanDo().setOrderCode(orderCode);
				loanPersonDo.getLoanDo().setLoanAmount(borrowAmount);
				loanPersonDo.getLoanDo().setLoanTime(loanDate);
				loanPersonDo.getLoanDo().setInvestAnnualRate(annualRate);
				loanPersonDo.getLoanDo().setLoanStatus(LoanDo.LoanStatus.REPAYING);
				map.put("loanPersonDo", loanPersonDo);
				return true ;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				log.setLogMsg("生成订单还款计划表失败,订单参数异常! pushData["+loan.toString()+"]");
				log.setLogStatus(1);
				loanLogService.addLoanLog(log);
			}
			return false ;
		}
		return false ;
	}
	
	private void initRepay(JSONArray loanList) throws Exception{
		System.out.println("===============================");
		System.out.println(loanList.toString());
		System.out.println("===============================");
		if(loanList != null && loanList.size()>0){
			Map<String,LoanPersonDo> map = new HashMap<String,LoanPersonDo>();
				for(int i=0;i<loanList.size();i++){
					JSONObject loan = (JSONObject)loanList.get(i);
					if(checkData(loan,map)){
						LoanPersonDo loanPersonDo = map.get("loanPersonDo");
						LoanDo loanDo = loanPersonDo.getLoanDo();
						LoanLogDo initLog = new LoanLogDo();
						initLog.setLogType(2);
						initLog.setLoanId(loanDo.getLoanId());
						initLog.setOrderCode(loanDo.getOrderCode());
						boolean res = managerLoanService.initRepayPlan(loanDo);
						if(res){
							initLog.setLogMsg("生成订单还款计划表成功....");
							initLog.setLogStatus(0);
						}else{
							initLog.setLogMsg("生成订单还款计划表失败....");
							initLog.setLogStatus(1);
						}
						loanLogService.addLoanLog(initLog);
					}
				}
		}
	} 
	
	public void initProtocol(LoanPersonDo loanPersonDo){
		ContactVO cv = new ContactVO();
		cv.setUrl("");
		cv.setRealName(loanPersonDo.getRealName());
		cv.setIdNo(loanPersonDo.getIdNo());
		cv.setCmd("save");
		cv.setOrderCode(loanPersonDo.getLoanDo().getOrderCode());
		
		List<Map<String,Object>> list = null;
		if(loanPersonDo.getChannel() == 1){
			list = managerLoanService.getSbNameForHF(loanPersonDo.getLoanDo().getOrderCode());
		}
		if(loanPersonDo.getChannel() == 2){
			list = managerLoanService.getSbNameForTL(loanPersonDo.getLoanDo().getOrderCode());
		}
		StringBuffer lendUserName = new StringBuffer("");
		StringBuffer lendIdNo = new StringBuffer("");
		if(list != null && list.size()>0){
			for(Map<String,Object> m:list){
				if(m.get("realName") != null){
					lendUserName.append(String.valueOf(m.get("realName"))).append(",");
				}
				if(m.get("userId") != null){
					lendUserName.append(String.valueOf(m.get("userId"))).append(",");
				}
			}
		}
		
		if(lendUserName.length()>1){
			lendUserName.setLength(lendUserName.length()-1);
		}
		if(lendIdNo.length()>1){
			lendIdNo.setLength(lendIdNo.length()-1);
		}
		cv.setLendUserName(lendUserName.toString());
		cv.setLendIdNo(lendIdNo.toString());;
		
		cv.setBorrowerName(loanPersonDo.getRealName());
		cv.setBorrowerIdNo(loanPersonDo.getIdNo());
		
		cv.setLoanAmount(String.valueOf(loanPersonDo.getLoanDo().getLoanAmount()));
		SettSchemeDo ssDo = settSchemeService.getBySchemeId(loanPersonDo.getLoanDo().getSchemeId());
		cv.setRepayType(ssDo.getSchemeName());
		cv.setYearRate(loanPersonDo.getLoanDo().getAnnualRate()+"%");
		cv.setLoanPeriod(String.valueOf(loanPersonDo.getLoanDo().getLoanPeriod()));
		cv.setLoanDay(DateUtils.formatDate(loanPersonDo.getLoanDo().getLoanTime(),DateUtils.Format_Date));
		cv.setRepayDay(String.valueOf(DateUtils.getDayOfMonth(loanPersonDo.getLoanDo().getLoanTime())));
		Date limitDate = DateUtils.getMonthAfter(loanPersonDo.getLoanDo().getLoanTime(),loanPersonDo.getLoanDo().getLoanPeriod());
		cv.setLimitTime(DateUtils.formatDate(limitDate,DateUtils.Format_Date));
		
		Map<String,Object> accMap = null ;
		if(loanPersonDo.getChannel() == 1){
			accMap = managerLoanService.getBankAccountForHF(loanPersonDo.getIdNo());
		}
		if(loanPersonDo.getChannel() == 2){
			accMap = managerLoanService.getBankAccountForTL(loanPersonDo.getIdNo());
		}
		if(accMap != null && accMap.get("cardNo") != null){
			cv.setAccount(accMap.get("cardNo").toString());
		}else{
			cv.setAccount("");
		}
		
		cv.setLoanUsage(loanPersonDo.getLoanDo().getLoanUsage());
		
		commonService.generateContactPdf(cv);
	}
	
	public static void main(String args[]){
		String loanDate = DateUtils.getCurrentDateAsStringByBackSlant();
		String key = "HHN&XD#$%CD%des$" ;
		String sign = DigestUtils.md5Hex(key+loanDate+key);
		StringBuffer url = new StringBuffer("http://192.168.16.193//loan.do?loanDate=");
		url.append("2015/05/05");
		url.append("&sign=").append(sign);
		System.out.println("====================================");
		System.out.println(url.toString());
		System.out.println("====================================");
		String res = null;
		try {
			res = HttpClientUtils.get(url.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(res);
	}
}
*/