package com.hehenian.manager.modules.users.model;

/**
 * 
 * 
 * 角色
 */
public class Roles {

	private Integer id;
	
	/**
	 * 角色名
	 */
	private String name;
	
	/**
	 * 角色描述
	 */
	private String roleDesc;
	
	/**
	 * 是否可用
	 */
	private Boolean enabled;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	
}
