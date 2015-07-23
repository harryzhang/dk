/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.account.dataobject
 * @Title: LoginInfoRelate.java
 * @Description: 手机号、邮箱等与userId对应关系分表
 *
 * @author: zhanbmf
 * @date 2015-3-29 上午10:43:19
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.common.account.dataobject;

import java.io.Serializable;

public class LoginInfoRelate implements Serializable {
	private static final long serialVersionUID = 6261344407094118965L;
	/**
	 * 数据库自增主键
	 */
	private Integer id;
	/**
	 * 登录类型,邮箱0，手机1，昵称2
	 */
	private Integer type;
	/**
	 * 登录信息(邮箱|手机|昵称|...)
	 */
	private String loginInfo;
	/**
	 * 用户ID
	 */
	private Integer userId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(String loginInfo) {
		this.loginInfo = loginInfo;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
