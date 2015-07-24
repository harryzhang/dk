/**
 * @fileName LoanCallbackTask.java
 * @auther liminglmf
 * @createDate 2015年6月9日
 */
package com.hehenian.lend.manager.service.pdf;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.loan.ILoanContractService;
import com.hehenian.biz.common.loan.ILoanLogService;
import com.hehenian.biz.common.loan.IManagerLoanRepaymentService;
import com.hehenian.biz.common.loan.IManagerLoanService;
import com.hehenian.biz.common.loan.dataobject.ConsultVO;
import com.hehenian.biz.common.loan.dataobject.ContactVO;
import com.hehenian.biz.common.loan.dataobject.LoanDo;
import com.hehenian.biz.common.loan.dataobject.LoanLogDo;
import com.hehenian.biz.common.loan.dataobject.LoanRepaymentDo;
import com.hehenian.biz.common.task.ConsultCreateTask;
import com.hehenian.biz.common.task.ContactCreateTask;
import com.hehenian.biz.common.util.CommonReqUtil;
import com.hehenian.biz.common.util.DateUtils;
import com.hehenian.biz.common.util.constant.ConstantText;

/**
 * 取放款和还款计划的数据
 * 
 * @author liminglmf
 * 
 */
@Component
public class LoanContractServiceImpl implements ILoanContractService{

	private Logger logger = Logger.getLogger(LoanContractServiceImpl.class);
	private static SimpleDateFormat toDate = new SimpleDateFormat("yyyy/MM/dd");
	private static SimpleDateFormat toDate1 = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private IManagerLoanRepaymentService managerLoanRepaymentService;

	@Autowired
	private IManagerLoanService managerLoanService;

	@Autowired
	private ILoanLogService loanLogService;
	
	@Autowired
	@Qualifier("taskExecutor")
	private org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor executor;
	

	@Value("#{sysconfig['loan.fk.url']}")
	private String loanFkUrl;

	@Value("#{sysconfig['manager.genPdfUrl']}")
	private String genPdfUrl;

	@Value("#{sysconfig['manager.genPdfFilePath']}")
	private String savePath;


	
	/**
	 * 
	 * @param returnJsVal
	 * @return
	 */
	private JSONArray getLoanList(String returnJsVal){
		JSONArray loanList = null;
		if (StringUtils.isNotBlank(returnJsVal)) {
		
			JSONObject data = JSONObject.fromObject(returnJsVal);
			if ("true".equals(data.get("success").toString())) {
				loanList = (JSONArray) data.get("loanList");
				String sign = data.get("sign").toString();
				String checkSign = DigestUtils.md5Hex(ConstantText.MDSIGNKEY + loanList + ConstantText.MDSIGNKEY);
				if (!(checkSign.equals(sign))) {
					LoanLogDo log = new LoanLogDo();
					log.setLogType(3);
					log.setLogMsg("数据验证失败！");
					log.setLogStatus(1);
					loanLogService.addLoanLog(log);
				}
			}
			
		}
		return loanList;
	}
	
	/**
	 * 调用服务获取每天的放款数据： 更新订单状态，生成还款计划，生成电子合约
	 * @param date "yyyy/MM/dd" 格式的日期参数
	 */
	@Override
	public void fankuanProcess(String date) {
		
		//call http service and return js string
		String pushDataStr = CommonReqUtil.pushServiceData(loanFkUrl,"loanDate", date);
		
		//获取json格式的订单列表
		JSONArray loanList =  getLoanList(pushDataStr);
		
		this.initRepay(loanList);
		
	}

