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
package com.hehenian.biz.component.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.loan.dataobject.LoanPersonDo;

/**
 * 
 * @author xiexiangmf
 *
 */
public interface IJobComponent {

    /**
     * 修改借款人的信息
     * @param loanPersonDoList
     */
    void updateLoanPerson(LoanPersonDo loanPersonDo);

    /**
     * 根据条件查询借款人信息
     * @param searchItems
     * @return
     */
    List<LoanPersonDo> queryLoanPerson(Map<String, Object> searchItems);

    /**
     * 根据ID查询表的记录
     * @return
     */
    int getCountByIds(Long id);
}
