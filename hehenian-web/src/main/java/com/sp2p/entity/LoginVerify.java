package com.sp2p.entity;

import java.io.Serializable;

public class LoginVerify implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int jbStatus; //基本认证信息
	private int allworkjbStatus;//工作认证信息 包括 4 个状态
	private int identyVerifyStatus;//身份认证
	private int workVerifyStatus;//工作认证
	private int addressVerifyStatus;//居住地认证
	private int responseVerifyStatus;//信用报告
	private int incomeVerifyStatus;//收入认证
	private int fangchanVerifyStatus;//房产认证
	private int gcVerifyStatus;//购车
	private int jhVerifyStatus;//结婚
	private int xlVerifyStatus;//学历
	private int jsVerifyStatus;//技术
	private int sjVerifyStatus;//手机
	private int wbVerifyStatus;//微博
	private int spVerifyStatus;//视频
	private int xcVerifyStatus;//现场
	private int dyVerifyStatus;//抵押
	private int dbVerifyStatus;//担保
	
	public int getJbStatus() {
		return jbStatus;
	}
	public void setJbStatus(int jbStatus) {
		this.jbStatus = jbStatus;
	}
	public int getAllworkjbStatus() {
		return allworkjbStatus;
	}
	public void setAllworkjbStatus(int allworkjbStatus) {
		this.allworkjbStatus = allworkjbStatus;
	}
	public int getIdentyVerifyStatus() {
		return identyVerifyStatus;
	}
	public void setIdentyVerifyStatus(int identyVerifyStatus) {
		this.identyVerifyStatus = identyVerifyStatus;
	}
	public int getWorkVerifyStatus() {
		return workVerifyStatus;
	}
	public void setWorkVerifyStatus(int workVerifyStatus) {
		this.workVerifyStatus = workVerifyStatus;
	}
	public int getAddressVerifyStatus() {
		return addressVerifyStatus;
	}
	public void setAddressVerifyStatus(int addressVerifyStatus) {
		this.addressVerifyStatus = addressVerifyStatus;
	}
	public int getResponseVerifyStatus() {
		return responseVerifyStatus;
	}
	public void setResponseVerifyStatus(int responseVerifyStatus) {
		this.responseVerifyStatus = responseVerifyStatus;
	}
	public int getIncomeVerifyStatus() {
		return incomeVerifyStatus;
	}
	public void setIncomeVerifyStatus(int incomeVerifyStatus) {
		this.incomeVerifyStatus = incomeVerifyStatus;
	}
	public int getFangchanVerifyStatus() {
		return fangchanVerifyStatus;
	}
	public void setFangchanVerifyStatus(int fangchanVerifyStatus) {
		this.fangchanVerifyStatus = fangchanVerifyStatus;
	}
	public int getGcVerifyStatus() {
		return gcVerifyStatus;
	}
	public void setGcVerifyStatus(int gcVerifyStatus) {
		this.gcVerifyStatus = gcVerifyStatus;
	}
	public int getJhVerifyStatus() {
		return jhVerifyStatus;
	}
	public void setJhVerifyStatus(int jhVerifyStatus) {
		this.jhVerifyStatus = jhVerifyStatus;
	}
	public int getXlVerifyStatus() {
		return xlVerifyStatus;
	}
	public void setXlVerifyStatus(int xlVerifyStatus) {
		this.xlVerifyStatus = xlVerifyStatus;
	}
	public int getJsVerifyStatus() {
		return jsVerifyStatus;
	}
	public void setJsVerifyStatus(int jsVerifyStatus) {
		this.jsVerifyStatus = jsVerifyStatus;
	}
	public int getSjVerifyStatus() {
		return sjVerifyStatus;
	}
	public void setSjVerifyStatus(int sjVerifyStatus) {
		this.sjVerifyStatus = sjVerifyStatus;
	}
	public int getWbVerifyStatus() {
		return wbVerifyStatus;
	}
	public void setWbVerifyStatus(int wbVerifyStatus) {
		this.wbVerifyStatus = wbVerifyStatus;
	}
	public int getSpVerifyStatus() {
		return spVerifyStatus;
	}
	public void setSpVerifyStatus(int spVerifyStatus) {
		this.spVerifyStatus = spVerifyStatus;
	}
	public int getXcVerifyStatus() {
		return xcVerifyStatus;
	}
	public void setXcVerifyStatus(int xcVerifyStatus) {
		this.xcVerifyStatus = xcVerifyStatus;
	}
	public int getDyVerifyStatus() {
		return dyVerifyStatus;
	}
	public void setDyVerifyStatus(int dyVerifyStatus) {
		this.dyVerifyStatus = dyVerifyStatus;
	}
	public int getDbVerifyStatus() {
		return dbVerifyStatus;
	}
	public void setDbVerifyStatus(int dbVerifyStatus) {
		this.dbVerifyStatus = dbVerifyStatus;
	}
	

}
