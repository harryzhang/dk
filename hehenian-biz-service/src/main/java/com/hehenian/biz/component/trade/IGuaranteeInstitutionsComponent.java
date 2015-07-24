package com.hehenian.biz.component.trade;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.trade.dataobject.GuaranteeInstitutionsDo;

public interface IGuaranteeInstitutionsComponent {

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public GuaranteeInstitutionsDo getById(int id);
	
	/**
	 *根据条件查询列表
	 */
	public List<GuaranteeInstitutionsDo> selectGuaranteeInstitutions(Map<String,Object> parameterMap);
	
	/**
	 * 更新
	 */
	public int  updateGuaranteeInstitutionsById(GuaranteeInstitutionsDo newGuaranteeInstitutionsDo);
	
	/**
	 * 新增
	 */
	public int addGuaranteeInstitutions(GuaranteeInstitutionsDo newGuaranteeInstitutionsDo);
	
	/**
	 * 删除
	 */
	public int deleteById(int id);

    /** 
     * 更新担保公司的资金
     * @param needAmount  
     * @author: zhangyunhmf
     * @date: 2014年9月24日上午9:15:23
     */
    public int updateOrganMoney(double needAmount);

}
