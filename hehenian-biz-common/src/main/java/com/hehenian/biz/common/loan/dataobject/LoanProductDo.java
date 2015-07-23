package com.hehenian.biz.common.loan.dataobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author wangt
 *@author liminglmf
 */
public class LoanProductDo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;//产品名称
	private String code;   //产品类型：对应字典明细表-产品类别编码产品编码（(D00)、集团贷(D01)、工薪贷(D02)、E贷款(D03)、青春贷(D04)、农村贷(D05)、首付贷（D06））
	private Double minLines; //最低额度
	private Double maxLines;//最高额度
	private String loanIssue;//贷款期限类型-数据字典明细表取得
	private String guarantee;//是否需要担保(0,不需要,1需要,默认0)
	private String mortgage;//是否需要抵押(0,不需要,1需要,默认0)
	private String retaLock;//利率锁定(0,是 1,否，默认0)
	private String status; //锛圱鏈夋晥  F 鏃犳晥锛�
	private String publishCode;//渠道编码-对应渠道编码表
	private String refeCode;//推荐奖励-对应字典明细表
	private String remark; //
	
	public static final String HBJH="D00";
	public static final String JTD= "D01";
	public static final String GXD= "D02";
	public static final String EDK= "D03";
	public static final String QCD= "D04";
	public static final String NCD= "D05";
	public static final String SFD= "D06";
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Double getMinLines() {
		return minLines;
	}
	public void setMinLines(Double minLines) {
		this.minLines = minLines;
	}
	public Double getMaxLines() {
		return maxLines;
	}
	public void setMaxLines(Double maxLines) {
		this.maxLines = maxLines;
	}
	public String getLoanIssue() {
		return loanIssue;
	}
	public void setLoanIssue(String loanIssue) {
		this.loanIssue = loanIssue;
	}
	public String getGuarantee() {
		return guarantee;
	}
	public void setGuarantee(String guarantee) {
		this.guarantee = guarantee;
	}
	public String getMortgage() {
		return mortgage;
	}
	public void setMortgage(String mortgage) {
		this.mortgage = mortgage;
	}
	public String getRetaLock() {
		return retaLock;
	}
	public void setRetaLock(String retaLock) {
		this.retaLock = retaLock;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPublishCode() {
		return publishCode;
	}
	public void setPublishCode(String publishCode) {
		this.publishCode = publishCode;
	}
	public String getRefeCode() {
		return refeCode;
	}
	public void setRefeCode(String refeCode) {
		this.refeCode = refeCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
