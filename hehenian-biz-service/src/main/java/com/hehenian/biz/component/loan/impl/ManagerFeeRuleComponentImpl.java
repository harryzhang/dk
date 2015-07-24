/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.system.impl
 * @Title: FeeRuleComponentImpl.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月12日 下午5:04:57
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.loan.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.system.dataobject.FeeRuleDo;
import com.hehenian.biz.common.system.dataobject.FeeRuleDo.RuleType;
import com.hehenian.biz.component.loan.IManagerFeeRuleComponent;
import com.hehenian.biz.dal.loan.IManagerFeeRuleDao;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月12日 下午5:04:57
 */
@Component("managerFeeRuleComponent")
public class ManagerFeeRuleComponentImpl implements IManagerFeeRuleComponent {
    @Autowired
    private IManagerFeeRuleDao managerFeeRuleDao;

    @Override
    public FeeRuleDo getBySchemeIdAndRuleType(Long schemeId, RuleType ruleType) {
        return managerFeeRuleDao.getBySchemeIdAndRuleType(schemeId, ruleType);
    }

}
