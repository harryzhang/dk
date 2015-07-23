/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.wygj.dataobject
 * @Title: OffsetRecordsDo.java
 * @Description: TODO
 *
 * @author: jiangwmf
 * @date 2015-5-6 下午4:43:03
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.common.wygj.dataobject;

import java.io.Serializable;

public class OffsetRecordsDo  implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * '冲抵地址、车辆信息ID'
	 */
	private Integer id;
	/**
	 * '用户ID'
	 */
	private Integer user_id;
	/**
	 * '交易流水ID'
	 */
	private Integer trade_id;
	/**
	 * '费用'
	 */
	private Double fee;
	/**
	 * 冲抵交易类型：0，冲抵物业费；1冲抵停车费'
	 */
	private Integer infotype;
	/**
	 * '冲抵开始日期'
	 */
	private String begindate;
	/**
	 * '冲抵结束日期'
	 */
	private String enddate;
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
	public Integer getInfotype() {
		return infotype;
	}
	public void setInfotype(Integer infotype) {
		this.infotype = infotype;
	}
	public String getBegindate() {
		return begindate;
	}
	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	
	
}
