/**
 * 
 */
package com.hehenian.biz.common.invite;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.invite.dataobject.InviteDetail;

/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.invite
 * @Title: InviteDetailService
 * @Description: 邀请详情业务接口
 * @author: chenzhpmf
 * @date 2015年5月13日
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
public interface InviteDetailService {

	/**
	 * 添加一条邀请记录
	 * @param inviteDetail
	 * @return
	 */
	public IResult<InviteDetail> addInvite(InviteDetail inviteDetail);

	/**
	 * 添加多条邀请记录
	 * @param inviteDetailList
	 * @return
	 */
	public List<IResult<InviteDetail>> addInvite(List<InviteDetail> inviteDetailList);

	/**
	 * 根据指定条件获取邀请记录
	 * @param inviteDetail
	 * @return
	 */
	public List<InviteDetail> getList(Map<String, Object> queryParams);

	/**
	 * 根据指定条件获取邀请记录数量
	 * @param inviteDetail
	 * @return
	 */
	public Long count(Map<String, Object> queryParams);
}
