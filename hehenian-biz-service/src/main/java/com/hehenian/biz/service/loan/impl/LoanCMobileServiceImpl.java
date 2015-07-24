package com.hehenian.biz.service.loan.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.base.dataobject.PageDo;
import com.hehenian.biz.common.loan.ILoanCMobileService;
import com.hehenian.biz.common.loan.dataobject.LoanCMobileDo;
import com.hehenian.biz.component.loan.ILoanCMobileComponent;
import com.hehenian.lend.manager.common.contant.Constants;

/**
 * @author zhengyfmf
s */
@Service("loanCMobileService")
public class LoanCMobileServiceImpl implements ILoanCMobileService{

	@Autowired
	private ILoanCMobileComponent loanCMobileComponent;
	
	@Override
	public int addLoanCMobile(LoanCMobileDo loanCMobileDo) {
		return loanCMobileComponent.addLoanCMobile(loanCMobileDo);
	}

	@Override
	public int updateLoanCMoblie(LoanCMobileDo loanCMobileDo) {
		return loanCMobileComponent.updateLoanCMoblie(loanCMobileDo);
	}

	@Override
	public LoanCMobileDo getById(Integer id) {
		return loanCMobileComponent.getById(id);
	}

	@Override
	public PageDo<LoanCMobileDo> getLoanCMobilePage(Map<String, Object> param, PageDo<LoanCMobileDo> page){
		param.put(Constants.MYBATIS_PAGE, page);
		List<LoanCMobileDo> list;
		list = loanCMobileComponent.getLoanCMobilePage(param);
		if(list != null){
			for(int i=0 ;i<list.size();i++){
				list.get(i).setNum(i+1);
			}
			page.setPage(list);
		}
		return page;
	}

	@Override
	public int deleteLoanCMobileById(Integer id) {
		return loanCMobileComponent.deleteLoanCMobileById(id);
	}

	@Override
	public List<LoanCMobileDo> getAllBusinessDept() {
		return loanCMobileComponent.getAllBusinessDept();
	}

}
