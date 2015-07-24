/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.system.impl
 * @Title: SettSchemeComponentImpl.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月6日 上午9:37:21
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.loan.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.system.dataobject.FeeRuleDo;
import com.hehenian.biz.common.system.dataobject.FeeRuleDo.RuleType;
import com.hehenian.biz.common.system.dataobject.SettSchemeDo;
import com.hehenian.biz.component.loan.IManagerSettSchemeComponent;
import com.hehenian.biz.dal.loan.IManagerFeeRuleDao;
import com.hehenian.biz.dal.loan.IManagerSettSchemeDao;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月6日 上午9:37:21
 */
@Component("managerSettSchemeComponent")
public class ManagerSettSchemeComponentImpl implements IManagerSettSchemeComponent {
    @Autowired
    private IManagerSettSchemeDao managerSettSchemeDao;
    @Autowired
    private IManagerFeeRuleDao    managerFeeRuleDao;

    @Override
    public SettSchemeDo getBySchemeId(Long schemeId) {
        SettSchemeDo settSchemeDo = managerSettSchemeDao.getBySchemeId(schemeId);
        if (settSchemeDo == null) {
            return settSchemeDo;
        }
        settSchemeDo.setFeeRuleDoList(managerFeeRuleDao.queryBySchemeId(settSchemeDo.getSchemeId()));
        return settSchemeDo;
    }

    @Override
    public SettSchemeDo getBySchemeCode(String schemeCode) {
        SettSchemeDo settSchemeDo = managerSettSchemeDao.getBySchemeCode(schemeCode);
        if (settSchemeDo == null) {
            return settSchemeDo;
        }
        settSchemeDo.setFeeRuleDoList(managerFeeRuleDao.queryBySchemeId(settSchemeDo.getSchemeId()));
        return settSchemeDo;
    }

    @Override
    public FeeRuleDo getBySchemeIdAndRuleType(Long schemeId, RuleType ruleType) {
        return managerFeeRuleDao.getBySchemeIdAndRuleType(schemeId, ruleType);
    }

    @Override
    public int countSettSchemes(Map<String, Object> searchItems) {
        return managerSettSchemeDao.countSettSchemes(searchItems);
    }

    @Override
    public List<SettSchemeDo> querySettSchemes(Map<String, Object> searchItems) {
        return managerSettSchemeDao.querySettSchemes(searchItems);
    }

    @Override
    public int addSettScheme(SettSchemeDo settSchemeDo) {
        int count = managerSettSchemeDao.addSettScheme(settSchemeDo);
        if (settSchemeDo.getFeeRuleDoList() == null || settSchemeDo.getFeeRuleDoList().size() == 0) {
            return count;
        }
        for (FeeRuleDo feeRuleDo : settSchemeDo.getFeeRuleDoList()) {
            feeRuleDo.setSchemeId(settSchemeDo.getSchemeId());
            managerFeeRuleDao.addFeeRule(feeRuleDo);
        }
        return count;
    }

    @Override
    public int updateSettScheme(SettSchemeDo settSchemeDo) {
        int count = managerSettSchemeDao.updateSettScheme(settSchemeDo);
        managerFeeRuleDao.deleteBySchemeId(settSchemeDo.getSchemeId());
        if (settSchemeDo.getFeeRuleDoList() == null || settSchemeDo.getFeeRuleDoList().size() == 0) {
            return count;
        }
        for (FeeRuleDo feeRuleDo : settSchemeDo.getFeeRuleDoList()) {
            feeRuleDo.setSchemeId(settSchemeDo.getSchemeId());
            managerFeeRuleDao.addFeeRule(feeRuleDo);
        }
        return count;
    }

    @Override
    public int deleteBySchemeId(Long schemeId) {
    	managerFeeRuleDao.deleteBySchemeId(schemeId);// 删除费用规则信息
        return managerSettSchemeDao.deleteBySchemeId(schemeId);

    }

}
