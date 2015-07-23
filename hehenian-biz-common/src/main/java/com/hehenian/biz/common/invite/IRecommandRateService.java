package com.hehenian.biz.common.invite;

import com.hehenian.biz.common.invite.dataobject.RecommandRateDo;

public interface IRecommandRateService {
	//通过渠道标示查询推荐奖励利率
	RecommandRateDo getRecommandRate(int source);
}
