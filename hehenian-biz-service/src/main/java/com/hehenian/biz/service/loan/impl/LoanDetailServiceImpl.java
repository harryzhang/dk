/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.service.loan.impl
 * @Title: LoanDetailServiceImpl.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年12月10日 下午7:03:07
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.service.loan.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hehenian.biz.common.account.dataobject.BankCardDo;
import com.hehenian.biz.common.base.dataobject.NPageDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.base.result.ResultSupport;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.loan.ILoanDetailService;
import com.hehenian.biz.common.loan.dataobject.LoanDetailDo;
import com.hehenian.biz.common.loan.dataobject.LoanDetailDo.LoanStatus;
import com.hehenian.biz.common.trade.dataobject.BorrowDo;
import com.hehenian.biz.component.account.IBankCardComponent;
import com.hehenian.biz.component.account.IUserComponent;
import com.hehenian.biz.component.loan.ILoanDetailComponent;
import com.hehenian.biz.component.trade.IBorrowComponent;

/**
 * 
 * @author: liuzgmf
 * @date 2014年12月10日 下午7:03:07
 */
@Service("loanDetailService")
public class LoanDetailServiceImpl implements ILoanDetailService {
    private final Logger         logger = Logger.getLogger(this.getClass());
    @Autowired
    private ILoanDetailComponent loanDetailComponent;
    @Autowired
    private IUserComponent       userComponent;
    @Autowired
    private IBankCardComponent   bankCardComponent;
    @Autowired
    private IBorrowComponent     borrowComponent;

    @Override
    public IResult<?> addLoanDetail(LoanDetailDo loanDetailDo) {
        IResult<String> result = new ResultSupport<String>(true);
        try {
            loanDetailDo.setLoanStatus(LoanStatus.PROCESSING);
            loanDetailComponent.addLoanDetail(loanDetailDo);
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setErrorMessage(e.getMessage());
            logger.error(e.getMessage() + "，输入参数：" + loanDetailDo.toString());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("操作失败,请稍后再试!");
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public IResult<?> changeLoanStatus(Long loanId, LoanStatus loanStatus) {
        IResult<String> result = new ResultSupport<String>(true);
        try {
            loanDetailComponent.changeLoanStatus(loanId, loanStatus);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("操作失败,请稍后再试!");
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public IResult<?> updateLoanDetail(List<LoanDetailDo> loanDetailDoList) {
        IResult<String> result = new ResultSupport<String>(true);
        try {
            loanDetailComponent.updateLoanDetail(loanDetailDoList);
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setErrorMessage(e.getMessage());
            logger.error(e.getMessage() + "，输入参数：" + loanDetailDoList.toString());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("操作失败,请稍后再试!");
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public NPageDo<LoanDetailDo> queryLoanDetails(Map<String, Object> searchItems) {
        NPageDo<LoanDetailDo> pageDo = new NPageDo<LoanDetailDo>();
        try {
            long count = loanDetailComponent.countLoanDetails(searchItems);
            pageDo.setTotalCount(count);
            if (count == 0) {
                return pageDo;
            }
            List<LoanDetailDo> loanDetailDoList = loanDetailComponent.queryLoanDetails(searchItems);
            pageDo.setModelList(loanDetailDoList);

            List<Long> userIdList = new ArrayList<Long>();
            for (LoanDetailDo loanDetailDo : loanDetailDoList) {
                if (loanDetailDo.getUserId() != null) {
                    userIdList.add(loanDetailDo.getUserId());
                }
            }

            // 查询银行卡信息
            List<BankCardDo> bankCardDoList = bankCardComponent.queryByUserIds(userIdList);
            for (LoanDetailDo loanDetailDo : loanDetailDoList) {
                for (BankCardDo bankCardDo : bankCardDoList) {
                    if (loanDetailDo.getUserId() != null
                            && loanDetailDo.getUserId().longValue() == bankCardDo.getUserId().longValue()) {
                        loanDetailDo.setBankCardDo(bankCardDo);
                        break;
                    }
                }
            }

            return pageDo;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            pageDo.setTotalCount(0l);
            return pageDo;
        }
    }

    @Override
    public LoanDetailDo getByIdNo(String idNo) {
        LoanDetailDo loanDetailDo = loanDetailComponent.getByIdNo(idNo);
        if(!(null==loanDetailDo)){
	        if (loanDetailDo.getLoanStatus().equals(LoanStatus.CHECKED)) {
	            BorrowDo borrowDo = borrowComponent.getByIdNo(idNo);
	            if (borrowDo != null && (borrowDo.getBorrowStatus().intValue() == 4 || borrowDo.getBorrowStatus().intValue() == 5)) {
	                loanDetailDo.setLoanStatus(LoanStatus.LOANS);
	                loanDetailDo.setBorrowDo(borrowDo);
	            }
	        }
	    }
        return loanDetailDo;
    }
    
    @Override
    public LoanDetailDo getByIdNoGroup(String idNo) {
        LoanDetailDo loanDetailDo = loanDetailComponent.getByIdNoGroup(idNo);
        if(!(null==loanDetailDo)){
	        if (loanDetailDo.getLoanStatus().equals(LoanStatus.CHECKED)) {
	            BorrowDo borrowDo = borrowComponent.getByIdNo(idNo);
	            if (borrowDo != null && (borrowDo.getBorrowStatus().intValue() == 4 || borrowDo.getBorrowStatus().intValue() == 5)) {
	                loanDetailDo.setLoanStatus(LoanStatus.LOANS);
	                loanDetailDo.setBorrowDo(borrowDo);
	            }
	        }
	    }
        return loanDetailDo;
    }
    @Override
    public List<LoanDetailDo> queryByLoanStatus(LoanStatus loanStatus) {
        return loanDetailComponent.queryByLoanStatus(loanStatus);
    }

}
