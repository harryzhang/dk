/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.account.dataobject
 * @Title: InviteCode.java
 * @Description: TODO
 *
 * @author: chenzhpmf
 * @date 2015-3-30 下午8:57:33
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.common.account.dataobject;

import java.io.Serializable;

public class InviteCodeDo implements Serializable{

	private static final long serialVersionUID = 7840669966917839051L;
	
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 渠道标识
	 */
	private Integer channel;
	/**
	 * 邀请码
	 */
	private String code;
	/**
	 * 用户id
	 */
	private Long userId;
	
	public InviteCodeDo() {
		super();
	}
	
	public InviteCodeDo(Long userId) {
		super();
		this.userId = userId;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getChannel() {
		return channel;
	}
	public void setChannel(Integer channel) {
		this.channel = channel;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
