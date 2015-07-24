package com.sp2p.entity;

import java.io.Serializable;

public class Operator implements Serializable{
	
	/**   
	 *   
	 * @since 1.0.0   
	 */   
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	public Operator() {
		super();
	}
	public Operator(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
