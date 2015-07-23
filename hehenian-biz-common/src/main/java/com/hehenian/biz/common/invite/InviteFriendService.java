/**
 * 
 */
package com.hehenian.biz.common.invite;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.invite.dataobject.InviteFriend;

/**
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.invite
 * @Title: InviteFriendsService
 * @Description: 邀请好友远程调用接口
 * @author: chenzhpmf
 * @date 2015年5月13日
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
public interface InviteFriendService {

	/**
	 * 通过指定条件获取邀请好友信息
	 * @param queryParams
	 * @return
	 */
	List<InviteFriend> listInviteFriend(Map<String, Object> queryParams);

	/**
	 * 根据指定条件获取邀请好友记录数
	 * @param queryParams
	 * @return
	 */
	long countInviteFriend(Map<String, Object> queryParams);
	
	/**
	 * 根据用户ID查询推荐人的信息
	 * @param queryParams
	 * @return
	 */
	Map<String, Object> queryReferInfoByUserId(Map<String, Object> queryParams );
}
