package com.hehenian.biz.component.loan;

import com.hehenian.biz.common.loan.dataobject.JobDo;

public interface ILoanJobComponent {
    /**
     * 修改职业信息
     * @param jobDo
     */
    void updateJob(JobDo jobDo);
    
    /**
     * 新增职业信息
     * @param jobDo
     */
    void addJob(JobDo jobDo);
    
    /**
     * 根据ID查询表的记录
     * @return
     */
	JobDo getJobByIds(String id);
}
