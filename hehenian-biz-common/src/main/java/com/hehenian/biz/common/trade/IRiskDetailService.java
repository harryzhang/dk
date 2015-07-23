package com.hehenian.biz.common.trade;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.trade.dataobject.RiskDetailDo;


public interface IRiskDetailService{

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public RiskDetailDo getById(int id);
	
	/**
	 *根据条件查询列表
	 */
	public List<RiskDetailDo> selectRiskDetail(Map<String,Object> parameterMap);
	
	/**
	 * 更新
	 */
	public int  updateRiskDetailById(RiskDetailDo newRiskDetailDo);
	
	/**
	 * 新增
	 */
	public long addRiskDetail(RiskDetailDo newRiskDetailDo);
	
	/**
	 * 删除
	 */
	public int deleteById(int id);
}
