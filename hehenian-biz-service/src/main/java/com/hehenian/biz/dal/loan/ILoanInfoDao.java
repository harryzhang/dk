/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.dal.loan
 * @Title: ILoanInfoDao.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年4月20日 下午2:56:50
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.dal.loan;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hehenian.biz.common.loan.dataobject.LoanInfoDo;

/**
 * 
 * @author: liuzgmf
 * @date 2015年4月20日 下午2:56:50
 */
public interface ILoanInfoDao {
    /**
     * 新增借款信息
     * 
     * @param loanInfoDo
     * @return
     * @author: liuzgmf
     * @date: 2015年4月20日下午2:14:28
     */
    int addLoanInfo(LoanInfoDo loanInfoDo);

    /**
     * 修改借款信息
     * 
     * @param loanInfoDo
     * @return
     * @author: liuzgmf
     * @date: 2015年4月20日下午2:14:28
     */
    int updateLoanInfo(LoanInfoDo loanInfoDo);

    /**
     * 根据查询条件查询借款标的信息
     * 
     * @param searchItems
     * @return
     * @author: liuzgmf
     * @date: 2015年4月20日下午3:01:10
     */
    List<LoanInfoDo> queryLoanInfos(Map<String, Object> searchItems);

    /**
     * 根据条件查询借款标的记录数
     * 
     * @param searchItems
     * @return
     * @author: liuzgmf
     * @date: 2015年4月20日下午3:59:35
     */
    long countLoanInfo(Map<String, Object> searchItems);

    /**
     * 根据ID查询借款标的信息
     * 
     * @param loanInfoId
     * @return
     * @author: liuzgmf
     * @date: 2015年4月20日下午3:01:25
     */
    LoanInfoDo getByLoanInfoId(Long loanInfoId);

    /**
     * 根据借款标的ID查询借款标的信息
     * 
     * @param loanInfoIdList
     * @return
     * @author: liuzgmf
     * @date: 2015年4月21日下午2:43:35
     */
    List<LoanInfoDo> queryByLoanInfoIds(@Param("loanInfoIdList") List<Long> loanInfoIdList);
}
