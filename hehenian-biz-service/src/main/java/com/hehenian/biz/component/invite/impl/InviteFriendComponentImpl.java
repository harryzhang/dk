/**
 * 
 */
package com.hehenian.biz.component.invite.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hehenian.biz.common.invite.dataobject.InviteFriend;
import com.hehenian.biz.component.invite.InviteFriendComponent;
import com.hehenian.biz.dal.invite.InviteFriendDao;

/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.invite.impl
 * @Title: InviteFriendComponentImpl
 * @Description: TODO
 * @author: chenzhpmf
 * @date 2015年5月13日
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
@Component("inviteFriendComponent")
public class InviteFriendComponentImpl implements InviteFriendComponent {
	
	@Resource
	private InviteFriendDao inviteFriendDao;

	/* (non-Javadoc)
	 * @see com.hehenian.biz.component.invite.InviteFriendComponent#listInviteFriend(java.util.Map)
	 */
	@Override
	public List<InviteFriend> listInviteFriend(Map<String, Object> queryParams) {
		return this.inviteFriendDao.listInviteFriend(queryParams);
	}

	@Override
	public long countInviteFriend(Map<String, Object> queryParams) {
		return this.inviteFriendDao.countInviteFriend(queryParams);
	}
	
	@Override
	public Map<String, Object> queryReferInfoByUserId(Map<String, Object> queryParams ) {
		return this.inviteFriendDao.queryReferInfoByUserId(queryParams);
	}
}
