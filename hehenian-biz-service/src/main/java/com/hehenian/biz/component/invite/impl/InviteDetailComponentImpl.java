/**
 * 
 */
package com.hehenian.biz.component.invite.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hehenian.biz.common.invite.dataobject.InviteDetail;
import com.hehenian.biz.component.invite.InviteDetailComponent;
import com.hehenian.biz.dal.invite.InviteDetailDao;

/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.invite.impl
 * @Title: InviteDetailComponentImpl
 * @Description: 邀请详情内部接口实现
 * @author: chenzhpmf
 * @date 2015年5月13日
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
@Component("inviteDetailComponent")
public class InviteDetailComponentImpl implements InviteDetailComponent {
	
	@Resource
	private InviteDetailDao inviteDetailDao;

	/* (non-Javadoc)
	 * @see com.hehenian.biz.component.invite.InviteDetailComponent#addInvite(com.hehenian.biz.common.invite.dataobject.InviteDetail)
	 */
	@Override
	public int addInvite(InviteDetail inviteDetail) {
		return this.inviteDetailDao.addInvite(inviteDetail);
	}

	/* (non-Javadoc)
	 * @see com.hehenian.biz.component.invite.InviteDetailComponent#getList(java.util.Map)
	 */
	@Override
	public List<InviteDetail> getList(Map<String, Object> queryParams) {
		return this.inviteDetailDao.getList(queryParams);
	}

	/* (non-Javadoc)
	 * @see com.hehenian.biz.component.invite.InviteDetailComponent#count(java.util.Map)
	 */
	@Override
	public Long count(Map<String, Object> queryParams) {
		return this.inviteDetailDao.count(queryParams);
	}

}
