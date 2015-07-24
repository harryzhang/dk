/**
 * 
 */
package com.hehenian.biz.component.invite;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.invite.dataobject.InviteDetail;

/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.invite
 * @Title: InviteDetailComponent
 * @Description: 邀请详情内部接口
 * @author: chenzhpmf
 * @date 2015年5月13日
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
public interface InviteDetailComponent {

	/**
	 * 添加一条邀请记录
	 * @param inviteDetail
	 * @return
	 */
	public int addInvite(InviteDetail inviteDetail);

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
