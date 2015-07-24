/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.service.system
 * @Title: SettSchemeServiceImpl.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月6日 上午9:34:53
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.service.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.base.dataobject.NPageDo;
import com.hehenian.biz.common.system.ISettSchemeService;
import com.hehenian.biz.common.system.dataobject.FeeRuleDo;
import com.hehenian.biz.common.system.dataobject.FeeRuleDo.RuleType;
import com.hehenian.biz.common.system.dataobject.SettSchemeDo;
import com.hehenian.biz.component.system.ISettSchemeComponent;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月6日 上午9:34:53
 */
@Service("settSchemeService")
public class SettSchemeServiceImpl implements ISettSchemeService {
    private final Logger         logger = Logger.getLogger(this.getClass());
    @Autowired
    private ISettSchemeComponent settSchemeComponent;

    @Override
    public SettSchemeDo getBySchemeId(Long schemeId) {
        return settSchemeComponent.getBySchemeId(schemeId);
    }

    @Override
    public SettSchemeDo getBySchemeCode(String schemeCode) {
        return settSchemeComponent.getBySchemeCode(schemeCode);
    }

    @Override
    public FeeRuleDo getBySchemeIdAndRuleType(Long schemeId, RuleType ruleType) {
        return settSchemeComponent.getBySchemeIdAndRuleType(schemeId, ruleType);
    }

    
    @Override
	public List<SettSchemeDo> getSettSchemesList(Map<String, Object> searchItems) {
        return settSchemeComponent.getSettSchemesList(searchItems);
    }
    
    @Override
    public NPageDo<SettSchemeDo> querySettSchemes(Map<String, Object> searchItems) {
        try {
            NPageDo<SettSchemeDo> pageDo = new NPageDo<SettSchemeDo>();
            long count = settSchemeComponent.countSettSchemes(searchItems);
            pageDo.setTotalCount(count);
            if (count == 0) {
                return pageDo;
            }
            List<SettSchemeDo> SettSchemeDoList = settSchemeComponent.querySettSchemes(searchItems);
            pageDo.setModelList(SettSchemeDoList);
            return pageDo;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            NPageDo<SettSchemeDo> pageDo = new NPageDo<SettSchemeDo>();
            pageDo.setTotalCount(0l);
            return pageDo;
        }
    }

    @Override
    public int addSettScheme(SettSchemeDo settSchemeDo) {
        return settSchemeComponent.addSettScheme(settSchemeDo);
    }

    @Override
    public int updateSettScheme(SettSchemeDo settSchemeDo) {
        return settSchemeComponent.updateSettScheme(settSchemeDo);
    }

    @Override
    public int deleteBySchemeId(Long schemeId) {
        return settSchemeComponent.deleteBySchemeId(schemeId);
    }

	@Override
	public List<SettSchemeDo> selectList() {
		return settSchemeComponent.selectList();
	}
    
}
