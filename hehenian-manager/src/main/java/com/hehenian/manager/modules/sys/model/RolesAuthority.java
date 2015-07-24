package com.hehenian.manager.modules.sys.model;

/**
 * 角色权限表
 */
public class RolesAuthority {

	private Integer id;
	
	/**
	 * 角色id
	 */
	private Integer roleId;
	
	/**
	 * 权限id
	 */
	private Integer authorityId;
	
	/**
	 * 是否有效
	 */
	private Boolean enabled;
	

	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(Integer authorityId) {
		this.authorityId = authorityId;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	
}
