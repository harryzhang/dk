package com.hehenian.biz.common.account.dataobject;

import java.io.Serializable;

/**
 * 用户分组
 * @author liuzgmf
 *
 */
public class UserGroupDo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;// 编号
	private Integer userId;// 用户ID
	private String groupName;// 组名称
	private String groupRemark;// 组备注
	private Integer cashStatus;// 提现状态(默认1 启动 2 禁止)
	private Long adminId;// 管理员ID

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupRemark() {
		return groupRemark;
	}

	public void setGroupRemark(String groupRemark) {
		this.groupRemark = groupRemark;
	}

	public Integer getCashStatus() {
		return cashStatus;
	}

	public void setCashStatus(Integer cashStatus) {
		this.cashStatus = cashStatus;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

}
