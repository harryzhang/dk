package com.hehenian.biz.dal.loan;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hehenian.biz.common.loan.dataobject.LoanProductDo;

public interface IManagerLoanProductDao {

	public List<LoanProductDo> queryLoanProductList(Map<String,Object> searchItem );
	
	public List<LoanProductDo> queryLoanProductPage(Map<String,Object> searchItem );

	public int addLoanProductDo(LoanProductDo loanProductDo);

	public LoanProductDo getProdById(Long id);

	public int updateProd(LoanProductDo loanProductDo);
	
	
	public int deleteByIds(@Param("ids")List<Long> ids);

}
