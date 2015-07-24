/**
 * @auther liminglmf
 * @date 2015年4月29日
 */
package com.hehenian.biz.component.loan.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.loan.dataobject.LoanFeeRuleDo;
import com.hehenian.biz.common.loan.dataobject.LoanSettSchemeDo;
import com.hehenian.biz.component.loan.ILoanSettSchemeComponent;
import com.hehenian.biz.dal.loan.IManagerLoanFeeRuleDao;
import com.hehenian.biz.dal.loan.IManagerLoanSettSchemeDao;

/**
 * @author liminglmf
 *
 */
@Component("loanSettSchemeComponent")
public class LoanSettSchemeComponentImpl implements ILoanSettSchemeComponent{
	
	@Autowired
    private IManagerLoanSettSchemeDao managerLoanSettSchemeDao;
    @Autowired
    private IManagerLoanFeeRuleDao    managerLoanFeeRuleDao;

    @Override
    public LoanSettSchemeDo getById(Long id) {
        LoanSettSchemeDo loanSettSchemeDo = managerLoanSettSchemeDao.getById(id);
        if (loanSettSchemeDo == null) {
            return loanSettSchemeDo;
        }
        loanSettSchemeDo.setLoanFeeRuleDoList(managerLoanFeeRuleDao.queryBySchemeId(loanSettSchemeDo.getId()));
        return loanSettSchemeDo;
    }

    @Override
    public LoanSettSchemeDo getByCode(String code) {
    	LoanSettSchemeDo loanSettSchemeDo = managerLoanSettSchemeDao.getByCode(code);
        if (loanSettSchemeDo == null) {
            return loanSettSchemeDo;
        }
        loanSettSchemeDo.setLoanFeeRuleDoList(managerLoanFeeRuleDao.queryBySchemeId(loanSettSchemeDo.getId()));
        return loanSettSchemeDo;
    }

    @Override
    public LoanFeeRuleDo getBySchemeIdAndType(Long schemeId, String ruleType) {
        return managerLoanFeeRuleDao.getBySchemeIdAndType(schemeId, ruleType);
    }

    @Override
    public int countList(Map<String, Object> searchItems) {
        return managerLoanSettSchemeDao.countList(searchItems);
    }

    @Override
    public List<LoanSettSchemeDo> queryList(Map<String, Object> searchItems) {
        return managerLoanSettSchemeDao.queryList(searchItems);
    }

    @Override
    public int add(LoanSettSchemeDo loanSettSchemeDo) {
        int count = managerLoanSettSchemeDao.add(loanSettSchemeDo);
        if (loanSettSchemeDo.getLoanFeeRuleDoList() == null || loanSettSchemeDo.getLoanFeeRuleDoList().size() == 0) {
            return count;
        }
        for (LoanFeeRuleDo loanFeeRuleDo : loanSettSchemeDo.getLoanFeeRuleDoList()) {
            loanFeeRuleDo.setSchemeId(loanSettSchemeDo.getId());
            managerLoanFeeRuleDao.add(loanFeeRuleDo);
        }
        return count;
    }

    @Override
    public int update(LoanSettSchemeDo loanSettSchemeDo) {
        int count = managerLoanSettSchemeDao.update(loanSettSchemeDo);
        managerLoanFeeRuleDao.deleteBySchemeId(loanSettSchemeDo.getId());
        if (loanSettSchemeDo.getLoanFeeRuleDoList() == null || loanSettSchemeDo.getLoanFeeRuleDoList().size() == 0) {
            return count;
        }
        for (LoanFeeRuleDo loanFeeRuleDo : loanSettSchemeDo.getLoanFeeRuleDoList()) {
        	loanFeeRuleDo.setSchemeId(loanSettSchemeDo.getId());
            managerLoanFeeRuleDao.add(loanFeeRuleDo);
        }
        return count;
    }

    @Override
    public int deleteById(Long id) {
    	managerLoanFeeRuleDao.deleteBySchemeId(id);// 删除费用规则信息
        return managerLoanSettSchemeDao.deleteById(id);

    }
    
    /**
     * 查询出产品对应的方案并查询出方案对应的费用规则
     */
	@Override
	public List<LoanSettSchemeDo> queryByProdId(Long prodId) {
		List<LoanSettSchemeDo> list = managerLoanSettSchemeDao.queryByProdId(prodId);
		if(list != null && list.size() >0){
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				LoanSettSchemeDo loanSettSchemeDo = (LoanSettSchemeDo) iterator.next();
				loanSettSchemeDo.setLoanFeeRuleDoList(managerLoanFeeRuleDao.queryBySchemeId(loanSettSchemeDo.getId()));
			}
		}
        return list;
	}

	@Override
	public int deleteByIds(List<Long> idsList) {
		return managerLoanSettSchemeDao.deleteByIds(idsList);
	}

	@Override
	public List<LoanSettSchemeDo> queryLoanSettPage(Map<String, Object> searchItems) {
		List<LoanSettSchemeDo> list = managerLoanSettSchemeDao.queryPage(searchItems);
		if(list != null && list.size() >0){
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				LoanSettSchemeDo loanSettSchemeDo = (LoanSettSchemeDo) iterator.next();
				loanSettSchemeDo.setLoanFeeRuleDoList(managerLoanFeeRuleDao.queryBySchemeId(loanSettSchemeDo.getId()));
			}
		}
		return list;
	}
	
}
