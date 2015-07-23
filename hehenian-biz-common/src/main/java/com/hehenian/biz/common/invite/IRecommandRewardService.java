/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.invite
 * @Title: IRecommandRewardService.java
 * @Description: TODO
 *
 * @author: jiangwmf
 * @date 2015-5-13 下午4:43:03
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.common.invite;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.invite.dataobject.RecommondRewardDo;

public interface IRecommandRewardService {
	
	//通过用户id，月份查询奖励记录
	List<RecommondRewardDo> listRecommandReward(Map<String, Object> map);
	
	int insertRecommandReward(RecommondRewardDo rr);
	
	RecommondRewardDo  getSumRewardAmount(String userId,Date startDate,Date endDate);
	
	long getRewardNum(String userId,Date startDate,Date endDate);
	
	double getReferGroupReward(String userId,String referee);
}
