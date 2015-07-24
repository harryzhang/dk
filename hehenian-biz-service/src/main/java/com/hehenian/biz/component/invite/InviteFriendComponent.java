/**
 * 
 */
package com.hehenian.biz.component.invite;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.invite.dataobject.InviteFriend;

/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.invite
 * @Title: InviteFriendComponent
 * @Description: TODO
 * @author: chenzhpmf
 * @date 2015年5月13日
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
public interface InviteFriendComponent {

	/**
	 * 通过指定条件获取邀请好友信息
	 * @param queryParams
	 * @return
	 */
	List<InviteFriend> listInviteFriend(Map<String, Object> queryParams);

	/**
	 * 根据指定条件获取邀请好友数量
	 * @param queryParams
	 * @return
	 */
	long countInviteFriend(Map<String, Object> queryParams);
	
	Map<String, Object> queryReferInfoByUserId(Map<String, Object> queryParams );
}
