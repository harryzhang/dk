package com.hehenian.biz.service.invite;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hehenian.biz.common.invite.IRecommandRateService;
import com.hehenian.biz.common.invite.dataobject.RecommandRateDo;
import com.hehenian.biz.dal.invite.IRecommandRateDao;

@Service("recommandRateService")
public class RecommandRateServiceImpl implements IRecommandRateService{
	@Resource
	private IRecommandRateDao recommandRateDao;
	
	@Override
	public RecommandRateDo getRecommandRate(int source) {
		Map<String, Object> conditon = new HashMap<String, Object>();
		conditon.put("source", source);
		return recommandRateDao.getRecommandRate(conditon);
	}


}
