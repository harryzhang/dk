/**
 * Project Name:hehenian-manager
 * File Name:BaseModel.java
 * Package Name:com.hehenian.manager.modules.basicdata
 * Date:2015年5月5日上午8:52:42
 * Copyright (c) 2015, hehenian.com All Rights Reserved.
 *
*/

package com.hehenian.manager.modules.basicdata;

import java.util.Date;

/**
 * 
 * @author   songxjmf
 * @date: 2015年5月5日 上午8:52:42 	 
 */
public class BaseModel {
	private Long id;
	private Long createUserId;
	private Long updateUserId;
	private Date createTime;
	private Date updateTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	public Long getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}

