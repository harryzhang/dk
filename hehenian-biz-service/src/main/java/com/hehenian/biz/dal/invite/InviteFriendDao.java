/**
 * 
 */
package com.hehenian.biz.dal.invite;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.invite.dataobject.InviteFriend;

/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.dal.invite
 * @Title: InviteFriendDao
 * @Description: 邀请好友数据库操作
 * @author: chenzhpmf
 * @date 2015年5月13日
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
public interface InviteFriendDao {

	/**
	 * 通过指定条件获取邀请好友信息
	 * @param queryParams
	 * @return
	 */
	List<InviteFriend> listInviteFriend(Map<String, Object> queryParams);

	/**
	 * 根据指定条件获取邀请好友记录数量
	 * @param queryParams
	 * @return
	 */
	long countInviteFriend(Map<String, Object> queryParams);

	/**
	 * 统计投资的好友
	 * @param queryParams
	 * @return
	 */
	long countInvestFriend(Map<String, Object> queryParams);

	/**
	 * 统计注册的好友
	 * @param queryParams
	 * @return
	 */
	long countRegisterFriend(Map<String, Object> queryParams);
	/**
	 * 根据用户ID查询推荐人的信息
	 * @param queryParams
	 * @return
	 */
	Map<String, Object> queryReferInfoByUserId(Map<String, Object> queryParams );
}
