package com.hehenian.manager.modules.sys.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 模块
 * 
 *
 */
public class Module implements Cloneable,Comparable<Module>{

	private Integer id;
	
	private String module;
	
	private String name;
	
	private String moduleDesc;
	
	private Date createTime;
	
	private List<Resources> resources;

	private int order;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModuleDesc() {
		return moduleDesc;
	}

	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	

	public List<Resources> getResources() {
		return resources;
	}

	public void setResources(List<Resources> resources) {
		this.resources = resources;
	}

	@Override
	public Module clone() throws CloneNotSupportedException {
		Module m=(Module)super.clone();
		m.setResources(new ArrayList<Resources>());
		return m;
	}

	@Override
	public int compareTo(Module o) {
		return this.order<o.order?1:-1;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
}
