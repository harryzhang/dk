/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.loan
 * @Title: ILoanDetailComponent.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年12月11日 上午9:54:53
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.loan.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.loan.dataobject.JobDo;
import com.hehenian.biz.component.loan.ILoanJobComponent;
import com.hehenian.biz.dal.loan.IJobDao;

/**
 * @author xiexiangmf
 *
 */
@Component("loanJobComponent")
public class LoanJobComponentImpl implements ILoanJobComponent{
	
    @Autowired
    private IJobDao jobDao; 

	@Override
	public JobDo getJobByIds(String id) {
		return jobDao.getJobByIds(id);
	}

	@Override
	public void updateJob(JobDo jobDo) {
		jobDao.updateJob(jobDo);
	}

	@Override
	public void addJob(JobDo jobDo) {
		jobDao.addJob(jobDo);
	}

}
