/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.dal.system
 * @Title: IFeeRuleDao.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月6日 上午9:49:44
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.dal.loan;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hehenian.biz.common.system.dataobject.FeeRuleDo;
import com.hehenian.biz.common.system.dataobject.FeeRuleDo.RuleType;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月6日 上午9:49:44
 */
public interface IManagerFeeRuleDao {

    /**
     * 根据方案ID查询费用规则信息
     * 
     * @param schemeId
     * @return
     * @author: liuzgmf
     * @date: 2015年1月6日上午9:50:17
     */
    List<FeeRuleDo> queryBySchemeId(Long schemeId);

    /**
     * 根据方案ID，规则类型查询费用规则信息
     * 
     * @param schemeId
     * @param ruleType
     * @return
     * @author: liuzgmf
     * @date: 2015年1月7日下午3:54:07
     */
    FeeRuleDo getBySchemeIdAndRuleType(@Param("schemeId") Long schemeId, @Param("ruleType") RuleType ruleType);

    /**
     * 新增费用规则信息
     * 
     * @param feeRuleDo
     * @author: liuzgmf
     * @date: 2015年1月9日上午9:24:51
     */
    int addFeeRule(FeeRuleDo feeRuleDo);

    /**
     * 删除结算方案的费用规则信息
     * 
     * @param schemeId
     * @author: liuzgmf
     * @date: 2015年1月9日上午9:25:14
     */
    int deleteBySchemeId(Long schemeId);
}
