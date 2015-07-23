/**
 * 
 */
package com.hehenian.biz.common.invite.dataobject;

import java.io.Serializable;
import java.math.BigDecimal;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.account.dataobject.PersonDo;

/**
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.invite.dataobject
 * @Title: InviteFriend
 * @Description: 邀请好友信息实体类
 * @author: chenzhpmf
 * @date 2015年5月13日
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0
 */
public class InviteFriend implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 编号(自增长)
	 */
	private Long id;
	
	/**
	 * 邀请好友的账户信息
	 */
	private AccountUserDo friendUserInfo;
	
	/**
	 * 推荐好友的个人信息
	 */
	private PersonDo friendPersonInfo;
	
	/**
	 * 总投资金额
	 */
	private BigDecimal sunInvestMoney;
	
	/**
	 * 奖励金额
	 */
	private BigDecimal awardMoney;
	
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
	 * @return the friendUserInfo
	 */
	public AccountUserDo getFriendUserInfo() {
		return friendUserInfo;
	}

	/**
	 * @param friendUserInfo the friendUserInfo to set
	 */
	public void setFriendUserInfo(AccountUserDo friendUserInfo) {
		this.friendUserInfo = friendUserInfo;
	}

	/**
	 * @return the friendPersonInfo
	 */
	public PersonDo getFriendPersonInfo() {
		return friendPersonInfo;
	}

	/**
	 * @param friendPersonInfo the friendPersonInfo to set
	 */
	public void setFriendPersonInfo(PersonDo friendPersonInfo) {
		this.friendPersonInfo = friendPersonInfo;
	}

	/**
	 * @return the sunInvestMoney
	 */
	public BigDecimal getSunInvestMoney() {
		return sunInvestMoney;
	}

	/**
	 * @param sunInvestMoney the sunInvestMoney to set
	 */
	public void setSunInvestMoney(BigDecimal sunInvestMoney) {
		this.sunInvestMoney = sunInvestMoney;
	}

	/**
	 * @return the awardMoney
	 */
	public BigDecimal getAwardMoney() {
		return awardMoney;
	}

	/**
	 * @param awardMoney the awardMoney to set
	 */
	public void setAwardMoney(BigDecimal awardMoney) {
		this.awardMoney = awardMoney;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "InviteFriend [id=" + id + ", friendUserInfo=" + friendUserInfo + ", friendPersonInfo=" + friendPersonInfo
				+ ", sunInvestMoney=" + sunInvestMoney + ", awardMoney=" + awardMoney + ", remarks=" + remarks + "]";
	}

}
