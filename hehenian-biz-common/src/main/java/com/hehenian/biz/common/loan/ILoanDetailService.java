/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.loan
 * @Title: ILoanDetailService.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年12月10日 下午6:58:32
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.loan;

import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.base.dataobject.NPageDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.loan.dataobject.LoanDetailDo;
import com.hehenian.biz.common.loan.dataobject.LoanDetailDo.LoanStatus;

/**
 * 
 * @author: liuzgmf
 * @date 2014年12月10日 下午6:58:32
 */
public interface ILoanDetailService {

    /**
     * 新增借款申请信息
     * 
     * @param loanDetailDo
     * @return
     * @author: liuzgmf
     * @date: 2014年12月10日下午6:59:12
     */
    IResult<?> addLoanDetail(LoanDetailDo loanDetailDo);

    /**
     * 修改借款申请信息
     * 
     * @param loanDetailDoList
     * @return
     * @author: liuzgmf
     * @date: 2014年12月11日上午9:51:54
     */
    IResult<?> updateLoanDetail(List<LoanDetailDo> loanDetailDoList);

    /**
     * 根据条件查询借款申请信息
     * 
     * @param searchItems
     * @return
     * @author: liuzgmf
     * @date: 2014年12月10日下午7:01:33
     */
    NPageDo<LoanDetailDo> queryLoanDetails(Map<String, Object> searchItems);

    /**
     * 根据借款状态查询借款申请信息
     * 
     * @param loanStatus
     * @return
     * @author: liuzgmf
     * @date: 2014年12月10日下午7:14:06
     */
    List<LoanDetailDo> queryByLoanStatus(LoanStatus loanStatus);

    /**
     * 修改借款申请借款的状态
     * 
     * @param loanId
     * @param loanStatus
     * @return
     * @author: liuzgmf
     * @date: 2014年12月16日上午11:17:18
     */
    IResult<?> changeLoanStatus(Long loanId, LoanStatus loanStatus);

    /**
     * 根据身份证号查询借款申请信息
     * 
     * @param idNo
     * @return
     * @author: liuzgmf
     * @date: 2014年12月16日下午7:29:04
     */
    LoanDetailDo getByIdNo(String idNo);
    /**
     * 根据身份证号查询借款申请信息
     * 
     * @param idNo
     * @return
     * @author: huangzlmf
     * @date: 2015年4月21日 17:13:12
     */
	LoanDetailDo getByIdNoGroup(String idNo);

}
