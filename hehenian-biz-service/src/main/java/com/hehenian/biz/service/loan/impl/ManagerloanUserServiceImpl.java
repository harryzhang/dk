package com.hehenian.biz.service.loan.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.loan.IManagerLoanUserService;
import com.hehenian.biz.common.loan.dataobject.LoanUserDo;
import com.hehenian.biz.component.loan.IManagerLoanUserComponent;

@Service("managerLoanUserService")
public class ManagerloanUserServiceImpl implements IManagerLoanUserService{
	
	@Autowired
	private IManagerLoanUserComponent managerLoanUserComponent;
	
	@Override
	public LoanUserDo getById(Long loanUserId) {
		return null;
	}

	@Override
	public LoanUserDo getBySourceUserId(String sourceUserId) {
		return managerLoanUserComponent.getBySourceUserId(sourceUserId);
	}

	@Override
	public List<LoanUserDo> selectLoanUser(Map<String, Object> parameterMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateLoanUserById(LoanUserDo newLoanUserDo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addLoanUser(LoanUserDo newLoanUserDo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(Long loanUserId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
