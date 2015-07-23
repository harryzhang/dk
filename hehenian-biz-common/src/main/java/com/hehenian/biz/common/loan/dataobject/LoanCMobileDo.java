package com.hehenian.biz.common.loan.dataobject;

import java.io.Serializable;
import java.util.Date;

public class LoanCMobileDo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int num ;

	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	private		Integer 	id ;			//id
	private 	Long		cid;			//小区ID
	private 	String 		cname;			//小区名称
	private 	Long		cuserId; 		//小区管理员ID
	private 	String		cusername;		//小区管理员名称
	private 	String 		cmobile;		//小区管理员联系电话
	private 	String		status;			//状态：T-正常，F-失效
	private 	Date		createDate;		
	private 	Date		lastUpdateDate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public Long getCuserId() {
		return cuserId;
	}
	public void setCuserId(Long cuserId) {
		this.cuserId = cuserId;
	}
	public String getCusername() {
		return cusername;
	}
	public void setCusername(String cusername) {
		this.cusername = cusername;
	}
	public String getCmobile() {
		return cmobile;
	}
	public void setCmobile(String cmobile) {
		this.cmobile = cmobile;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	
	
}
