/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2015
 */

package com.hehenian.biz.common.account.dataobject;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * 菜单项
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

public class MenuDo  implements java.io.Serializable{	
	
	//columns START
	private java.lang.Long id;
	private java.lang.String name;
	private java.lang.String action;
	private java.lang.String description;
	private java.lang.Integer isLog;
	private java.lang.Integer isIntercept;
	private java.lang.String summary;
	private java.lang.Long parentId;
	private java.lang.Integer isQuery;
	private java.lang.Integer indexs;
	private java.lang.Long menuId;
	//columns END
	
	//子菜单
	private List<MenuDo> childMenuList;
	
	
	public void addChildMenu(MenuDo childMenu){
		if(null == childMenuList){
			childMenuList = new ArrayList<MenuDo>();
		}
		this.childMenuList.add(childMenu);
	}
	
	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	public java.lang.String getAction() {
		return this.action;
	}
	
	public void setAction(java.lang.String value) {
		this.action = value;
	}
	public java.lang.String getDescription() {
		return this.description;
	}
	
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
	public java.lang.Integer getIsLog() {
		return this.isLog;
	}
	
	public void setIsLog(java.lang.Integer value) {
		this.isLog = value;
	}
	public java.lang.Integer getIsIntercept() {
		return this.isIntercept;
	}
	
	public void setIsIntercept(java.lang.Integer value) {
		this.isIntercept = value;
	}
	public java.lang.String getSummary() {
		return this.summary;
	}
	
	public void setSummary(java.lang.String value) {
		this.summary = value;
	}
	public java.lang.Long getParentId() {
		return this.parentId;
	}
	
	public void setParentId(java.lang.Long value) {
		this.parentId = value;
	}
	public java.lang.Integer getIsQuery() {
		return this.isQuery;
	}
	
	public void setIsQuery(java.lang.Integer value) {
		this.isQuery = value;
	}
	public java.lang.Integer getIndexs() {
		return this.indexs;
	}
	
	public void setIndexs(java.lang.Integer value) {
		this.indexs = value;
	}
	public java.lang.Long getMenuId() {
		return this.menuId;
	}
	
	public void setMenuId(java.lang.Long value) {
		this.menuId = value;
	}

	
	
	public List<MenuDo> getChildMenuList() {
		return childMenuList;
	}

	public void setChildMenuList(List<MenuDo> childMenuList) {
		this.childMenuList = childMenuList;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Name",getName())
			.append("Action",getAction())
			.append("Description",getDescription())
			.append("IsLog",getIsLog())
			.append("IsIntercept",getIsIntercept())
			.append("Summary",getSummary())
			.append("ParentId",getParentId())
			.append("IsQuery",getIsQuery())
			.append("Indexs",getIndexs())
			.append("MenuId",getMenuId())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof MenuDo == false) return false;
		if(this == obj) return true;
		MenuDo other = (MenuDo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}