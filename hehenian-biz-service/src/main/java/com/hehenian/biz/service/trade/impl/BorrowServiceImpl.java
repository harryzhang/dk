package com.hehenian.biz.service.trade.impl;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.account.dataobject.PersonDo;
import com.hehenian.biz.common.base.dataobject.NPageDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.base.result.ResultSupport;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.trade.IBorrowService;
import com.hehenian.biz.common.trade.dataobject.BorrowDo;
import com.hehenian.biz.common.trade.dataobject.InvestDo;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.common.util.MoneyUtil;
import com.hehenian.biz.component.account.IBankCardComponent;
import com.hehenian.biz.component.account.IPersonComponent;
import com.hehenian.biz.component.account.IUserComponent;
import com.hehenian.biz.component.trade.IBorrowComponent;
import com.hehenian.biz.component.trade.IInvestComponent;
import com.hehenian.biz.component.trade.IReconciliationComponent;
import com.hehenian.biz.facade.account.AccountType;
import com.hehenian.biz.facade.account.IAccountManagerService;
import com.hehenian.biz.facade.account.parameter.InParameter;
import com.hehenian.biz.facade.account.parameter.OutParameter;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

@Service("borrowService")
public class BorrowServiceImpl implements IBorrowService {
    private final Logger             logger = Logger.getLogger(this.getClass());
    @Autowired
    private IAccountManagerService   accountManagerService;
    @Autowired
    private IBorrowComponent         borrowComponent;
    @Autowired
    private IInvestComponent         investComponent;
    @Autowired
    private IUserComponent           userComponent;
    @Autowired
    private IPersonComponent         personComponent;
    @Autowired
    private IReconciliationComponent reconciliationComponent;
    @Autowired
    private IBankCardComponent       bankCardComponent;

    /**
     * 根据ID 查询
     * 
     * @parameter id
     */
    public BorrowDo getById(Long id) {
        return borrowComponent.getById(id);
    }

    /**
     * 根据条件查询列表
     */
    public List<BorrowDo> selectBorrow(Map<String, Object> parameterMap) {
        return borrowComponent.selectBorrow(parameterMap);
    }

    /**
     * 更新
     */
    public int updateBorrowById(BorrowDo newBorrowDo) {
        return borrowComponent.updateBorrowById(newBorrowDo);
    }

    /**
     * 新增
     */
    public int addBorrow(BorrowDo newBorrowDo) {
        return borrowComponent.addBorrow(newBorrowDo);
    }

    /**
     * 删除
     */
    public int deleteById(Long id) {
        return borrowComponent.deleteById(id);
    }

    @Override
    public IResult<?> updateBorrowFullScale(Long borrowId, Integer borrrowStatus, String auditOpinion, Long adminId) {
        IResult<String> result = new ResultSupport<String>();
        try {
            // 校验标的是否符合放款要求
            result = checkBorrowFullScale(borrowId, borrrowStatus);
            if (!result.isSuccess()) {
                return result;
            }
            // 调用汇付的接口放款给借款人
            lending(borrowId, borrrowStatus, adminId);

            // 平台放款处理
            borrowComponent.updateBorrowFullScale(borrowId, borrrowStatus, adminId);
            result.setSuccess(true);
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setErrorMessage(e.getMessage());
            logger.error("标的[" + borrowId + "]放款失败，" + e.getMessage(), e);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("操作失败,请稍后再试!");
            logger.error("标的[" + borrowId + "]放款失败，" + e.getMessage(), e);
        }
        return result;
    }

    /**
     * 调用汇付的接口放款给借款人
     * 
     * @param borrowId
     * @param borrrowStatus
     * @param adminId
     * @return
     */
    private void lending(Long borrowId, Integer borrrowStatus, Long adminId) {
        // 根据借款ID查询投资记录
        List<InvestDo> investDoList = investComponent.queryByBorrowId(borrowId);
        // 查询借款标的记录
        BorrowDo borrowDo = borrowComponent.getById(borrowId);
        // 查询借款人的用户信息
        AccountUserDo borrowUserDo = userComponent.getById(borrowDo.getPublisher());
        for (int i = 0; i < investDoList.size(); i++) {
            InvestDo investDo = investDoList.get(i);
            // 解冻投资的投资金额
            if (i >= borrowDo.getUnfreeOk()) {
                InParameter inParameter = new InParameter();
                inParameter.setTrxId(investDo.getTrxId() + "");
                OutParameter outParameter = accountManagerService.usrUnFreeze(inParameter, AccountType.CHINAPNR);
                if (!outParameter.isSuccess() && !"重复交易".equals(outParameter.getRespDesc())) {
                    updateUnFreeAndLoanQty(borrowId, i, i);// 标示解冻多少投资
                    throw new BusinessException(outParameter.getRespDesc());
                }
            }
            // 放款投资金额给借款人
            if (i >= borrowDo.getLoansOk()) {
                InParameter inParameter = new InParameter();
                inParameter.setOrdId(investDo.getId() + "");
                inParameter.getParams().put("OrdDate", investDo.getInvestTime());
                AccountUserDo investUserDo = userComponent.getById(investDo.getInvestor());
                inParameter.getParams().put("OutCustId", investUserDo.getUsrCustId());
                inParameter.getParams().put("TransAmt", investDo.getInvestAmount());
                inParameter.getParams().put("Fee", investDo.getManageFee());
                inParameter.getParams().put("InCustId", borrowUserDo.getUsrCustId());
                inParameter.getParams().put("DivDetails", "");
                inParameter.getParams().put("IsDefault", "N");// 是否取现到银行卡
                OutParameter outParameter = accountManagerService.loans(inParameter, AccountType.CHINAPNR);
                if (!outParameter.isSuccess() && !"重复的放款请求".equals(outParameter.getRespDesc())) {
                    updateUnFreeAndLoanQty(borrowId, i + 1, i);// 标示放款了多少笔投资
                    throw new BusinessException(outParameter.getRespDesc());
                }
            }
        }
    }

