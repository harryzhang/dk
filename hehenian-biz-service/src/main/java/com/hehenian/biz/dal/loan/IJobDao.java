/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.dal.loan
 * @Title: IJobDao.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月19日 下午3:43:53
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.dal.loan;

import com.hehenian.biz.common.loan.dataobject.JobDo;

/** 
 *  
 * @author: liuzgmf
 * @date 2015年1月19日 下午3:43:53
 */
public interface IJobDao {

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
