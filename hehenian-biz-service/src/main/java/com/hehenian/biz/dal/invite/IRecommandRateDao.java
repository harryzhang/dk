package com.hehenian.biz.dal.invite;

import java.util.Map;

import com.hehenian.biz.common.invite.dataobject.RecommandRateDo;

public interface IRecommandRateDao {
	public RecommandRateDo getRecommandRate(Map<String, Object> conditon);
}
