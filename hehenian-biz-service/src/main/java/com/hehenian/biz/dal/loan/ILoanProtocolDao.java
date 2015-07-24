package com.hehenian.biz.dal.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.loan.dataobject.LoanProtocolDo;

public interface ILoanProtocolDao {

	/**
	 * 根据ID 查询
	 * @parameter id
	 */
	public LoanProtocolDo getById(int id);
	
	/**
	 *根据条件查询列表
	 */
	public List<LoanProtocolDo> selectLoanProtocol(Map<String,Object> parameterMap);
	
	/**
	 * 更新
	 */
	public int  updateLoanProtocolById(LoanProtocolDo newLoanProtocolDo);
	
	/**
	 * 新增
	 */
	public int addLoanProtocol(LoanProtocolDo newLoanProtocolDo);
	
	/**
	 * 删除
	 */
	public int deleteById(int id);

}