    /**
     * 修改借款标的的解冻数和放款记录数
     * 
     * @param borrowId
     * @param unFreeQty
     * @return
     */
    private void updateUnFreeAndLoanQty(Long borrowId, Integer unFreeQty, Integer loanQty) {
        if (unFreeQty.intValue() == 0 && loanQty.intValue() == 0) {
            return;
        }
        BorrowDo localBorrowDo = borrowComponent.getById(borrowId);
        if (localBorrowDo.getUnfreeOk().intValue() >= unFreeQty.intValue()
                && localBorrowDo.getLoansOk().intValue() >= loanQty.intValue()) {
            return;
        }
        BorrowDo borrowDo = new BorrowDo();
        borrowDo.setId(borrowId);
        borrowDo.setUnfreeOk(unFreeQty);
        borrowDo.setLoansOk(loanQty);
        borrowDo.setVersion(localBorrowDo.getVersion());
        borrowComponent.updateBorrowById(borrowDo);
    }

    /**
     * 校验标的是否符合放款要求
     * 
     * @param borrowId
     * @param borrrowStatus
     * @return
     */
    private IResult<String> checkBorrowFullScale(Long borrowId, Integer borrrowStatus) {
        BorrowDo borrowDo = borrowComponent.getById(borrowId);
        IResult<String> result = new ResultSupport<String>();
        if (borrowDo == null) {
            result.setSuccess(false);
            result.setErrorMessage("标的不存在!");
            return result;
        }
        if (borrowDo.getBorrowStatus() == null || borrowDo.getBorrowStatus().intValue() != 3) {
            result.setSuccess(false);
            result.setErrorMessage("满标之后借款才能放款!");
            return result;
        }
        if (borrrowStatus == null || (borrrowStatus.intValue() != 4 && borrrowStatus.intValue() != 6)) {
            result.setSuccess(false);
            result.setErrorMessage("借款只能做放款和流标处理!");
            return result;
        }

        if (!CalculateUtils.eq(borrowDo.getBorrowAmount(), borrowDo.getHasInvestAmount())) {
            result.setSuccess(false);
            result.setErrorMessage("借款金额与投资金额不相符!");
            return result;
        }

        result.setSuccess(true);
        return result;
    }

