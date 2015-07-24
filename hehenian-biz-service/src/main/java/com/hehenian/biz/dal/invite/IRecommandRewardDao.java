package com.hehenian.biz.dal.invite;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.invite.dataobject.RecommondRewardDo;

public interface IRecommandRewardDao {
	List<RecommondRewardDo> listRecommandReward(Map<String, Object> conditon);
	
	int insertRecommandReward(RecommondRewardDo rr);
	
	RecommondRewardDo getSumRewardAmount(Map<String, Object> conditon) ;
	
	long getRewardNum(Map<String, Object> conditon) ;
	
	Double getReferGroupReward(Map<String, Object> conditon) ;
}
