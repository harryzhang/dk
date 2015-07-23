package com.hehenian.biz.common.loan.dataobject;

import java.io.Serializable;

/**
 * 
 * @author wangt
 *@author liminglmf
 */
public class LoanProvidedDo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long prodId;
	private Integer minAge;
	private Integer maxAge;
	private Integer minIncome;//最低收入(税前)单位(元)
	private String ifpunch;//工资是否打卡(0,否 1,是，默认1)
	private String job;//工作岗位
	private Integer minYearWork; //最低工作年限(单位：月)
	private String workAddr;//工作地址
	private String livAddr;//居住地址
	private String status; //锛圱鏈夋晥  F 鏃犳晥锛�
	private String remark; //备注
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getProdId() {
		return prodId;
	}
	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}
	public Integer getMinAge() {
		return minAge;
	}
	public void setMinAge(Integer minAge) {
		this.minAge = minAge;
	}
	public Integer getMaxAge() {
		return maxAge;
	}
	public void setMaxAge(Integer maxAge) {
		this.maxAge = maxAge;
	}
	public Integer getMinIncome() {
		return minIncome;
	}
	public void setMinIncome(Integer minIncome) {
		this.minIncome = minIncome;
	}
	public String getIfpunch() {
		return ifpunch;
	}
	public void setIfpunch(String ifpunch) {
		this.ifpunch = ifpunch;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public Integer getMinYearWork() {
		return minYearWork;
	}
	public void setMinYearWork(Integer minYearWork) {
		this.minYearWork = minYearWork;
	}
	public String getWorkAddr() {
		return workAddr;
	}
	public void setWorkAddr(String workAddr) {
		this.workAddr = workAddr;
	}
	public String getLivAddr() {
		return livAddr;
	}
	public void setLivAddr(String livAddr) {
		this.livAddr = livAddr;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
