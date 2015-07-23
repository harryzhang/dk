/**
 * 
 */
package com.hehenian.biz.common.invite.dataobject;

import java.io.Serializable;
import java.util.Date;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;

/**
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.invite.dataobject
 * @Title: InviteDetail
 * @Description: 邀请详细信息实体类
 * @author: chenzhpmf
 * @date 2015年5月13日
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0
 */
public class InviteDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号（自增长）
	 */
	private Long id;

	/**
	 * 邀请人
	 */
	private AccountUserDo invite;

	/**
	 * 被邀请者
	 */
	private AccountUserDo invitee;

	/**
	 * 邀请信息内容
	 */
	private String content;

	/**
	 * 邀请类型
	 */
	private InviteType type;

	/**
	 * 邀请时间
	 */
	private Date createTime;

	/**
	 * 更改时间
	 */
	private Date updateTime;

	/**
	 * 备注信息
	 */
	private String remarks;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the invite
	 */
	public AccountUserDo getInvite() {
		return invite;
	}

	/**
	 * @param invite the invite to set
	 */
	public void setInvite(AccountUserDo invite) {
		this.invite = invite;
	}

	/**
	 * @return the invitee
	 */
	public AccountUserDo getInvitee() {
		return invitee;
	}

	/**
	 * @param invitee the invitee to set
	 */
	public void setInvitee(AccountUserDo invitee) {
		this.invitee = invitee;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the type
	 */
	public InviteType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(InviteType type) {
		this.type = type;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "InviteDetail [id=" + id + ", invite=" + invite + ", invitee=" + invitee + ", content=" + content + ", type=" + type
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", remarks=" + remarks + "]";
	}

}
