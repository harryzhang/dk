package com.hehenian.biz.common.loan.dataobject;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class ContactVO implements Serializable{
    //必填
	private String url; //url  properties中获取这个属性  hehenian-agreement-web  
	//必填
	private String realName;
	
	private String idNo;
	//必填  save 
	private String cmd;//save  or  get
	
	private String userId;//可以不填
	
	private String reqTmplate;//可以不填
    //必填
	private String orderCode;//
	
	private String savePath;//文件保存路径
	
	/**甲方（出借人信息）**/
	private String lendUserName;
	private String lendIdNo;
	 
	/**乙方（借款人信息）**/
	private String borrowerName;//借款人姓名
	private String borrowerIdNo;//借款人身份证号
	
	/****借款基本信息*****/
	private String loanAmount;//借款本金
	private String repayType;//还款方式
	private String yearRate;//年利率
	private String loanPeriod;//借款期限
	private String loanDay;//放款日
	private String repayDay;//还款日
	private String limitTime;//最终到期日
	private String account;//体现账号
	private String loanUsage;//借款用途
	
	/*liminglong add 20150626 */
	private String filePrefix;//协议号 规则前缀加订单号
	
	private String procedure;//手续费收取方式 （一次性/每月）
	private String scale; //计算比例     0.2   百分比
	private String advance;//借款 几个月内不能还款  2
	
	private String createDate;
	
	private Long loanId;

	public ContactVO(String url, String realName, String idNo, String cmd,
			String userId, String reqTmplate, String orderCode,
			String savePath, String lendUserName, String lendIdNo,
			String borrowerName, String borrowerIdNo, String loanAmount,
			String repayType, String yearRate, String loanPeriod,
			String loanDay, String repayDay, String limitTime, String account,
			String loanUsage, String filePrefix, String procedure,
			String scale, String advance, String createDate, Long loanId) {
		super();
		this.url = url;
		this.realName = realName;
		this.idNo = idNo;
		this.cmd = cmd;
		this.userId = userId;
		this.reqTmplate = reqTmplate;
		this.orderCode = orderCode;
		this.savePath = savePath;
		this.lendUserName = lendUserName;
		this.lendIdNo = lendIdNo;
		this.borrowerName = borrowerName;
		this.borrowerIdNo = borrowerIdNo;
		this.loanAmount = loanAmount;
		this.repayType = repayType;
		this.yearRate = yearRate;
		this.loanPeriod = loanPeriod;
		this.loanDay = loanDay;
		this.repayDay = repayDay;
		this.limitTime = limitTime;
		this.account = account;
		this.loanUsage = loanUsage;
		this.filePrefix = filePrefix;
		this.procedure = procedure;
		this.scale = scale;
		this.advance = advance;
		this.createDate = createDate;
		this.loanId = loanId;
	}

	public ContactVO() {
		super();
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public String getUserId() {
		if(StringUtils.isBlank(userId)){
			return "1";
		}
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getRepayType() {
		return repayType;
	}

	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}

	public String getYearRate() {
		return yearRate;
	}

	public void setYearRate(String yearRate) {
		this.yearRate = yearRate;
	}

	public String getLoanDay() {
		return loanDay;
	}

	public void setLoanDay(String loanDay) {
		this.loanDay = loanDay;
	}

	public String getRepayDay() {
		return repayDay;
	}

	public void setRepayDay(String repayDay) {
		this.repayDay = repayDay;
	}

	public String getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(String limitTime) {
		this.limitTime = limitTime;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getLoanUsage() {
		return loanUsage;
	}

	public void setLoanUsage(String loanUsage) {
		this.loanUsage = loanUsage;
	}

	public String getLendUserName() {
		return lendUserName;
	}

	public void setLendUserName(String lendUserName) {
		this.lendUserName = lendUserName;
	}

	public String getLendIdNo() {
		return lendIdNo;
	}

	public void setLendIdNo(String lendIdNo) {
		this.lendIdNo = lendIdNo;
	}

	public String getBorrowerName() {
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	public String getBorrowerIdNo() {
		return borrowerIdNo;
	}

	public void setBorrowerIdNo(String borrowerIdNo) {
		this.borrowerIdNo = borrowerIdNo;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	

	public String getLoanPeriod() {
		return loanPeriod;
	}

	public void setLoanPeriod(String loanPeriod) {
		this.loanPeriod = loanPeriod;
	}

	public String getCmd() {
		return cmd;
	}
	/**
	 * save get customSave
	 * @auther liminglmf
	 * @date 2015年6月30日
	 * @param cmd
	 */
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getReqTmplate() {
		if(StringUtils.isBlank(reqTmplate)){
			return "Concat";
		}
		return reqTmplate;
	}

	public void setReqTmplate(String reqTmplate) {
		this.reqTmplate = reqTmplate;
	}

	public String getFilePrefix() {
		return filePrefix;
	}

	public void setFilePrefix(String filePrefix) {
		this.filePrefix = filePrefix;
	}

	public String getProcedure() {
		return procedure;
	}

	public void setProcedure(String procedure) {
		this.procedure = procedure;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getAdvance() {
		return advance;
	}

	public void setAdvance(String advance) {
		this.advance = advance;
	}
	
	
	
}
