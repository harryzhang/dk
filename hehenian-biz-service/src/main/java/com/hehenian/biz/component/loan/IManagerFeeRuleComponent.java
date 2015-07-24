/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.system
 * @Title: IFeeRuleComponent.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月12日 下午5:03:06
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.loan;

import com.hehenian.biz.common.system.dataobject.FeeRuleDo;
import com.hehenian.biz.common.system.dataobject.FeeRuleDo.RuleType;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月12日 下午5:03:06
 */
public interface IManagerFeeRuleComponent {

    /**
     * 根据结算方案ID，费用规则类型查询费用规则信息
     * 
     * @param schemeId
     * @param ruleType
     * @return
     * @author: liuzgmf
     * @date: 2015年1月12日下午5:04:06
     */
    FeeRuleDo getBySchemeIdAndRuleType(Long schemeId, RuleType ruleType);
}
