/**
 * Project Name:hehenian-manager
 * File Name:SysCode.java
 * Package Name:com.hehenian.manager.modules.basicdata.model
 * Date:2015年5月5日上午9:30:16
 * Copyright (c) 2015, hehenian.com All Rights Reserved.
 *
*/

package com.hehenian.manager.modules.basicdata.model;

import com.hehenian.manager.modules.basicdata.BaseModel;

/**
 * 
 * @author   songxjmf
 * @date: 2015年5月5日 上午9:30:16 	 
 */
public class SysCode extends BaseModel {
	
	/**
	 * 是否有效-有效
	 */
	public final static int INVALID_OFF = 0;
	/**
	 * 是否有效-无效
	 */
	public final static int INVALID_ON = 1;
	
	/**
	 * 类型id
	 */
	private Integer typeId;
	/**
	 * 父typeId
	 */
    private Integer parentTypeId;
    /**
     * 父id
     */
    private Long parentId;
    /**
     * 描述
     */
    private String remark;
    /**
     * 用于展示的描述
     */
    private String remarkForShow;
    /**
     * 是否有效
     */
    private Integer invalid;
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public Integer getParentTypeId() {
		return parentTypeId;
	}
	public void setParentTypeId(Integer parentTypeId) {
		this.parentTypeId = parentTypeId;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRemarkForShow() {
		return remarkForShow;
	}
	public void setRemarkForShow(String remarkForShow) {
		this.remarkForShow = remarkForShow;
	}
	public Integer getInvalid() {
		return invalid;
	}
	public void setInvalid(Integer invalid) {
		this.invalid = invalid;
	}
}

