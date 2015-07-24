package com.hehenian.lend.manager.time.task;

import java.util.ArrayList;
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

import com.hehenian.biz.common.loan.ILoanLogService;
import com.hehenian.biz.common.loan.IManagerLoanRepaymentService;
import com.hehenian.biz.common.loan.IManagerLoanService;
import com.hehenian.biz.common.loan.dataobject.LoanFeeRuleDo;
import com.hehenian.biz.common.loan.dataobject.LoanLogDo;
import com.hehenian.biz.common.loan.dataobject.LoanPersonDo;
import com.hehenian.biz.common.loan.dataobject.LoanRepaymentDo;
import com.hehenian.biz.common.loan.dataobject.LoanRepaymentFeeDo;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.common.util.DateUtils;
import com.hehenian.biz.common.util.HttpClientUtils;

@Component
public class RepayTask {
	
	private Logger logger = Logger.getLogger(RepayTask.class);
	
	@Autowired
	private IManagerLoanService managerLoanService ;
	
	@Autowired
	private IManagerLoanRepaymentService managerLoanRepaymentService ;
	
	@Autowired
	private ILoanLogService loanLogService;
	
	@Value("#{sysconfig['loan.hk.url']}")
	private String loanHkUrl ;

	@Scheduled(cron="0 0 22 * * ?")
	public void doJob(){
		logger.info("repay Task do job..................");
		String key = "HHN&XD#$%CD%des$" ;
		String pushDataStr = pushData();
		if(StringUtils.isNotBlank(pushDataStr)){
			try {
				JSONObject data = JSONObject.fromObject(pushDataStr);
				if("true".equals(data.get("success").toString())){
					JSONArray repaymentList = (JSONArray)data.get("repaymentList");
					String sign = data.get("sign").toString();
					String checkSign = DigestUtils.md5Hex(key+repaymentList.toString()+key);
					if(!(checkSign.equals(sign))){
						LoanLogDo log = new LoanLogDo();
						log.setLogType(3);
						log.setLogMsg("数据验证失败！");
						log.setLogStatus(1);
						loanLogService.addLoanLog(log);
					}else{
						initRepay(repaymentList);
					}
				//	initRepay(repaymentList);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
	
	@SuppressWarnings("unchecked")
	private void initRepay(JSONArray repaymentList) throws Exception{
		if(repaymentList != null && repaymentList.size()>0){
			Map<String,Object> map = new HashMap<String,Object>();
			for(int i=0;i<repaymentList.size();i++){
				JSONObject repayData = (JSONObject)repaymentList.get(i);
				if(checkData(repayData,map)){
					Map<String,Object> repaymentParam = (Map<String,Object>)map.get("repaymentParam");
					List<LoanRepaymentFeeDo> lrfList = (List<LoanRepaymentFeeDo>)map.get("lrfList");
					boolean res = managerLoanRepaymentService.repaymentTask(repaymentParam, lrfList);
					LoanLogDo rpLog = new LoanLogDo();
					rpLog.setLogType(3);							   
					rpLog.setLoanId(Long.parseLong(repaymentParam.get("loanId").toString()));
					rpLog.setOrderCode(repaymentParam.get("orderCode").toString());
					if(res){
						rpLog.setLogMsg("插入还款记录成功....");
						rpLog.setLogStatus(0);
					}else{
						rpLog.setLogMsg("插入还款记录失败....");
						rpLog.setLogStatus(0);
					}
					loanLogService.addLoanLog(rpLog);
				}
			}
		}
	}
	
	private boolean checkData(JSONObject repayData,Map<String,Object> map){
		String orderCode = null;
		try {
			orderCode = repayData.get("businessNo").toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(StringUtils.isNotBlank(orderCode)){
			List<LoanRepaymentFeeDo> lrfList = new ArrayList<LoanRepaymentFeeDo>();
			LoanLogDo log = new LoanLogDo();
			log.setLogType(3);
			log.setOrderCode(orderCode);
			try {
				LoanPersonDo loanPersonDo = managerLoanService.getLoanInfoForFkByOrderCode(orderCode);
				if(loanPersonDo == null){
					log.setLogMsg("添加还款信息失败,订单信息不存在!");
					log.setLogStatus(1);
					loanLogService.addLoanLog(log);
					return false;
				}
				String realName = repayData.getString("realName");
				if(!(realName.equals(loanPersonDo.getRealName()))){
					log.setLogMsg("添加还款信息失败,订单用户姓名不一致!db["+loanPersonDo.getRealName()+"],pushData["+realName+"]");
					log.setLogStatus(1);
					loanLogService.addLoanLog(log);
					return false ;
				}
				String idNo = repayData.getString("idNo");
				if(!(idNo.equals(loanPersonDo.getIdNo()))){
					log.setLogMsg("添加还款信息失败,订单用户身份证号码不一致!db["+loanPersonDo.getIdNo()+"],pushData["+idNo+"]");
					log.setLogStatus(1);
					loanLogService.addLoanLog(log);
					return false ;
				}
				Map<String,Object> repaymentParam = new HashMap<String,Object>();
				repaymentParam.put("loanId", loanPersonDo.getLoanDo().getLoanId());
				repaymentParam.put("orderCode", orderCode);
				int loanPeriod = Integer.parseInt(repayData.getString("loanPeriod"));
				repaymentParam.put("repayPeriod", loanPeriod);
				Map<String,Object> lrdParams  = new HashMap<String,Object>();
				lrdParams.put("orderCode", orderCode);
				lrdParams.put("repayPeriod", loanPeriod);
				LoanRepaymentDo lrd = managerLoanRepaymentService.selectRepaymentByOrderCodeAndPeriod(lrdParams);
				if(lrd.getRepayStatus().intValue() == 1){
					log.setLogMsg("订单已还款!!");
					log.setLogStatus(1);
					loanLogService.addLoanLog(log);
					return false;
				}
				Date repayDate = DateUtils.parseDateOrNull(repayData.getString("repayDate"), DateUtils.Format_Date_back_slant);
				repaymentParam.put("realRepayDate", repayDate);
				double principalAmt = Double.parseDouble(repayData.getString("principalAmt")); //本金
				repaymentParam.put("realPrincipal", principalAmt);
				double iterestAmt = Double.parseDouble(repayData.getString("iterestAmt")); //利息
				double consultFeeAmt = Double.parseDouble(repayData.getString("consultFeeAmt")); //管理费
				LoanRepaymentFeeDo consultFeeAmtLrfDo = new LoanRepaymentFeeDo();
				consultFeeAmtLrfDo.setLoanId(loanPersonDo.getLoanId());
				consultFeeAmtLrfDo.setOrderCode(orderCode);
				consultFeeAmtLrfDo.setRepayType(1);
				consultFeeAmtLrfDo.setFeeType("");
				consultFeeAmtLrfDo.setFeeName("管理费");
				consultFeeAmtLrfDo.setFeeAmount(consultFeeAmt);
				lrfList.add(consultFeeAmtLrfDo);
		//		double repayAmt = Double.parseDouble(repayData.getString("repayAmt")); //应还款总额
		//		double preSettleAmt = Double.parseDouble(repayData.getString("preSettleAmt")); //提前还款额度
		//		double remainPrincipalAmt = Double.parseDouble(repayData.getString("remainPrincipalAmt")); //剩余本金
				double lateFeeAmt = Double.parseDouble(repayData.getString("lateFeeAmt"));//滞纳金
				repaymentParam.put("realFI", lateFeeAmt);
				int lateDay = Integer.parseInt(repayData.getString("lateDay"));//逾期天数
				repaymentParam.put("lateDay", lateDay);
				int repayStatus = Integer.parseInt(repayData.getString("repayStatus"));//本期是否还完 还款状态（0 默认未偿还 1 已偿还,2-部分还款）
				repaymentParam.put("repayStatus", repayStatus);
				double repayFee = Double.parseDouble(repayData.getString("repayFee"));//手续费
				if(CalculateUtils.gt(repayFee,0d)){
					LoanRepaymentFeeDo repayFeeLrfDo = new LoanRepaymentFeeDo();
					repayFeeLrfDo.setLoanId(loanPersonDo.getLoanId());
					repayFeeLrfDo.setOrderCode(orderCode);
					repayFeeLrfDo.setRepayType(1);
					repayFeeLrfDo.setFeeType(LoanFeeRuleDo.feeType.REPAY_FEE.toString());
					repayFeeLrfDo.setFeeName("手续费");
					repayFeeLrfDo.setFeeAmount(repayFee);
					lrfList.add(repayFeeLrfDo);
				}
				double servFeeAmt = Double.parseDouble(repayData.getString("servFeeAmt"));//服务费
				if(CalculateUtils.gt(servFeeAmt,0d)){
					LoanRepaymentFeeDo servFeeAmtLrfDo = new LoanRepaymentFeeDo();
					servFeeAmtLrfDo.setLoanId(loanPersonDo.getLoanId());
					servFeeAmtLrfDo.setOrderCode(orderCode);
					servFeeAmtLrfDo.setRepayType(1);
					servFeeAmtLrfDo.setFeeType(LoanFeeRuleDo.feeType.SERV_FEE.toString());
					servFeeAmtLrfDo.setFeeName("服务费");
					servFeeAmtLrfDo.setFeeAmount(servFeeAmt);
					lrfList.add(servFeeAmtLrfDo);
				}
				double creditFeeAmt = Double.parseDouble(repayData.getString("creditFeeAmt"));//征信费
				if(CalculateUtils.gt(creditFeeAmt,0d)){
					LoanRepaymentFeeDo creditFeeAmtLrfDo = new LoanRepaymentFeeDo();
					creditFeeAmtLrfDo.setLoanId(loanPersonDo.getLoanId());
					creditFeeAmtLrfDo.setOrderCode(orderCode);
					creditFeeAmtLrfDo.setRepayType(1);
					creditFeeAmtLrfDo.setFeeType(LoanFeeRuleDo.feeType.CREDIT_FEE.toString());
					creditFeeAmtLrfDo.setFeeName("征信费");
					creditFeeAmtLrfDo.setFeeAmount(creditFeeAmt);
					lrfList.add(creditFeeAmtLrfDo);
				}
				double parkingFeeAmt = Double.parseDouble(repayData.getString("parkingFeeAmt"));//停车费
				if(CalculateUtils.gt(parkingFeeAmt,0d)){
					LoanRepaymentFeeDo parkingFeeAmtLrfDo = new LoanRepaymentFeeDo();
					parkingFeeAmtLrfDo.setLoanId(loanPersonDo.getLoanId());
					parkingFeeAmtLrfDo.setOrderCode(orderCode);
					parkingFeeAmtLrfDo.setRepayType(1);
					parkingFeeAmtLrfDo.setFeeType(LoanFeeRuleDo.feeType.PARKING_FEE.toString());
					parkingFeeAmtLrfDo.setFeeName("停车费");
					parkingFeeAmtLrfDo.setFeeAmount(parkingFeeAmt);
					lrfList.add(parkingFeeAmtLrfDo);
				}
				double regFeeAmt = Double.parseDouble(repayData.getString("regFeeAmt"));//登记费
				if(CalculateUtils.gt(regFeeAmt,0d)){
					LoanRepaymentFeeDo regFeeAmtLrfDo = new LoanRepaymentFeeDo();
					regFeeAmtLrfDo.setLoanId(loanPersonDo.getLoanId());
					regFeeAmtLrfDo.setOrderCode(orderCode);
					regFeeAmtLrfDo.setRepayType(1);
					regFeeAmtLrfDo.setFeeType(LoanFeeRuleDo.feeType.REG_FEE.toString());
					regFeeAmtLrfDo.setFeeName("登记费");
					regFeeAmtLrfDo.setFeeAmount(regFeeAmt);
					lrfList.add(regFeeAmtLrfDo);
				}
				double preSettleFeeAmt = Double.parseDouble(repayData.getString("preSettleFeeAmt"));//提前结清手续费
				if(CalculateUtils.gt(preSettleFeeAmt,0d)){
					LoanRepaymentFeeDo preSettleFeeAmtDo = new LoanRepaymentFeeDo();
					preSettleFeeAmtDo.setLoanId(loanPersonDo.getLoanId());
					preSettleFeeAmtDo.setOrderCode(orderCode);
					preSettleFeeAmtDo.setRepayType(1);
					preSettleFeeAmtDo.setFeeType(LoanFeeRuleDo.feeType.SETTLE_FEE.toString());
					preSettleFeeAmtDo.setFeeName("提前结清手续费");
					preSettleFeeAmtDo.setFeeAmount(preSettleFeeAmt);
					lrfList.add(preSettleFeeAmtDo);
				}
				double realRepayAmt = Double.parseDouble(repayData.getString("realRepayAmt"));//实际还款总额
				repaymentParam.put("realRepayAll", realRepayAmt);
				repaymentParam.put("realInterest", CalculateUtils.add(iterestAmt,CalculateUtils.add(consultFeeAmt
						, CalculateUtils.add(repayFee, CalculateUtils.add(servFeeAmt
						, CalculateUtils.add(creditFeeAmt, CalculateUtils.add(parkingFeeAmt,regFeeAmt)))))));
				
				map.put("repaymentParam", repaymentParam);
				map.put("lrfList", lrfList);
				return true ;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				log.setLogMsg("添加还款信息失败,订单参数异常! pushData["+repayData.toString()+"]");
				log.setLogStatus(1);
				loanLogService.addLoanLog(log);
			}
		}
		return false ;
	}
	
	/**
	 * 发送还款请求， 参数当前日期
	 * @return
	 */
	private String pushData(){
		/*
		JSONObject data = new JSONObject();
		data.put("success",true);
		data.put("sign", "");
		JSONArray repayArr = new JSONArray();
		JSONObject repayData = new JSONObject();
		repayData.put("realName", "何鲁丽"); //姓名
		repayData.put("idNo", "430621198809171818"); //身份证号码
		repayData.put("businessNo", "D031505060001"); //业务编号
		repayData.put("loanPeriod", "5"); //还款期数
		repayData.put("repayDate", "2015/05/08"); //还款日期
		repayData.put("principalAmt", "1666.67"); //本金
		repayData.put("iterestAmt", "213.89"); //利息
		repayData.put("consultFeeAmt", "476.11"); //管理费
		repayData.put("repayAmt", "2356.67"); //应还款总额
		repayData.put("preSettleAmt", "0"); //提前还款额度
		repayData.put("remainPrincipalAmt", "23333.32"); //剩余本金
		repayData.put("lateFeeAmt", "0.00"); //滞纳金
		repayData.put("lateDay", "0"); //逾期天数
		repayData.put("repayStatus", "1"); //本期是否还完
		repayData.put("repayFee", "0.00"); //手续费
		repayData.put("servFeeAmt", "0"); //服务费
		repayData.put("creditFeeAmt", "0"); //征信费
		repayData.put("parkingFeeAmt", "0");//停车费
		repayData.put("regFeeAmt", "0"); //登记费
		repayData.put("preSettleFeeAmt", "0"); //提前结清手续费
		repayData.put("realRepayAmt", "2356.67"); //实际还款总额
		repayArr.add(repayData);
		data.put("repaymentList", repayArr);
		
		String pushDataStr = null;
		try {
			pushDataStr = data.toString();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return pushDataStr ;
		*/
		
		String repayDate = DateUtils.getCurrentDateAsStringByBackSlant();
	//	repayDate="2015/05/12";
		String key = "HHN&XD#$%CD%des$" ;
		String sign = DigestUtils.md5Hex(key+repayDate+key);
		StringBuffer url = new StringBuffer(loanHkUrl+"?repayDate=");
		url.append(repayDate);
		url.append("&sign=").append(sign);
		logger.info("=============还款借款URL=======================");
		logger.info(url.toString());
		logger.info("=============还款借款URL=======================");
		String res = null;
		try {
			res = HttpClientUtils.get(url.toString());
		} catch (Exception e) {
			logger.error(e);
		}
		return res;
	}
	
	public static void main(String args[]){
		String repayDate = DateUtils.getCurrentDateAsStringByBackSlant();
		String key = "HHN&XD#$%CD%des$" ;
		String sign = DigestUtils.md5Hex(key+repayDate+key);
		StringBuffer url = new StringBuffer("http://192.168.16.193//repayment.do?repayDate=");
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
