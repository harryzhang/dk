/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2015
 */

package com.hehenian.biz.service.loan.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.biz.common.contant.Constants;
import com.hehenian.biz.common.loan.ILoanCheckItemService;
import com.hehenian.biz.common.loan.dataobject.LoanCheckDetailDo;
import com.hehenian.biz.common.loan.dataobject.LoanCheckItemDo;
import com.hehenian.biz.component.loan.ILoanCheckItemComponent;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


@Service("loanCheckItemService")
public class LoanCheckItemServiceImpl implements ILoanCheckItemService {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired	
    private ILoanCheckItemComponent  loanCheckItemComponent;

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public LoanCheckItemDo getById(Long id){
	  return loanCheckItemComponent.getById(id);
	}
	
	/**
	 *根据条件查询列表
	 */
	public List<LoanCheckItemDo> selectLoanCheckItem(Map<String,Object> parameterMap){
		return loanCheckItemComponent.selectLoanCheckItem(parameterMap);
	}
	
	/**
	 * 更新
	 */
	public int  updateLoanCheckItemById(LoanCheckItemDo newLoanCheckItemDo){
		return loanCheckItemComponent.updateLoanCheckItemById(newLoanCheckItemDo);
	}
	
	/**
	 * 新增
	 */
	public int addLoanCheckItem(LoanCheckItemDo newLoanCheckItemDo){
		return loanCheckItemComponent.addLoanCheckItem(newLoanCheckItemDo);
	}
	
	/**
	 * 删除
	 */
	public int deleteById(Long id){
		return loanCheckItemComponent.deleteById(id);
	}

	
	
	/** (non-Javadoc)
	 * @see com.hehenian.biz.common.loan.ILoanCheckItemService#getCheckItemPage(java.util.Map, com.hehenian.biz.common.base.dataobject.PageDo)
	 */
	@Override
	public PageDo<LoanCheckItemDo> getCheckItemPage(Map<String, Object> param,
			PageDo<LoanCheckItemDo> page) {
		param.put(Constants.MYBATIS_PAGE, page);
		List<LoanCheckItemDo> list = loanCheckItemComponent.getCheckItemPage(param);
		page.setPage(list);
		return page;
	}

	/** (non-Javadoc)
	 * @see com.hehenian.biz.common.loan.ILoanCheckItemService#getCheckDetailPage(java.util.Map, com.hehenian.biz.common.base.dataobject.PageDo)
	 */
	@Override
	public PageDo<LoanCheckDetailDo> getCheckDetailPage(
			Map<String, Object> param, PageDo<LoanCheckDetailDo> page) {
		param.put(Constants.MYBATIS_PAGE, page);
		List<LoanCheckDetailDo> list = loanCheckItemComponent.getCheckDetailPage(param);
		page.setPage(list);
		return page;
	}

	@Override
	public LoanCheckDetailDo getCheckDetailById(Long id) {
		return loanCheckItemComponent.getCheckDetailById(id);
	}

	@Override
	public int updateLoanCheckDetailById(LoanCheckDetailDo loanCheckDetailDo) {
		return loanCheckItemComponent.updateLoanCheckDetailById(loanCheckDetailDo);
	}

	@Override
	public int addLoanCheckDetail(LoanCheckDetailDo loanCheckDetailDo) {
		return loanCheckItemComponent.addLoanCheckDetail(loanCheckDetailDo);
	}

	@Override
	public int deleteCheckDetailById(Long id) {
		return loanCheckItemComponent.deleteCheckDetailById(id);
	}

	
	/** (non-Javadoc)
	 * @see com.hehenian.biz.common.loan.ILoanCheckItemService#deleteCheckDetailIds(java.lang.String)
	 */
	@Override
	public int deleteCheckDetailIds(String ids) {
		Long[] longArr = stringToLongArr(ids);
		List<Long> idsList = Arrays.asList(longArr);
		return loanCheckItemComponent.deleteCheckDetailIds(idsList);
	}

	private Long[] stringToLongArr(String ids){
		String[] arr = ids.split(",");
		Long[] longArr = new Long[arr.length];
		for (int i = 0; i < longArr.length; i++) {
			longArr[i] = Long.parseLong(arr[i]);
		}
		return longArr;
	}

	/** (non-Javadoc)
	 * @see com.hehenian.biz.common.loan.ILoanCheckItemService#countDetailByCheckId(java.lang.Long)
	 */
	@Override
	public int countDetailByCheckId(Long id) {
		return loanCheckItemComponent.countDetailByCheckId(id);
	}
}
