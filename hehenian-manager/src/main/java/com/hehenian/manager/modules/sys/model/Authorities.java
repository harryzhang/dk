package com.hehenian.manager.modules.sys.model;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

/**
 * 权限
 */
public class Authorities implements GrantedAuthority{

	private static final long serialVersionUID = -2199591630706329716L;

	private Integer id;
	
	/**
	 * 权限名称
	 */
	private String name;
	
	/**
	 * 权限描述
	 */
	private String authDesc;
	
	
	private Boolean enabled;
	
	private Boolean issys;
	
	private String module;

	private List<Resources> resources;
	
	public Authorities(){}
	
	public Authorities(String name){
		this.name=name;
	}
	
	

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

	public String getAuthDesc() {
		return authDesc;
	}

	public void setAuthDesc(String authDesc) {
		this.authDesc = authDesc;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getIssys() {
		return issys;
	}

	public void setIssys(Boolean issys) {
		this.issys = issys;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public List<Resources> getResources() {
		return resources;
	}

	public void setResources(List<Resources> resources) {
		this.resources = resources;
	}

	@Override
	public String getAuthority() {
		return name;
	}
	
	
}
