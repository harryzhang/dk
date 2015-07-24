/**
 * 
 */
package com.hehenian.biz.service.invite;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hehenian.biz.common.invite.InviteFriendService;
import com.hehenian.biz.common.invite.dataobject.InviteFriend;
import com.hehenian.biz.component.invite.InviteFriendComponent;

/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.service.invite
 * @Title: InviteFriendServiceImpl
 * @Description: TODO
 * @author: chenzhpmf
 * @date 2015年5月13日
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
@Service("inviteFriendService")
public class InviteFriendServiceImpl implements InviteFriendService {
	
	@Resource
	private InviteFriendComponent inviteFriendComponent;

	/* (non-Javadoc)
	 * @see com.hehenian.biz.common.invite.InviteFriendService#listInviteFriend(java.util.Map)
	 */
	@Override
	public List<InviteFriend> listInviteFriend(Map<String, Object> queryParams) {
		return this.inviteFriendComponent.listInviteFriend(queryParams);
	}

	@Override
	public long countInviteFriend(Map<String, Object> queryParams) {
		return this.inviteFriendComponent.countInviteFriend(queryParams);
	}

	@Override
	public Map<String, Object> queryReferInfoByUserId(Map<String, Object> queryParams) {
		return this.inviteFriendComponent.queryReferInfoByUserId(queryParams);
	}

}
