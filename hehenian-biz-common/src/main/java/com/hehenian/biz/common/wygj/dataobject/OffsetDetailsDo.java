/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.wygj.dataobject
 * @Title: OffsetDetailsDo.java
 * @Description: TODO
 *
 * @author: jiangwmf
 * @date 2015-5-6 下午4:43:03
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.common.wygj.dataobject;

import java.io.Serializable;

public class OffsetDetailsDo  implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 'ID'
	 */
	private Integer id;
	/**
	 * '交易流水ID'
	 */
	private Integer trade_id;
	/**
	 * '冲抵金额'
	 */
	private Double fee;
	/**
	 * '冲抵日期'
	 */
	private String offsetdate;
	/**
	 * '期数'
	 */
	private String timeframe;
	/**
	 * '冲抵状态：0，未冲抵；1已冲抵'
	 */
	private Integer infostatus;
	/**
	*'是否有效：0，有效；1无效'
	*/
	private Integer isvalid;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTrade_id() {
		return trade_id;
	}
	public void setTrade_id(Integer trade_id) {
		this.trade_id = trade_id;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	public String getOffsetdate() {
		return offsetdate;
	}
	public void setOffsetdate(String offsetdate) {
		this.offsetdate = offsetdate;
	}
	public String getTimeframe() {
		return timeframe;
	}
	public void setTimeframe(String timeframe) {
		this.timeframe = timeframe;
	}
	public Integer getInfostatus() {
		return infostatus;
	}
	public void setInfostatus(Integer infostatus) {
		this.infostatus = infostatus;
	}
	public Integer getIsvalid() {
		return isvalid;
	}
	public void setIsvalid(Integer isvalid) {
		this.isvalid = isvalid;
	}
	
	
}
