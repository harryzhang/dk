/**
 * 
 */
package com.hehenian.biz.common.account.dataobject;

import java.io.Serializable;

/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.account.dataobject
 * @Title: WorkAuth
 * @Description: 工作信息认证实体
 * @author: chenzhpmf
 * @date 2015年4月28日
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
public class WorkAuth implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long 	id;               // 编号
	private String 	orgName;          // 单位名称
	private String 	occStatus;        // 职业状态
	private int 	workPro;          // 工作城市省份(默认-1)
	private int 	workCity;         // 工作城市(默认-1)
	private String 	companyType;      // 公司类别
	private String 	companyLine;      // 公司行业
	private String 	companyScale;     // 公司规模
	private String 	job;              // 职位
	private String 	monthlyIncome;    // 月收入
	private String 	monthlyOutcome;   // 月支出
	private String 	workYear;         // 现单位工作年限
	private String 	companyTel;       // 公司电话
	private String 	workEmail;        // 工作邮箱
	private String 	companyAddress;   // 公司地址
	private String 	directedName;     // 直系人姓名              
	private String 	directedRelation; // 直系人关系             
	private String 	directedTel;      // 直系人电话             
	private String 	directedIdNo;     // 直系人身份证            
	private String 	directedAddress;  // 直系人居住地址            
	private String 	otherName;        // 其他人姓名              
	private String 	otherRelation;    // 其他人关系             
	private String 	otherTel;         // 其他人电话             
	private String 	otherIdNo;        // 其他  人身份证       
	private String 	otherAddress;     // 其他 人居住地址
	private String 	moredName;        // 另外人姓名              
	private String 	moredRelation;    // 另外人关系             
	private String 	moredTel;         // 另外人电话             
	private long 	userId;           // 用户id             
	private int 	auditStatus;      // 工作信息认证状态(1 默认审核中或等待审核 2 审核不通过 3 审核通过)
	private int 	directedStatus;   // 联系信息认证1默认等待审核2审核不通过3审核通过
	private int 	otherStatus;      // 其他人联系审核状态1默认等待审核2审核不通过3审核通过
	private int 	moredStatus;      // 联系审核状态1默认等待审核2审核不通过3审核通过
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	/**
	 * @return the occStatus
	 */
	public String getOccStatus() {
		return occStatus;
	}
	/**
	 * @param occStatus the occStatus to set
	 */
	public void setOccStatus(String occStatus) {
		this.occStatus = occStatus;
	}
	/**
	 * @return the workPro
	 */
	public int getWorkPro() {
		return workPro;
	}
	/**
	 * @param workPro the workPro to set
	 */
	public void setWorkPro(int workPro) {
		this.workPro = workPro;
	}
	/**
	 * @return the workCity
	 */
	public int getWorkCity() {
		return workCity;
	}
	/**
	 * @param workCity the workCity to set
	 */
	public void setWorkCity(int workCity) {
		this.workCity = workCity;
	}
	/**
	 * @return the companyType
	 */
	public String getCompanyType() {
		return companyType;
	}
	/**
	 * @param companyType the companyType to set
	 */
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
	/**
	 * @return the companyLine
	 */
	public String getCompanyLine() {
		return companyLine;
	}
	/**
	 * @param companyLine the companyLine to set
	 */
	public void setCompanyLine(String companyLine) {
		this.companyLine = companyLine;
	}
	/**
	 * @return the companyScale
	 */
	public String getCompanyScale() {
		return companyScale;
	}
	/**
	 * @param companyScale the companyScale to set
	 */
	public void setCompanyScale(String companyScale) {
		this.companyScale = companyScale;
	}
	/**
	 * @return the job
	 */
	public String getJob() {
		return job;
	}
	/**
	 * @param job the job to set
	 */
	public void setJob(String job) {
		this.job = job;
	}
	/**
	 * @return the monthlyIncome
	 */
	public String getMonthlyIncome() {
		return monthlyIncome;
	}
	/**
	 * @param monthlyIncome the monthlyIncome to set
	 */
	public void setMonthlyIncome(String monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}
	/**
	 * @return the monthlyOutcome
	 */
	public String getMonthlyOutcome() {
		return monthlyOutcome;
	}
	/**
	 * @param monthlyOutcome the monthlyOutcome to set
	 */
	public void setMonthlyOutcome(String monthlyOutcome) {
		this.monthlyOutcome = monthlyOutcome;
	}
	/**
	 * @return the workYear
	 */
	public String getWorkYear() {
		return workYear;
	}
	/**
	 * @param workYear the workYear to set
	 */
	public void setWorkYear(String workYear) {
		this.workYear = workYear;
	}
	/**
	 * @return the companyTel
	 */
	public String getCompanyTel() {
		return companyTel;
	}
	/**
	 * @param companyTel the companyTel to set
	 */
	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}
	/**
	 * @return the workEmail
	 */
	public String getWorkEmail() {
		return workEmail;
	}
	/**
	 * @param workEmail the workEmail to set
	 */
	public void setWorkEmail(String workEmail) {
		this.workEmail = workEmail;
	}
	/**
	 * @return the companyAddress
	 */
	public String getCompanyAddress() {
		return companyAddress;
	}
	/**
	 * @param companyAddress the companyAddress to set
	 */
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	/**
	 * @return the directedName
	 */
	public String getDirectedName() {
		return directedName;
	}
	/**
	 * @param directedName the directedName to set
	 */
	public void setDirectedName(String directedName) {
		this.directedName = directedName;
	}
	/**
	 * @return the directedRelation
	 */
	public String getDirectedRelation() {
		return directedRelation;
	}
	/**
	 * @param directedRelation the directedRelation to set
	 */
	public void setDirectedRelation(String directedRelation) {
		this.directedRelation = directedRelation;
	}
	/**
	 * @return the directedTel
	 */
	public String getDirectedTel() {
		return directedTel;
	}
	/**
	 * @param directedTel the directedTel to set
	 */
	public void setDirectedTel(String directedTel) {
		this.directedTel = directedTel;
	}
	/**
	 * @return the directedIdNo
	 */
	public String getDirectedIdNo() {
		return directedIdNo;
	}
	/**
	 * @param directedIdNo the directedIdNo to set
	 */
	public void setDirectedIdNo(String directedIdNo) {
		this.directedIdNo = directedIdNo;
	}
	/**
	 * @return the directedAddress
	 */
	public String getDirectedAddress() {
		return directedAddress;
	}
	/**
	 * @param directedAddress the directedAddress to set
	 */
	public void setDirectedAddress(String directedAddress) {
		this.directedAddress = directedAddress;
	}
	/**
	 * @return the otherName
	 */
	public String getOtherName() {
		return otherName;
	}
	/**
	 * @param otherName the otherName to set
	 */
	public void setOtherName(String otherName) {
		this.otherName = otherName;
	}
	/**
	 * @return the otherRelation
	 */
	public String getOtherRelation() {
		return otherRelation;
	}
	/**
	 * @param otherRelation the otherRelation to set
	 */
	public void setOtherRelation(String otherRelation) {
		this.otherRelation = otherRelation;
	}
	/**
	 * @return the otherTel
	 */
	public String getOtherTel() {
		return otherTel;
	}
	/**
	 * @param otherTel the otherTel to set
	 */
	public void setOtherTel(String otherTel) {
		this.otherTel = otherTel;
	}
	/**
	 * @return the otherIdNo
	 */
	public String getOtherIdNo() {
		return otherIdNo;
	}
	/**
	 * @param otherIdNo the otherIdNo to set
	 */
	public void setOtherIdNo(String otherIdNo) {
		this.otherIdNo = otherIdNo;
	}
	/**
	 * @return the otherAddress
	 */
	public String getOtherAddress() {
		return otherAddress;
	}
	/**
	 * @param otherAddress the otherAddress to set
	 */
	public void setOtherAddress(String otherAddress) {
		this.otherAddress = otherAddress;
	}
	/**
	 * @return the moredName
	 */
	public String getMoredName() {
		return moredName;
	}
	/**
	 * @param moredName the moredName to set
	 */
	public void setMoredName(String moredName) {
		this.moredName = moredName;
	}
	/**
	 * @return the moredRelation
	 */
	public String getMoredRelation() {
		return moredRelation;
	}
	/**
	 * @param moredRelation the moredRelation to set
	 */
	public void setMoredRelation(String moredRelation) {
		this.moredRelation = moredRelation;
	}
	/**
	 * @return the moredTel
	 */
	public String getMoredTel() {
		return moredTel;
	}
	/**
	 * @param moredTel the moredTel to set
	 */
	public void setMoredTel(String moredTel) {
		this.moredTel = moredTel;
	}
	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}
	/**
	 * @return the auditStatus
	 */
	public int getAuditStatus() {
		return auditStatus;
	}
	/**
	 * @param auditStatus the auditStatus to set
	 */
	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}
	/**
	 * @return the directedStatus
	 */
	public int getDirectedStatus() {
		return directedStatus;
	}
	/**
	 * @param directedStatus the directedStatus to set
	 */
	public void setDirectedStatus(int directedStatus) {
		this.directedStatus = directedStatus;
	}
	/**
	 * @return the otherStatus
	 */
	public int getOtherStatus() {
		return otherStatus;
	}
	/**
	 * @param otherStatus the otherStatus to set
	 */
	public void setOtherStatus(int otherStatus) {
		this.otherStatus = otherStatus;
	}
	/**
	 * @return the moredStatus
	 */
	public int getMoredStatus() {
		return moredStatus;
	}
	/**
	 * @param moredStatus the moredStatus to set
	 */
	public void setMoredStatus(int moredStatus) {
		this.moredStatus = moredStatus;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WorkAuth [id=" + id + ", orgName=" + orgName + ", occStatus=" + occStatus + ", workPro=" + workPro
				+ ", workCity=" + workCity + ", companyType=" + companyType + ", companyLine=" + companyLine + ", companyScale="
				+ companyScale + ", job=" + job + ", monthlyIncome=" + monthlyIncome + ", monthlyOutcome=" + monthlyOutcome
				+ ", workYear=" + workYear + ", companyTel=" + companyTel + ", workEmail=" + workEmail + ", companyAddress="
				+ companyAddress + ", directedName=" + directedName + ", directedRelation=" + directedRelation + ", directedTel="
				+ directedTel + ", directedIdNo=" + directedIdNo + ", directedAddress=" + directedAddress + ", otherName="
				+ otherName + ", otherRelation=" + otherRelation + ", otherTel=" + otherTel + ", otherIdNo=" + otherIdNo
				+ ", otherAddress=" + otherAddress + ", moredName=" + moredName + ", moredRelation=" + moredRelation
				+ ", moredTel=" + moredTel + ", userId=" + userId + ", auditStatus=" + auditStatus + ", directedStatus="
				+ directedStatus + ", otherStatus=" + otherStatus + ", moredStatus=" + moredStatus + "]";
	}
}
