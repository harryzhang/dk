package com.hehenian.biz.common.loan.dataobject;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class ConsultVO implements Serializable{
    //必填
	private String url; //url  properties中获取这个属性  hehenian-agreement-web  
	//必填
	private String realName;
	
	private String idNo;
	//必填  save 
	private String cmd;//save  or  get
	
	private String userId;//可以不填
		
	private String reqTmplate;//可以不填

	private String savePath;//文件保存路径
 
	private String loanDay;//放款日
	
	private String address;//地址、住所
	
	private String compAddr;//单位地址
	
	private String mobile;
	
	private String filePrefix;//协议号 规则前缀加订单号
	
	private String penalty;//违约金比例
	
	private String credit;//征信费
	
	private String rate;//每月咨询费
	
	private Long loanId;
	
	private String createDate;
	
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

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
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

	public String getReqTmplate() {
		if(StringUtils.isBlank(reqTmplate)){
			return "Consult";
		}
		return reqTmplate;
	}

	public void setReqTmplate(String reqTmplate) {
		this.reqTmplate = reqTmplate;
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

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getLoanDay() {
		return loanDay;
	}

	public void setLoanDay(String loanDay) {
		this.loanDay = loanDay;
	}

	public String getFilePrefix() {
		return filePrefix;
	}

	public void setFilePrefix(String filePrefix) {
		this.filePrefix = filePrefix;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompAddr() {
		return compAddr;
	}

	public void setCompAddr(String compAddr) {
		this.compAddr = compAddr;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPenalty() {
		return penalty;
	}

	public void setPenalty(String penalty) {
		this.penalty = penalty;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}
	
	
	
}
