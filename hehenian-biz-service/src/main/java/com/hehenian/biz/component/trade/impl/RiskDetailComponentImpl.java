package com.hehenian.biz.component.trade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.trade.dataobject.RiskDetailDo;
import com.hehenian.biz.component.trade.IRiskDetailComponent;
import com.hehenian.biz.dal.trade.IRiskDetailDao;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


@Component("riskDetailComponent")
public class RiskDetailComponentImpl implements IRiskDetailComponent {
	@Autowired
    private IRiskDetailDao  riskDetailDao;

	@Override
	public RiskDetailDo getById(long id) {
		return riskDetailDao.getById(id);
	}

	@Override
	public List<RiskDetailDo> selectRiskDetail(Map<String, Object> parameterMap) {
		return riskDetailDao.selectRiskDetail(parameterMap);
	}

	@Override
	public int updateRiskDetailById(RiskDetailDo newRiskDetailDo) {
		int count = riskDetailDao.updateRiskDetailById(newRiskDetailDo);
		if(count< 1){
			throw new BusinessException("风险保障金更新失败");
		}
		return count;
		
	}

	@Override
	public long addRiskDetail(RiskDetailDo newRiskDetailDo) {
		return riskDetailDao.addRiskDetail(newRiskDetailDo);
	}

	@Override
	public int deleteById(long id) {
		return riskDetailDao.deleteById(id);
	}

	@Override
	public int addRiskAndSumBalance(RiskDetailDo newRiskDetailDo) {
		return riskDetailDao.addRiskAndSumBalance(newRiskDetailDo);
	}
}
