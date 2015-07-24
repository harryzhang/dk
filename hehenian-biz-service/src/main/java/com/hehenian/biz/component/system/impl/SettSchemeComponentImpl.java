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
package com.hehenian.biz.component.system.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.system.dataobject.FeeRuleDo;
import com.hehenian.biz.common.system.dataobject.FeeRuleDo.RuleType;
import com.hehenian.biz.common.system.dataobject.SettSchemeDo;
import com.hehenian.biz.component.system.ISettSchemeComponent;
import com.hehenian.biz.dal.system.IFeeRuleDao;
import com.hehenian.biz.dal.system.ISettSchemeDao;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月6日 上午9:37:21
 */
@Component("settSchemeComponent")
public class SettSchemeComponentImpl implements ISettSchemeComponent {
    @Autowired
    private ISettSchemeDao settSchemeDao;
    @Autowired
    private IFeeRuleDao    feeRuleDao;

    @Override
    public SettSchemeDo getBySchemeId(Long schemeId) {
        SettSchemeDo settSchemeDo = settSchemeDao.getBySchemeId(schemeId);
        if (settSchemeDo == null) {
            return settSchemeDo;
        }
        settSchemeDo.setFeeRuleDoList(feeRuleDao.queryBySchemeId(settSchemeDo.getSchemeId()));
        return settSchemeDo;
    }

    @Override
    public SettSchemeDo getBySchemeCode(String schemeCode) {
        SettSchemeDo settSchemeDo = settSchemeDao.getBySchemeCode(schemeCode);
        if (settSchemeDo == null) {
            return settSchemeDo;
        }
        settSchemeDo.setFeeRuleDoList(feeRuleDao.queryBySchemeId(settSchemeDo.getSchemeId()));
        return settSchemeDo;
    }

    @Override
    public FeeRuleDo getBySchemeIdAndRuleType(Long schemeId, RuleType ruleType) {
        return feeRuleDao.getBySchemeIdAndRuleType(schemeId, ruleType);
    }

    @Override
    public int countSettSchemes(Map<String, Object> searchItems) {
        return settSchemeDao.countSettSchemes(searchItems);
    }

    /**
     * 没有分页的方法
     * @param searchItems
     * @return
     */
    @Override
    public List<SettSchemeDo> getSettSchemesList(Map<String, Object> searchItems) {
        return settSchemeDao.getSettSchemesList(searchItems);
    }
    
    @Override
    public List<SettSchemeDo> querySettSchemes(Map<String, Object> searchItems) {
        return settSchemeDao.querySettSchemes(searchItems);
    }

    @Override
    public int addSettScheme(SettSchemeDo settSchemeDo) {
        int count = settSchemeDao.addSettScheme(settSchemeDo);
        if (settSchemeDo.getFeeRuleDoList() == null || settSchemeDo.getFeeRuleDoList().size() == 0) {
            return count;
        }
        for (FeeRuleDo feeRuleDo : settSchemeDo.getFeeRuleDoList()) {
            feeRuleDo.setSchemeId(settSchemeDo.getSchemeId());
            feeRuleDao.addFeeRule(feeRuleDo);
        }
        return count;
    }

    @Override
    public int updateSettScheme(SettSchemeDo settSchemeDo) {
        int count = settSchemeDao.updateSettScheme(settSchemeDo);
        feeRuleDao.deleteBySchemeId(settSchemeDo.getSchemeId());
        if (settSchemeDo.getFeeRuleDoList() == null || settSchemeDo.getFeeRuleDoList().size() == 0) {
            return count;
        }
        for (FeeRuleDo feeRuleDo : settSchemeDo.getFeeRuleDoList()) {
            feeRuleDo.setSchemeId(settSchemeDo.getSchemeId());
            feeRuleDao.addFeeRule(feeRuleDo);
        }
        return count;
    }

    @Override
    public int deleteBySchemeId(Long schemeId) {
        feeRuleDao.deleteBySchemeId(schemeId);// 删除费用规则信息
        return settSchemeDao.deleteBySchemeId(schemeId);

    }

	@Override
	public List<SettSchemeDo> selectList() {
		return settSchemeDao.selectList();
	}

}
