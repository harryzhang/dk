package com.hehenian.biz.service.trade.impl;

import com.hehenian.biz.common.trade.IRiskDetailService;
import com.hehenian.biz.common.trade.dataobject.RiskDetailDo;
import com.hehenian.biz.component.trade.IRiskDetailComponent;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


@Service("riskDetailService")
public class RiskDetailServiceImpl implements IRiskDetailService {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired	
    private IRiskDetailComponent  riskDetailComponent;

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public RiskDetailDo getById(int id){
	  return riskDetailComponent.getById(id);
	}
	
	/**
	 *根据条件查询列表
	 */
	public List<RiskDetailDo> selectRiskDetail(Map<String,Object> parameterMap){
		return riskDetailComponent.selectRiskDetail(parameterMap);
	}
	
	/**
	 * 更新
	 */
	public int  updateRiskDetailById(RiskDetailDo newRiskDetailDo){
		return riskDetailComponent.updateRiskDetailById(newRiskDetailDo);
	}
	
	/**
	 * 新增
	 */
	public long addRiskDetail(RiskDetailDo newRiskDetailDo){
		return riskDetailComponent.addRiskDetail(newRiskDetailDo);
	}
	
	/**
	 * 删除
	 */
	public int deleteById(int id){
		return riskDetailComponent.deleteById(id);
	}

}