	/**
	 * 生产合同
	 * 
	 * @param realName
	 * @param idNo
	 * @param orderCode
	 * @param loanAmount
	 * @param repayType
	 * @param yearRate
	 * @param loanPeriod
	 * @param loanTime
	 * @param loanUsage
	 * @param loanId
	 * @param createTime
	 * @throws ParseException
	 */
	private void genContactPDF(String realName, String idNo, String orderCode,
			String loanAmount, String repayType, String yearRate,
			String loanPeriod, String loanTime, String loanUsage, Long loanId,
			Date createTime) throws ParseException {

		ContactVO contactVO = new ContactVO();
		String url = genPdfUrl;
		contactVO.setUrl(url);
		contactVO.setReqTmplate("Concat");
		contactVO.setRealName(realName);
		contactVO.setIdNo(idNo);
		contactVO.setCmd("customSave");
		// contactVO.setUserId();
		contactVO.setOrderCode(orderCode);
		contactVO.setSavePath(savePath);
		// contactVO.setLendUserName(lendUserName);
		// contactVO.setLendIdNo(lendIdNo);
		contactVO.setBorrowerName(realName);
		contactVO.setBorrowerIdNo(idNo);
		contactVO.setLoanAmount(loanAmount);
		contactVO.setRepayType(repayType);
		contactVO.setYearRate(yearRate + "%");
		contactVO.setLoanPeriod(loanPeriod);
		contactVO.setLoanDay(loanTime);
		Date repDate = toDate.parse(loanTime);
		String repayDay = DateUtils.getEveryTime("yyyy/MM/dd", repDate, -1);
		contactVO.setRepayDay(repayDay);
		Date limitDate = toDate.parse(repayDay);
		String limitTime = toDate.format(DateUtils.getMonthAfter(limitDate,
				Integer.parseInt(loanPeriod)));
		contactVO.setLimitTime(limitTime);

		// contactVO.setAccount(account);
		contactVO.setLoanUsage(loanUsage);
		contactVO.setFilePrefix("RJK" + orderCode);
		contactVO.setProcedure("每月");
		contactVO.setScale("0.2");
		contactVO.setAdvance("2");
		contactVO.setLoanId(loanId);

		contactVO.setCreateDate(toDate.format(createTime));
		this.generateContactPdf(contactVO);
	}

	/**
	 * 生产服务协议
	 * 
	 * @param realName
	 * @param idNo
	 * @param loanId
	 * @param loanTime
	 * @param mobile
	 * @param orderCode
	 * @param caddress
	 * @param createTime
	 * @param string
	 */
	private void genConsultPDF(String realName, String idNo, Long loanId,
			String loanTime, String mobile, String orderCode, String caddress,
			Date createTime, String yearRate) {

		ConsultVO consultVO = new ConsultVO();
		String url = genPdfUrl;
		consultVO.setUrl(url);
		consultVO.setRealName(realName);
		consultVO.setIdNo(idNo);
		consultVO.setCmd("customSave");
		consultVO.setLoanId(loanId);
		// consultVO.setUserId(userId);
		consultVO.setReqTmplate("Consult");
		consultVO.setSavePath(savePath);
		consultVO.setLoanDay(loanTime);
		consultVO.setMobile(mobile);
		// consultVO.setAddress(address);
		// consultVO.setCompAddr(compAddr);
		consultVO.setFilePrefix("RFW" + orderCode);
		consultVO.setPenalty("3");
		consultVO.setCredit("0");
		// consultVO.setMobile(loanDo.getMobile());
		consultVO.setAddress(caddress);
		consultVO.setCreateDate(toDate.format(createTime));
		consultVO.setRate(yearRate);
		this.generateConsultPdf(consultVO);

	}

	private void initRepay(JSONArray loanList){
		
		if(loanList != null && loanList.size()>0){
			for(int i=0;i<loanList.size();i++){
				
				
				logger.info("一共"+loanList.size()+"放款数据返回");
				JSONObject loan = (JSONObject)loanList.get(i);
				try{
					
					String orderCode = loan.getString("businessNo");//订单号
					String idNo = loan.getString("idNo");//借款人身份证号码
					//String bankNo = loan.getString("bankNo");//银行编码
					//String bankName = loan.getString("bankName");//银行名称
					//String platform = loan.getString("platform");//放款渠道
					String loanAmount = loan.getString("borrowAmount");//放款金额
					String realName = loan.getString("realName");//借款人名称
					String loanPeriod = loan.getString("loanPeriod");//借款期限
					String loanTime = loan.getString("loanDate");//放款日期
					
					String channel = loan.getString("channel");//放款渠道 1汇付   2通联
					String investAnnualRate = loan.getString("annualRate");//投资年利率
					Map<String,Object> param = new HashMap<String,Object>();
					if(StringUtils.isNotBlank(orderCode)){
						param.put("orderCode", orderCode);
					}
					if(StringUtils.isNotBlank(idNo)){
						param.put("idNo", idNo);
					}
					if(StringUtils.isNotBlank(realName)){
						param.put("realName", realName);
					}
					logger.info("放款回调查询条件："+param);
					LoanDo loanDo = managerLoanService.getLoanforUpdate(param);
					logger.info("放款查询订单结果："+loanDo==null? "没有找到对应订单":loanDo);
					//找到订单
					if(loanDo != null){
						if(loanTime != null){
							loanDo.setLoanTime(toDate.parse(loanTime));
						}
						loanDo.setInvestAnnualRate(investAnnualRate == null ? 0.00 : Double.parseDouble(investAnnualRate));
						loanDo.setLoanAmount(loanAmount == null ? 0.00 : Double.parseDouble(loanAmount));
						loanDo.setLoanStatus(loanDo.getLoanStatus().REPAYING);
						if(StringUtils.isNotBlank(channel)){
							loanDo.setSubjectType(Integer.parseInt(channel));
						}
						//更新放款金额和订单状态
						managerLoanService.updateLoan(loanDo);
						
						//生成合约
						genContactPDF(realName,idNo,orderCode,loanAmount,loanDo.getSchemeName(),
								      loanDo.getAnnualRate().toString(),
								      loanPeriod,loanTime,loanDo.getLoanUsage(),
								      loanDo.getLoanId(),loanDo.getCreateTime());
						//生产服务协议
						genConsultPDF(realName,idNo,loanDo.getLoanId(),
								      loanTime,loanDo.getMobile(),
								      orderCode,loanDo.getCaddress(),
								      loanDo.getCreateTime(),loanDo.getAnnualRate().toString());
						//写还款计划
						insertRepaymentData(loanDo, loan.getJSONArray("repayment"));
					}
				}catch(Exception e){
					logger.error("处理放款数据出错:"+loan);
					logger.error(e);
				}
			}
				
		}
	}

