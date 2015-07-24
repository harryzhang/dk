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

import com.hehenian.biz.common.loan.dataobject.DepositLoanDetailDo;
import com.hehenian.biz.common.loan.dataobject.FundProductDo;
import com.hehenian.biz.common.loan.dataobject.FundUserAccountDo;
import com.hehenian.biz.common.loan.dataobject.LoanDetailDo;
import com.hehenian.biz.common.loan.dataobject.LoanDetailDo.LoanStatus;

/**
 * 
 * @author: liuzgmf
 * @date 2014年12月11日 上午9:54:53
 */
public interface ILoanDetailComponent {
    /**
     * 新增借款申请信息
     * 
     * @param loanDetailDo
     * @return
     * @author: liuzgmf
     * @date: 2014年12月10日下午6:59:12
     */
    Long addLoanDetail(LoanDetailDo loanDetailDo);

    /**
     * 根据人事系统反馈修改借款申请信息
     * 
     * @param loanDetailDoList
     * @return
     * @author: liuzgmf
     * @date: 2014年12月11日上午9:51:54
     */
    void updateLoanDetail(List<LoanDetailDo> loanDetailDoList);

    /**
     * 根据条件查询借款申请信息
     * 
     * @param searchItems
     * @return
     * @author: liuzgmf
     * @date: 2014年12月10日下午7:01:33
     */
    List<LoanDetailDo> queryLoanDetails(Map<String, Object> searchItems);

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
     * 根据条件查询借款申请记录数
     * 
     * @param searchItems
     * @return
     * @author: liuzgmf
     * @date: 2014年12月11日上午10:10:40
     */
    long countLoanDetails(Map<String, Object> searchItems);

    /**
     * 修改借款申请借款的状态
     * 
     * @param loanId
     * @param loanStatus
     * @return
     * @author: liuzgmf
     * @date: 2014年12月16日上午11:18:10
     */
    int changeLoanStatus(Long loanId, LoanStatus loanStatus);

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
     * @date: 2015年4月21日 17:34:37
     */
	LoanDetailDo getByIdNoGroup(String idNo);
    
    /**
     * 插入定存系统借款详细信息
     * @param loanDetailDo
     * @return
     */
    Long addDepositLoanDetail(DepositLoanDetailDo loanDetailDo);
    
    /**
     * 插入定存系统投资产品详情
     * @param prd
     */
    void addFundProduct(FundProductDo prd);
    
    /**
     * 插入定存系统虚拟账户
     * @param account
     */
    void addFundUserAccount(FundUserAccountDo account);
    
    /**
     * 判断是否存在虚拟资金账户
     * @param userId
     * @return
     */
    int existUserAccount(Long userId);
}
