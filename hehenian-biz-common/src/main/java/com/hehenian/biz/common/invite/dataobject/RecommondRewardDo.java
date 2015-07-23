package com.hehenian.biz.common.invite.dataobject;

import java.io.Serializable;
import java.util.Date;

public class RecommondRewardDo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer referee_id;//推荐人ID
	private String referee_name;//推荐人用户名
	private Integer user_id;//用户ID
	private String user_name;//用户名
	private String user_identity;//用户身份证号码
	private Integer trade_id;//'交易流水ID'
	private Date trade_time;//投资时间
	private Double trade_amount;//投资金额
	private Integer invest_period;//投资期数
	private Double reward_amount;//奖励金额
	private Integer status;//'是否审核：0，未审核；1已审核', 
	

	
	
	public Date getTrade_time() {
		return trade_time;
	}
	public void setTrade_time(Date trade_time) {
		this.trade_time = trade_time;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getReferee_id() {
		return referee_id;
	}
	public void setReferee_id(Integer referee_id) {
		this.referee_id = referee_id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getTrade_id() {
		return trade_id;
	}
	public void setTrade_id(Integer trade_id) {
		this.trade_id = trade_id;
	}
	
	public Double getTrade_amount() {
		return trade_amount;
	}
	public void setTrade_amount(Double trade_amount) {
		this.trade_amount = trade_amount;
	}
	public Integer getInvest_period() {
		return invest_period;
	}
	public void setInvest_period(Integer invest_period) {
		this.invest_period = invest_period;
	}
	public Double getReward_amount() {
		return reward_amount;
	}
	public void setReward_amount(Double reward_amount) {
		this.reward_amount = reward_amount;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getReferee_name() {
		return referee_name;
	}
	public void setReferee_name(String referee_name) {
		this.referee_name = referee_name;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_identity() {
		return user_identity;
	}
	public void setUser_identity(String user_identity) {
		this.user_identity = user_identity;
	}

	
}
