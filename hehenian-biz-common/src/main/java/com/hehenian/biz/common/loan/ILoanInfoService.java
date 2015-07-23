/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.loan
 * @Title: ILoanInfoService.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年4月20日 下午2:13:08
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.base.dataobject.NPageDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.loan.dataobject.LoanInfoDo;

/**
 * 
 * @author: liuzgmf
 * @date 2015年4月20日 下午2:13:08
 */
public interface ILoanInfoService {

    /**
     * 新增借款信息
     * 
     * @param loanInfoDoList
     * @return
     * @author: liuzgmf
     * @date: 2015年4月20日下午2:14:28
     */
    IResult<?> addLoanInfo(List<LoanInfoDo> loanInfoDoList);

    /**
     * 修改借款信息
     * 
     * @param loanInfoDo
     * @return
     * @author: liuzgmf
     * @date: 2015年4月20日下午2:14:28
     */
    IResult<?> updateLoanInfo(LoanInfoDo loanInfoDo);

    /**
     * 根据查询条件查询借款标的信息
     * 
     * @param searchItems
     * @return
     * @author: liuzgmf
     * @date: 2015年4月20日下午3:01:10
     */
    NPageDo<LoanInfoDo> queryLoanInfos(Map<String, Object> searchItems);

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
    List<LoanInfoDo> queryByLoanInfoIds(List<Long> loanInfoIdList);

    /**
     * 发布借款标的到定存系统
     * 
     * @param loanInfoIdList
     * @return
     */
    IResult<?> addLoanDetail(List<Long> loanInfoIdList);
}
