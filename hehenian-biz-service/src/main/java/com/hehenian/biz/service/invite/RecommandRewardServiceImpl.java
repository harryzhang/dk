package com.hehenian.biz.service.invite;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hehenian.biz.common.invite.IRecommandRewardService;
import com.hehenian.biz.common.invite.dataobject.RecommondRewardDo;
import com.hehenian.biz.dal.invite.IRecommandRewardDao;
@Service("recommandRewardService")
public class RecommandRewardServiceImpl implements IRecommandRewardService {
	@Resource
	private IRecommandRewardDao recommandRewardDao;
	
	@Override
	public List<RecommondRewardDo> listRecommandReward(Map<String, Object> map) {
		return recommandRewardDao.listRecommandReward(map);
	}

	@Override
	public int insertRecommandReward(RecommondRewardDo rr) {
		return recommandRewardDao.insertRecommandReward(rr);
	}

	@Override
	public RecommondRewardDo  getSumRewardAmount(String userId, Date startDate,Date endDate) {
		Map<String, Object> conditon = new HashMap<String, Object>();
		conditon.put("userId", userId);
		conditon.put("startDate", startDate);
		conditon.put("endDate", endDate);
		return recommandRewardDao.getSumRewardAmount(conditon);
	}

	@Override
	public long getRewardNum(String userId, Date startDate, Date endDate) {
		Map<String, Object> conditon = new HashMap<String, Object>();
		conditon.put("userId", userId);
		conditon.put("startDate", startDate);
		conditon.put("endDate", endDate);
		return recommandRewardDao.getRewardNum(conditon);
	}

	@Override
	public double getReferGroupReward(String userId, String referee) {
		Map<String, Object> conditon = new HashMap<String, Object>();
		conditon.put("userId", userId);
		conditon.put("referee", referee);
		if(recommandRewardDao.getReferGroupReward(conditon)!=null){
			return recommandRewardDao.getReferGroupReward(conditon);
		}else
		return 0d;
	}


}
