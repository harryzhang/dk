/**
 * 
 */
package com.hehenian.biz.service.invite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.base.result.ResultSupport;
import com.hehenian.biz.common.invite.InviteDetailService;
import com.hehenian.biz.common.invite.dataobject.InviteDetail;
import com.hehenian.biz.common.notify.INotifyService;
import com.hehenian.biz.common.notify.dataobject.NotifyBusinessType;
import com.hehenian.biz.common.notify.dataobject.NotifyDo;
import com.hehenian.biz.common.notify.dataobject.SMSNotifyDo;
import com.hehenian.biz.component.invite.InviteDetailComponent;

/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.service.invite
 * @Title: InviteDetailServiceImpl
 * @Description: 邀请详情业务接口实现
 * @author: chenzhpmf
 * @date 2015年5月13日
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
@Service("inviteDetailService")
public class InviteDetailServiceImpl implements InviteDetailService {
	
	@Resource
	private InviteDetailComponent inviteDetailComponent;
	
	@Autowired
    private INotifyService  notifyService;

	/* (non-Javadoc)
	 * @see com.hehenian.biz.common.invite.InviteDetailService#addInvite(com.hehenian.biz.common.invite.dataobject.InviteDetail)
	 */
	@Override
	public IResult<InviteDetail> addInvite(InviteDetail inviteDetail) {
		IResult<InviteDetail> result = null;
		result = new ResultSupport<InviteDetail>();
		NotifyDo notifyDo = new SMSNotifyDo(inviteDetail.getContent(), inviteDetail.getInvitee().getMobilePhone(), NotifyBusinessType.notify.name());
		notifyDo.setAsync(true);
		notifyDo.setMessageTemplate("sms_template_invite.ftl");
		result.setSuccess(this.notifyService.send(notifyDo));
		result.setModel(inviteDetail);
		
		this.inviteDetailComponent.addInvite(inviteDetail);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.hehenian.biz.common.invite.InviteDetailService#getList(java.util.Map)
	 */
	@Override
	public List<InviteDetail> getList(Map<String, Object> queryParams) {
		return this.inviteDetailComponent.getList(queryParams);
	}

	/* (non-Javadoc)
	 * @see com.hehenian.biz.common.invite.InviteDetailService#count(java.util.Map)
	 */
	@Override
	public Long count(Map<String, Object> queryParams) {
		return this.inviteDetailComponent.count(queryParams);
	}

	@Override
	public List<IResult<InviteDetail>> addInvite(List<InviteDetail> inviteDetails) {
		List<IResult<InviteDetail>> resultList = new ArrayList<IResult<InviteDetail>>();
		for (InviteDetail inviteDetail : inviteDetails) {
			resultList.add(addInvite(inviteDetail));
		}
		return resultList;
	}

}
