/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2015
 */

package com.hehenian.biz.service.loan.impl;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.biz.common.loan.ILoanRepaymentService;
import com.hehenian.biz.common.loan.dataobject.LoanRepaymentDo;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.common.util.DateUtils;
import com.hehenian.biz.component.loan.ILoanRepaymentComponent;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */


@Service("loanRepaymentService")
public class LoanRepaymentServiceImpl implements ILoanRepaymentService {

	@SuppressWarnings("unused")
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired	
    private ILoanRepaymentComponent  loanRepaymentComponent;

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public LoanRepaymentDo getById(Long repaymentId){
	  return loanRepaymentComponent.getById(repaymentId);
	}
	
	/**
	 *根据条件查询列表
	 */
	public List<LoanRepaymentDo> selectLoanRepayment(Map<String,Object> parameterMap){
		return loanRepaymentComponent.selectLoanRepayment(parameterMap);
	}
	
	/**
	 * 逾期、待还款、已还款、已还清 列表查询
	 * @param parameterMap
	 * @return
	 */
	public List<Map<String,Object>> listRepayment(Map<String,Object> parameterMap){
		DecimalFormat myformat = new DecimalFormat();
		myformat.applyPattern("##,###.00");
		List<Map<String,Object>> list =loanRepaymentComponent.listRepayment(parameterMap);
		if(list != null && list.size()>0){
			for(Map<String,Object> map :list){
				map.put("stillPrincipal", myformat.format(map.get("stillPrincipal")==null?0:map.get("stillPrincipal")));
				map.put("applyAmount", myformat.format(map.get("applyAmount")==null?0:map.get("applyAmount")));
				try {
					if(map.get("loanTime")!=null){
						map.put("loanTime", DateUtils.formatDate(DateUtils.parseTime(map.get("loanTime").toString())));
					}
					if(map.get("repayDate")!=null){
						map.put("repayDate", DateUtils.formatDate(DateUtils.parseTime(map.get("repayDate").toString())));
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	public List<Map<String,Object>> getByLoanStatus (Map<String,Object> parameterMap){
		DecimalFormat myformat = new DecimalFormat();
		myformat.applyPattern("##,###.00");
		List<Map<String,Object>> list =loanRepaymentComponent.getByLoanStatus(parameterMap);
		if(list != null && list.size()>0){
			for(Map<String,Object> map :list){
				map.put("applyAmount", myformat.format(map.get("applyAmount")));
				try {
					map.put("loanTime", DateUtils.formatDate(DateUtils.parseTime(map.get("loanTime").toString())));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	
	public Map<String,Object> getRepayDetail(Map<String,Object> parameterMap){
		Map<String,Object> map = loanRepaymentComponent.getRepayDetail(parameterMap);
		if(map != null){
			DecimalFormat myformat = new DecimalFormat();
			myformat.applyPattern("##,###.00");
			try {
				Double yhbx = CalculateUtils.add(Double.valueOf(map.get("stillPrincipal").toString()), Double.valueOf(map.get("stillInterest").toString()));
				map.put("yhbx", myformat.format(yhbx));
				Double yhzje = CalculateUtils.add(Double.valueOf(map.get("lateFI").toString()), CalculateUtils.add(Double.valueOf(map.get("stillPrincipal").toString()), Double.valueOf(map.get("stillInterest").toString())));
				map.put("yhzje", myformat.format(yhzje));
				map.put("repayDate", DateUtils.formatDate(DateUtils.parseTime(map.get("repayDate").toString())));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	/**
	 * 更新
	 */
	public int  updateLoanRepaymentById(LoanRepaymentDo newLoanRepaymentDo){
		return loanRepaymentComponent.updateLoanRepaymentById(newLoanRepaymentDo);
	}
	
	/**
	 * 新增
	 */
	public int addLoanRepayment(LoanRepaymentDo newLoanRepaymentDo){
		return loanRepaymentComponent.addLoanRepayment(newLoanRepaymentDo);
	}
	
	/**
	 * 删除
	 */
	public int deleteById(Long repaymentId){
		return loanRepaymentComponent.deleteById(repaymentId);
	}

	@Override
	public List<LoanRepaymentDo> queryRepaymentByLoanId(Long loanId) {
		 
		return loanRepaymentComponent.queryRepaymentByLoanId(loanId);
	}

	@Override
	public PageDo<Map<String, Object>> getLoanRepaymentList(Map<String, Object> searchItems, PageDo<Map<String, Object>> page) {
		searchItems.put("page", page);
		List<Map<String, Object>> list =  loanRepaymentComponent.listRepaymentPage(searchItems);
		DecimalFormat myformat = new DecimalFormat();
		myformat.applyPattern("##,###.00");
		if(list != null && list.size()>0){
			for(Map<String,Object> map :list){
				map.put("stillPrincipal", myformat.format(map.get("stillPrincipal")==null?0:map.get("stillPrincipal")));
				map.put("applyAmount", myformat.format(map.get("applyAmount")==null?0:map.get("applyAmount")));
				try {
					if(map.get("loanTime")!=null){
						map.put("loanTime", DateUtils.formatDate(DateUtils.parseTime(map.get("loanTime").toString())));
					}
					if(map.get("repayDate")!=null){
						map.put("repayDate", DateUtils.formatDate(DateUtils.parseTime(map.get("repayDate").toString())));
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		page.setPage(list);
		return page;
	}
	
}
