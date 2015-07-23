/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.system
 * @Title: ISettSchemeService.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月5日 下午9:06:14
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.system;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.base.dataobject.NPageDo;
import com.hehenian.biz.common.system.dataobject.FeeRuleDo;
import com.hehenian.biz.common.system.dataobject.FeeRuleDo.RuleType;
import com.hehenian.biz.common.system.dataobject.SettSchemeDo;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月5日 下午9:06:14
 */
public interface ISettSchemeService {
    /**
     * 根据方案ID查询结算方案信息
     * 
     * @param schemeId
     * @return
     * @author: liuzgmf
     * @date: 2015年1月6日上午9:32:56
     */
    SettSchemeDo getBySchemeId(Long schemeId);

    /**
     * 根据方案代码查询结算方案信息
     * 
     * @param schemeCode
     * @return
     * @author: liuzgmf
     * @date: 2015年1月6日上午9:33:21
     */
    SettSchemeDo getBySchemeCode(String schemeCode);

    /**
     * 根据方案ID，规则类型查询费用规则信息
     * 
     * @param schemeId
     * @param ruleType
     * @return
     * @author: liuzgmf
     * @date: 2015年1月7日下午3:54:07
     */
    FeeRuleDo getBySchemeIdAndRuleType(Long schemeId, RuleType ruleType);

    /**
     * 根据条件查询借款方案信息
     * 
     * @param searchItems
     * @return
     * @author: liuzgmf
     * @date: 2015年1月9日上午9:06:04
     */
    NPageDo<SettSchemeDo> querySettSchemes(Map<String, Object> searchItems);

    /**
     * 新增结算方案信息
     * 
     * @param settSchemeDo
     * @return
     * @author: liuzgmf
     * @date: 2015年1月9日上午9:07:04
     */
    int addSettScheme(SettSchemeDo settSchemeDo);

    /**
     * 修改结算方案信息
     * 
     * @param settSchemeDo
     * @return
     * @author: liuzgmf
     * @date: 2015年1月9日上午9:07:04
     */
    int updateSettScheme(SettSchemeDo settSchemeDo);

    /**
     * 根据方案ID删除结算方案信息
     * 
     * @param schemeId
     * @return
     * @author: liuzgmf
     * @date: 2015年1月9日上午9:08:19
     */
    int deleteBySchemeId(Long schemeId);

	List<SettSchemeDo> selectList();

	/**
	 * productCode 产品代码等条件查询
	 * @param searchItems
	 * @return
	 */
	public List<SettSchemeDo> getSettSchemesList(Map<String, Object> searchItems);

}