    @Override
    public NPageDo<BorrowDo> queryBorrows(Long currentPage, Long pageSize, Map<String, Object> searchItems) {
        try {
            long totalCount = borrowComponent.getBorrowQty(searchItems);
            if (totalCount == 0) {
                return new NPageDo<BorrowDo>(currentPage, pageSize, 0l, null);
            }
            List<BorrowDo> borrowDoList = borrowComponent.queryBorrows(searchItems);

            // 获取用户ID列表
            List<Long> userIdList = getUserIdList(borrowDoList);
            // 设置借款人信息
            List<AccountUserDo> userDoList = userComponent.queryByUserIds(userIdList);
            for (BorrowDo borrowDo : borrowDoList) {
                for (AccountUserDo accountUserDo : userDoList) {
                    if (borrowDo.getPublisher().longValue() == accountUserDo.getId().longValue()) {
                        borrowDo.setUserDo(accountUserDo);
                        break;
                    }
                }
            }
            // 设计借款人明细信息
            List<PersonDo> personDoList = personComponent.queryByUserIds(userIdList);
            for (BorrowDo borrowDo : borrowDoList) {
                for (PersonDo personDo : personDoList) {
                    if (borrowDo.getPublisher().longValue() == personDo.getUserId().longValue()) {
                        borrowDo.setPersonDo(personDo);
                        break;
                    }
                }
            }

            return new NPageDo<BorrowDo>(currentPage, pageSize, 0l, borrowDoList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new NPageDo<BorrowDo>(currentPage, pageSize, 0l, null);
        }
    }

    /**
     * 获取用户ID列表
     * 
     * @param borrowDoList
     * @return
     * @author: liuzgmf
     * @date: 2014年11月21日上午9:55:26
     */
    private List<Long> getUserIdList(List<BorrowDo> borrowDoList) {
        List<Long> userIdList = new ArrayList<Long>();
        for (BorrowDo borrowDo : borrowDoList) {
            userIdList.add(borrowDo.getPublisher());
        }
        return userIdList;
    }

    @Override
    public IResult<?> queryAgreementParams(Long userId, Long borrowId) {
        IResult<Object> result = new ResultSupport<Object>();
        try {
            // 投资明细信息
            List<Map<String, Object>> investList = investComponent.queryInvestDetails(borrowId);
            for (Map<String, Object> map : investList) {
                if (map.get("investAmount") != null) {
                    map.put("investAmount", ((BigDecimal) map.get("investAmount")).doubleValue());
                }
            }

            // 借款明细信息
            Map<String, Object> borrowMap = borrowComponent.queryBorrowDetails(borrowId);
            if (borrowMap.get("borrowAmount") != null) {
                borrowMap.put("borrowAmount", ((BigDecimal) borrowMap.get("borrowAmount")).doubleValue());
            }
            if (borrowMap.get("annualRate") != null) {
                borrowMap.put("annualRate", ((BigDecimal) borrowMap.get("annualRate")).doubleValue());
            }
            if (borrowMap.get("feeRate") != null) {
                borrowMap.put("feeRate", ((BigDecimal) borrowMap.get("feeRate")).doubleValue());
            }
            if (borrowMap.get("auditTime") != null && borrowMap.get("deadline") != null) {
                Date auditTime = (Date) borrowMap.get("auditTime");
                Integer dealline = (Integer) borrowMap.get("deadline");
                borrowMap.put("endTime", DateUtils.addMonths(auditTime, dealline));
            }
            // 计算借款手续费
            double feeAmt = calFeeAmt(borrowMap);
            borrowMap.put("feeAmt", feeAmt);
            borrowMap.put("feeAmtCn", MoneyUtil.amountToChinese(feeAmt));

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("investList", investList);
            params.put("borrow", borrowMap);

            result.setModel(params);
            result.setSuccess(true);
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setErrorMessage(e.getMessage());
            logger.error("标的[" + borrowId + "]放款失败，" + e.getMessage(), e);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("操作失败,请稍后再试!");
            logger.error("标的[" + borrowId + "]放款失败，" + e.getMessage(), e);
        }
        return result;
    }

    /**
     * 计算借款手续费
     * 
     * @param borrowAmount
     * @param feeRate
     * @param paymentMode
     * @return
     * @author: liuzgmf
     * @date: 2014年12月22日上午11:42:39
     */
    private double calFeeAmt(Map<String, Object> borrowMap) {
        Double borrowAmount = (Double) borrowMap.get("borrowAmount");
        Double feeRate = (Double) borrowMap.get("feeRate");
        Integer paymentMode = (Integer) borrowMap.get("paymentMode");
        Integer deadline = (Integer) borrowMap.get("deadline");
        if (borrowAmount == null || feeRate == null || paymentMode == null || deadline == null) {
            return 0.00;
        }
        if (paymentMode == 5 || paymentMode == 7) {
            return CalculateUtils.round(CalculateUtils.mul(CalculateUtils.mul(borrowAmount, 0.002), deadline), 2);
        } else {
            return CalculateUtils.round(CalculateUtils.mul(borrowAmount, CalculateUtils.div(feeRate, 100)), 2);
        }

    }
    
    @Override
    public List<Map<String,Object>> queryLoanBorrowList(Map<String, Object> searchItems) {
        try {

            List<Map<String,Object>> loanBorrowDoList = borrowComponent.queryLoanBorrows(searchItems);
            if(loanBorrowDoList == null || loanBorrowDoList.size() ==0){
            	return null;
            }

            return loanBorrowDoList;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
    
    @Override
    public List<Map<String,Object>> queryloanBorrowUserList(Map<String, Object> searchItems) {
        try {

            List<Map<String,Object>> loanBorrowUser = borrowComponent.queryloanBorrowUser(searchItems);
            
            if(loanBorrowUser == null || loanBorrowUser.size() ==0){
            	return null;
            }

            return loanBorrowUser;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

	@Override
	public List<Map<String, Object>> queryRepaymentList(Map<String, Object> searchItems) {
		
		try {

            List<Map<String,Object>> RepaymentList = borrowComponent.queryRepayment(searchItems);
            if(RepaymentList == null || RepaymentList.size() ==0){
            	return null;
            }

            return RepaymentList;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
	}
}
