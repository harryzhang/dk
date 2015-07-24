package com.hehenian.manager.modules.sys.model;

/**
 * 菜单
 */
public class Resources {

	private Integer id;
	
	/**
	 * 菜单名
	 */
	private String name;
	
	/**
	 * 菜单描述
	 */
	private String resourceDesc;
	
	/**
	 * 菜单url
	 */
	private String resourceStr;
	
	
	private String module;
	
	/**
	 * 是否有效
	 */
	private Boolean enabled;
	
	/**
	 * 是否系统菜单
	 */
	private Boolean issys;
	
	private String icon;

	/**
	 * 排序
	 */
	private int order;
	
	
	private ResouceType resouceType; //资源类型
	
	/**
	 * 
	 * menu 菜单资源类型， button 按钮资源类型 
	 *
	 */
	public enum ResouceType{ menu, button};

	
	
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

	public String getResourceDesc() {
		return resourceDesc;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}

	public String getResourceStr() {
		return resourceStr;
	}

	public void setResourceStr(String resourceStr) {
		this.resourceStr = resourceStr;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public ResouceType getResouceType() {
		return resouceType;
	}

	public void setResouceType(ResouceType resouceType) {
		this.resouceType = resouceType;
	}
	
	
}