	/**
	 * 写入还款计划
	 * 
	 * @param loanDo
	 * @param object
	 * @throws ParseException
	 */
	private void insertRepaymentData(LoanDo loanDo, JSONArray repayJsonObj)
			throws ParseException {
		// {"consultFee":"50.00","repayTime":"1","repayAmount":"250.00","capital":"0.00","servFee":"0.00","principalBalance":"20000.00",
		// "repaymentId":"208786","borrowId":"112","interest":"200.00","settleSum":"20650.0000","creditFee":"0","repayDate":"2015-08-01",
		// "gatherRate":"0.0"}
		if (null == repayJsonObj) {
			logger.error("放款的时候没有拿到还款计划 loanId=" + loanDo.getLoanId());
			return;
		}
		
		//先删除之前的还款计划
		managerLoanRepaymentService.deleteRepaymentByLoanId(loanDo.getLoanId());
		
		for (int i = 0; i < repayJsonObj.size(); i++) {
			JSONObject jsonRepay = repayJsonObj.getJSONObject(i);
			double consultFee = jsonRepay.getDouble("consultFee");
			int repayTime = jsonRepay.getInt("repayTime");
			double repayAmount = jsonRepay.getDouble("repayAmount");
			double capital = jsonRepay.getDouble("capital");
			double servFee = jsonRepay.getDouble("servFee");
			double principalBalance = jsonRepay.getDouble("principalBalance");
			Long repaymentId = jsonRepay.getLong("repaymentId");
			Long borrowId = jsonRepay.getLong("borrowId");
			double interest = jsonRepay.getDouble("interest");
			double settleSum = jsonRepay.getDouble("settleSum");
			double creditFee = jsonRepay.getDouble("creditFee");
			String repayDate = jsonRepay.getString("repayDate");
			double gatherRate = jsonRepay.getDouble("gatherRate");

			Date currentDate = new Date();
			LoanRepaymentDo newLoanRepaymentDo = new LoanRepaymentDo();
			newLoanRepaymentDo.setCreateDate(currentDate);
			// newLoanRepaymentDo.setInterestBalance(interestBalance);
			newLoanRepaymentDo.setLastUpdateDate(currentDate);
			// newLoanRepaymentDo.setLateDay(lateDay);
			// newLoanRepaymentDo.setLateFi(lateFi);
			newLoanRepaymentDo.setLoanId(loanDo.getLoanId());
			newLoanRepaymentDo.setOrderCode(loanDo.getOrderCode());
			newLoanRepaymentDo.setPrincipalBalance(principalBalance);
			newLoanRepaymentDo.setRepayDate(toDate1.parse(repayDate));
			newLoanRepaymentDo.setRepaymentId(repaymentId);
			newLoanRepaymentDo.setRepayPeriod(repayTime);
			newLoanRepaymentDo.setRepayStatus(0);
			// newLoanRepaymentDo.setRepayStyle(repayStyle);
			newLoanRepaymentDo.setStillInterest(interest);
			newLoanRepaymentDo.setStillPrincipal(capital);
			newLoanRepaymentDo.setStillRepayAll(repayAmount);
			
			managerLoanRepaymentService.addLoanRepayment(newLoanRepaymentDo);

		}
	}
	

	public void generateContactPdf(ContactVO contactVO) {
		Thread t = executor.createThread(new ContactCreateTask(contactVO));
		executor.execute(t, 5000);
		//executor.execute(new ContactCreateTask(contactVO));
	}
	
	public void generateConsultPdf(ConsultVO consultVO ) {
		Thread t = executor.createThread(new ConsultCreateTask(consultVO));
		executor.execute(t, 5000);
		//executor.execute(new ConsultCreateTask(consultVO));
	}
	

}
