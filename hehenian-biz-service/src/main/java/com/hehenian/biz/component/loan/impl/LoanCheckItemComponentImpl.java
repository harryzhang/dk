/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2015
 */

package com.hehenian.biz.component.loan.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.loan.dataobject.LoanCheckDetailDo;
import com.hehenian.biz.common.loan.dataobject.LoanCheckItemDo;
import com.hehenian.biz.component.loan.ILoanCheckItemComponent;
import com.hehenian.biz.dal.loan.ILoanCheckDetailDao;
import com.hehenian.biz.dal.loan.ILoanCheckItemDao;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


@Component("loanCheckItemComponent")
public class LoanCheckItemComponentImpl implements ILoanCheckItemComponent {

	@Autowired
    private ILoanCheckItemDao  loanCheckItemDao;
	
	@Autowired
    private ILoanCheckDetailDao loanCheckDetailDao;

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public LoanCheckItemDo getById(Long id){
	  return loanCheckItemDao.getById(id);
	}
	
	/**
	 *根据条件查询列表
	 */
	public List<LoanCheckItemDo> selectLoanCheckItem(Map<String,Object> parameterMap){
		return loanCheckItemDao.selectLoanCheckItem(parameterMap);
	}
	
	/**
	 * 更新
	 */
	public int  updateLoanCheckItemById(LoanCheckItemDo newLoanCheckItemDo){
		int result = loanCheckItemDao.updateLoanCheckItemById(newLoanCheckItemDo);
		if(result < 1){
			throw new BusinessException("更新失败");
		}
		return result;
	}
	
	/**
	 * 新增
	 */
	public int addLoanCheckItem(LoanCheckItemDo newLoanCheckItemDo){
		return loanCheckItemDao.addLoanCheckItem(newLoanCheckItemDo);
	}
	
	/**
	 * 删除
	 */
	public int deleteById(Long id){
		return loanCheckItemDao.deleteById(id);
	}

	@Override
	public List<LoanCheckItemDo> getCheckItemPage(Map<String, Object> param) {
		return loanCheckItemDao.getCheckItemPage(param);
	}

	@Override
	public List<LoanCheckDetailDo> getCheckDetailPage(Map<String, Object> param) {
		return loanCheckDetailDao.getCheckDetailPage(param);
	}

	@Override
	public LoanCheckDetailDo getCheckDetailById(Long id) {
		return loanCheckDetailDao.getById(id);
	}

	@Override
	public int updateLoanCheckDetailById(LoanCheckDetailDo loanCheckDetailDo) {
		return loanCheckDetailDao.updateLoanCheckDetailById(loanCheckDetailDo);
	}

	@Override
	public int addLoanCheckDetail(LoanCheckDetailDo loanCheckDetailDo) {
		return loanCheckDetailDao.addLoanCheckDetail(loanCheckDetailDo);
	}

	@Override
	public int deleteCheckDetailById(Long id) {
		return loanCheckDetailDao.deleteById(id);
	}

	@Override
	public int deleteCheckDetailIds(List<Long> idsList) {
		return loanCheckDetailDao.deleteByIds(idsList);
	}

	/** (non-Javadoc)
	 * @see com.hehenian.biz.component.loan.ILoanCheckItemComponent#countDetailByCheckId(java.lang.Long)
	 */
	@Override
	public int countDetailByCheckId(Long id) {
		return loanCheckDetailDao.countDetailByCheckId(id);
	}
	
}
