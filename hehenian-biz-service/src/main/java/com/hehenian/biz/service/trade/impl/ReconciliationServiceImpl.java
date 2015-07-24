/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.service.trade.impl
 * @Title: ReconciliationServiceImpl.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年11月24日 上午10:07:42
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.service.trade.impl;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.activity.dataobject.ActivityTradeDo;
import com.hehenian.biz.common.base.dataobject.NPageDo;
import com.hehenian.biz.common.trade.IReconciliationService;
import com.hehenian.biz.common.trade.dataobject.*;
import com.hehenian.biz.common.trade.dataobject.AuctionDebtDo.AuctionStatus;
import com.hehenian.biz.common.trade.dataobject.ReconciliationDo.ReconciliationStatus;
import com.hehenian.biz.common.trade.dataobject.ReconciliationDo.ReconciliationType;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.component.account.IUserComponent;
import com.hehenian.biz.component.activity.IActivityTradeComponent;
import com.hehenian.biz.component.trade.*;
import com.hehenian.biz.facade.account.AccountType;
import com.hehenian.biz.facade.account.IAccountManagerService;
import com.hehenian.biz.facade.account.parameter.InParameter;
import com.hehenian.biz.facade.account.parameter.OutParameter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 交易对账服务
 * 
 * @author: liuzgmf
 * @date 2014年11月24日 上午10:07:42
 */
@Service("reconciliationService")
public class ReconciliationServiceImpl implements IReconciliationService {
    private final Logger              logger = Logger.getLogger(this.getClass());
    @Autowired
    private IAccountManagerService    accountManagerService;
    @Autowired
    private IWithdrawComponent        withdrawComponent;
    @Autowired
    private IUserComponent            userComponent;
    @Autowired
    private IReconciliationComponent  reconciliationComponent;
    @Autowired
    private IBorrowComponent          borrowComponent;
    @Autowired
    private IInvestComponent          investComponent;
    @Autowired
    private IAssignmentDebtComponent  assignmentDebtComponent;
    @Autowired
    private IAuctionDebtComponent     auctionDebtComponent;
    @Autowired
    private IInvestRepaymentComponent investRepaymentComponent;
    @Autowired
    private IRechargeComponent        rechargeComponent;
    @Autowired
    private IActivityTradeComponent   activityTradeComponent;

    @Override
    public void cashReconciliation() {
        try {
            // 新增提现对账记录
            addCashReconciliation();

            // 提现对账
            int count = reconciliationComponent.countReconciliations(ReconciliationType.CASH,
                    ReconciliationStatus.UNRECONCILIATION);
            while (count > 0) {
                updateCashReconciliation();
                count = count - 50;// 每次执行50条记录
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 提现对账
     * 
     * @author: liuzgmf
     * @date: 2014年11月24日下午2:48:57
     */
    private void updateCashReconciliation() {
        List<ReconciliationDo> reconciliationDoList = reconciliationComponent.queryReconciliations(
                ReconciliationType.CASH, ReconciliationStatus.UNRECONCILIATION, 50);
        if (reconciliationDoList == null || reconciliationDoList.size() == 0) {
            return;
        }
        List<Long> ordIdList = new ArrayList<Long>(reconciliationDoList.size());
        for (ReconciliationDo reconciliationDo : reconciliationDoList) {
            ordIdList.add(reconciliationDo.getOrdId());
        }
        List<WithdrawDo> withdrawDoList = withdrawComponent.queryByIds(ordIdList);
        for (ReconciliationDo reconciliationDo : reconciliationDoList) {
            WithdrawDo targetWithdrawDo = null;
            for (WithdrawDo withdrawDo : withdrawDoList) {
                if (withdrawDo.getId().longValue() == reconciliationDo.getOrdId().longValue()) {
                    targetWithdrawDo = withdrawDo;
                    break;
                }
            }

            if (checkedCashReconciliation(reconciliationDo, targetWithdrawDo)) {
                reconciliationDo.setReconciliationStatus(ReconciliationStatus.SUCCESS);
            } else {
                reconciliationDo.setReconciliationStatus(ReconciliationStatus.FAILURE);
            }
        }
        reconciliationComponent.updateReconciliationStatus(reconciliationDoList);
    }

    /**
     * 对账校验
     * 
     * @param reconciliationDo
     * @param targetWithdrawDo
     * @return
     * @author: liuzgmf
     * @date: 2014年11月24日下午3:30:20
     */
    private boolean checkedCashReconciliation(ReconciliationDo reconciliationDo, WithdrawDo targetWithdrawDo) {
        try {
            if (targetWithdrawDo == null) {
                reconciliationDo.setReconciliationDesc("提现记录不存在!");
                return false;
            }
            AccountUserDo userDo = userComponent.getById(targetWithdrawDo.getUserId());
            if (userDo == null) {
                reconciliationDo.setReconciliationDesc("提现用户不存在!");
                return false;
            }
            boolean success = true;
            StringBuffer errors = new StringBuffer();
            // 校验提现用户
            if (userDo == null || !reconciliationDo.getUsrCustId().equals(userDo.getUsrCustId().toString())) {
                errors.append("提现用户不一致[" + reconciliationDo.getUsrCustId() + "|" + userDo.getUsrCustId() + "];");
                success = false;
            }
            // 校验提现金额
            if (!CalculateUtils.eq(reconciliationDo.getTransAmt(), targetWithdrawDo.getSum())) {
                errors.append("提现金额不一致[" + reconciliationDo.getTransAmt() + "|" + targetWithdrawDo.getSum() + "];");
                success = false;
            }
            // 校验提现银行卡号
            if (!reconciliationDo.getCardId().equals(targetWithdrawDo.getAcount())) {
                errors.append("提现卡号不一致[" + reconciliationDo.getCardId() + "|" + targetWithdrawDo.getAcount() + "];");
                success = false;
            }
            // 校验提现状态
            if ((reconciliationDo.getTransStat().equals("S") && targetWithdrawDo.getStatus().intValue() != 2)
                    || (!reconciliationDo.getTransStat().equals("S") && targetWithdrawDo.getStatus().intValue() == 2)) {
                errors.append("提现状态不一致[" + reconciliationDo.getTransStat() + "|" + targetWithdrawDo.getStatus() + "];");
                success = false;
            }
            reconciliationDo.setReconciliationDesc((success ? null : errors.substring(0, errors.length() - 1)));
            return success;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reconciliationDo.setReconciliationDesc("对账异常!");
            return false;
        }
    }

    /**
     * 新增提现对账记录
     * 
     * @author: liuzgmf
     * @date: 2014年11月24日下午2:14:02
     */
    private void addCashReconciliation() {
        try {
            InParameter inParameter = new InParameter();
            Date beginDate = reconciliationComponent.getReconciliationBeginDate(ReconciliationType.CASH);
            Date endDate = DateUtils.addDays(beginDate, 10);
            inParameter.getParams().put("BeginDate", DateFormatUtils.format(beginDate, "yyyyMMdd"));
            inParameter.getParams().put("EndDate", DateFormatUtils.format(endDate, "yyyyMMdd"));
            inParameter.getParams().put("PageNum", 1);
            inParameter.getParams().put("PageSize", 200);
            while (beginDate.before(new Date())) {
                OutParameter outParameter = accountManagerService.cashReconciliation(inParameter, AccountType.CHINAPNR);
                if (!outParameter.isSuccess()) {
                    logger.error("查询提现对账数据失败!");
                    return;
                }

                // 新增提现对账记录
                @SuppressWarnings("unchecked")
                List<Map<String, String>> cashReconciliationDtoList = (List<Map<String, String>>) outParameter
                        .getParams().get("CashReconciliationDtoList");
                addReconciliation(cashReconciliationDtoList, ReconciliationType.CASH);

                int pageNum = Integer.parseInt((String) outParameter.getParams().get("PageNum"));
                long totalItems = Long.parseLong((String) outParameter.getParams().get("TotalItems"));
                int count = cashReconciliationDtoList.size();
                if (((pageNum - 1) * 200 + count) < totalItems) {// 继续同步下一页数据
                    inParameter.getParams().put("PageNum", (pageNum + 1));
                    continue;
                }

                // 下一个同步时间段
                beginDate = endDate;
                endDate = DateUtils.addDays(beginDate, 10);
                inParameter.getParams().put("PageNum", 1);
                inParameter.getParams().put("BeginDate", DateFormatUtils.format(beginDate, "yyyyMMdd"));
                inParameter.getParams().put("EndDate", DateFormatUtils.format(endDate, "yyyyMMdd"));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 新增对账信息
     * 
     * @param reconciliationDtoList
     * @param reconciliationType
     * @author: liuzgmf
     * @date: 2014年11月26日上午9:18:38
     */
    private void addReconciliation(List<Map<String, String>> reconciliationDtoList,
            ReconciliationType reconciliationType) {
        List<ReconciliationDo> reconciliationDoList = new ArrayList<ReconciliationDo>(reconciliationDtoList.size());
        for (Map<String, String> reconciliationDto : reconciliationDtoList) {
            ReconciliationDo reconciliationDo = new ReconciliationDo();
            reconciliationDo.setReconciliationType(reconciliationType);// 对账类型
            reconciliationDo.setReconciliationStatus(ReconciliationStatus.UNRECONCILIATION);// 未对账
            reconciliationDo.setOrdId(Long.parseLong(reconciliationDto.get("OrdId")));
            reconciliationDo.setMerCustId(reconciliationDto.get("MerCustId"));
            reconciliationDo.setUsrCustId(reconciliationDto.get("UsrCustId"));
            reconciliationDo.setCardId(reconciliationDto.get("CardId"));
            if (StringUtils.isNotBlank(reconciliationDto.get("TransAmt"))) {
                reconciliationDo.setTransAmt(Double.parseDouble(reconciliationDto.get("TransAmt")));
            }
            reconciliationDo.setTransStat(reconciliationDto.get("TransStat"));
            try {
                if (StringUtils.isNotBlank(reconciliationDto.get("PnrDate"))) {
                    reconciliationDo.setPnrDate(DateUtils.parseDate(reconciliationDto.get("PnrDate"),
                            new String[] { "yyyyMMdd" }));
                }
            } catch (ParseException e) {
                logger.error("新增提现对账日期解析错误，" + e.getMessage(), e);
            }

            reconciliationDo.setPnrSeqId(reconciliationDto.get("PnrSeqId"));
            try {
                if (StringUtils.isNotBlank(reconciliationDto.get("OrdDate"))) {
                    reconciliationDo.setOrdDate(DateUtils.parseDate(reconciliationDto.get("OrdDate"),
                            new String[] { "yyyyMMdd" }));
                }
            } catch (ParseException e) {
                logger.error("新增提现对账日期解析错误，" + e.getMessage(), e);
            }
            reconciliationDo.setInvestCustId(reconciliationDto.get("InvestCustId"));
            reconciliationDo.setBorrCustId(reconciliationDto.get("BorrCustId"));
            reconciliationDo.setGateBusiId(reconciliationDto.get("GateBusiId"));

            reconciliationDo.setOpenBankId(reconciliationDto.get("OpenBankId"));
            reconciliationDo.setOpenAcctId(reconciliationDto.get("OpenAcctId"));
            if (StringUtils.isNotBlank(reconciliationDto.get("FeeAmt"))) {
                reconciliationDo.setFeeAmt(Double.parseDouble(reconciliationDto.get("FeeAmt")));
            }
            reconciliationDo.setFeeCustId(reconciliationDto.get("FeeCustId"));
            reconciliationDo.setFeeAcctId(reconciliationDto.get("FeeAcctId"));

            reconciliationDo.setSellCustId(reconciliationDto.get("SellCustId"));
            if (StringUtils.isNotBlank(reconciliationDto.get("CreditAmt"))) {
                reconciliationDo.setCreditAmt(Double.parseDouble(reconciliationDto.get("CreditAmt")));
            }
            if (StringUtils.isNotBlank(reconciliationDto.get("CreditDealAmt"))) {
                reconciliationDo.setCreditDealAmt(Double.parseDouble(reconciliationDto.get("CreditDealAmt")));
            }
            if (StringUtils.isNotBlank(reconciliationDto.get("Fee"))) {
                reconciliationDo.setFee(Double.parseDouble(reconciliationDto.get("Fee")));
            }
            reconciliationDo.setBuyCustId(reconciliationDto.get("BuyCustId"));
            reconciliationDoList.add(reconciliationDo);
        }
        reconciliationComponent.addReconciliation(reconciliationDoList);
    }

    @Override
    public void loansReconciliation() {
        try {
            // 新增放款对账记录
            addLoansReconciliation();
            // 债权转让对账
            int count = reconciliationComponent.countReconciliations(ReconciliationType.LOANS,
                    ReconciliationStatus.UNRECONCILIATION);
            while (count > 0) {
                updateLoansReconciliation();
                count = count - 50;// 每次执行50条记录
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 放款对账
     * 
     * @author: liuzgmf
     * @date: 2014年11月25日下午1:57:11
     */
    private void updateLoansReconciliation() {
        List<ReconciliationDo> reconciliationDoList = reconciliationComponent.queryReconciliations(
                ReconciliationType.LOANS, ReconciliationStatus.UNRECONCILIATION, 50);
        if (reconciliationDoList == null || reconciliationDoList.size() == 0) {
            return;
        }
        List<Long> ordIdList = new ArrayList<Long>(reconciliationDoList.size());
        for (ReconciliationDo reconciliationDo : reconciliationDoList) {
            ordIdList.add(reconciliationDo.getOrdId());
        }
        List<InvestDo> investDoList = investComponent.queryByIds(ordIdList);
        for (ReconciliationDo reconciliationDo : reconciliationDoList) {
            InvestDo targetInvestDo = null;
            for (InvestDo investDo : investDoList) {
                if (investDo.getId().longValue() == reconciliationDo.getOrdId().longValue()) {
                    targetInvestDo = investDo;
                    break;
                }
            }

            if (loansReconciliation(reconciliationDo, targetInvestDo)) {
                reconciliationDo.setReconciliationStatus(ReconciliationStatus.SUCCESS);
            } else {
                reconciliationDo.setReconciliationStatus(ReconciliationStatus.FAILURE);
            }
        }
        reconciliationComponent.updateReconciliationStatus(reconciliationDoList);
    }

    /**
     * 放款对账
     * 
     * @param reconciliationDo
     * @param investDo
     * @return
     * @author: liuzgmf
     * @date: 2014年11月25日下午2:04:48
     */
    private boolean loansReconciliation(ReconciliationDo reconciliationDo, InvestDo investDo) {
        try {
            if (investDo == null) {
                reconciliationDo.setReconciliationDesc("投资记录不存在!");
                return false;
            }
            BorrowDo borrowDo = borrowComponent.getById(investDo.getBorrowId());
            if (borrowDo == null) {
                reconciliationDo.setReconciliationDesc("借款记录不存在!");
                return false;
            }
            AccountUserDo investUserDo = userComponent.getById(investDo.getInvestor());
            if (investUserDo == null) {
                reconciliationDo.setReconciliationDesc("投资用户不存在!");
                return false;
            }
            AccountUserDo loanUserDo = userComponent.getById(borrowDo.getPublisher());
            if (loanUserDo == null) {
                reconciliationDo.setReconciliationDesc("借款用户不存在!");
                return false;
            }
            boolean success = true;
            StringBuffer errors = new StringBuffer();
            if (investUserDo == null || !reconciliationDo.getInvestCustId().equals(investUserDo.getUsrCustId() + "")) {
                errors.append("投资用户不一致[" + reconciliationDo.getInvestCustId() + "|" + investUserDo.getUsrCustId()
                        + "];");
                success = false;
            }
            if (loanUserDo == null || !reconciliationDo.getBorrCustId().equals(loanUserDo.getUsrCustId() + "")) {
                errors.append("借款用户不一致[" + reconciliationDo.getBorrCustId() + "|" + loanUserDo.getUsrCustId() + "];");
                success = false;
            }
            if (!CalculateUtils.eq(reconciliationDo.getTransAmt(), investDo.getInvestAmount())) {
                errors.append("交易金额不一致[" + reconciliationDo.getTransAmt() + "|" + investDo.getInvestAmount() + "];");
                success = false;
            }
            // 借款状态，如果放款状态为成功(P就为成功)，那么借款标的的状态应为满标，还款中，已还完。
            // 如果放款状态为失败，则借款的状态不能为满标，还款中，已还完状态中任何一个。
            if ((reconciliationDo.getTransStat().equals("P") && borrowDo.getBorrowStatus().intValue() != 3
                    && borrowDo.getBorrowStatus().intValue() != 4 && borrowDo.getBorrowStatus().intValue() != 5)
                    || (!reconciliationDo.getTransStat().equals("P") && (borrowDo.getBorrowStatus().intValue() == 4 || borrowDo
                            .getBorrowStatus().intValue() == 5))) {
                errors.append("交易状态不一致[" + reconciliationDo.getTransStat() + "|" + borrowDo.getBorrowStatus() + "];");
                success = false;
            }
            reconciliationDo.setReconciliationDesc((success ? null : errors.substring(0, errors.length() - 1)));
            return success;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reconciliationDo.setReconciliationDesc("对账异常!");
            return false;
        }
    }

    /**
     * 新增放款对账记录
     * 
     * @author: liuzgmf
     * @date: 2014年11月25日上午11:24:22
     */
    private void addLoansReconciliation() {
        try {
            InParameter inParameter = new InParameter();
            Date beginDate = reconciliationComponent.getReconciliationBeginDate(ReconciliationType.LOANS);
            Date endDate = DateUtils.addDays(beginDate, 10);
            inParameter.getParams().put("BeginDate", DateFormatUtils.format(beginDate, "yyyyMMdd"));
            inParameter.getParams().put("EndDate", DateFormatUtils.format(endDate, "yyyyMMdd"));
            inParameter.getParams().put("QueryTransType", "LOANS");
            inParameter.getParams().put("PageNum", 1);
            inParameter.getParams().put("PageSize", 200);
            while (beginDate.before(new Date())) {
                OutParameter outParameter = accountManagerService.reconciliation(inParameter, AccountType.CHINAPNR);
                if (!outParameter.isSuccess()) {
                    logger.error("查询放款对账数据失败!");
                    return;
                }

                // 新增放款对账记录
                @SuppressWarnings("unchecked")
                List<Map<String, String>> reconciliationDtoList = (List<Map<String, String>>) outParameter.getParams()
                        .get("ReconciliationDtoList");
                addReconciliation(reconciliationDtoList, ReconciliationType.LOANS);

                int pageNum = Integer.parseInt((String) outParameter.getParams().get("PageNum"));
                long totalItems = Long.parseLong((String) outParameter.getParams().get("TotalItems"));
                int count = reconciliationDtoList.size();
                if (((pageNum - 1) * 200 + count) < totalItems) {// 继续同步下一页数据
                    inParameter.getParams().put("PageNum", (pageNum + 1));
                    continue;
                }

                // 下一个同步时间段
                beginDate = endDate;
                endDate = DateUtils.addDays(beginDate, 10);
                inParameter.getParams().put("PageNum", 1);
                inParameter.getParams().put("BeginDate", DateFormatUtils.format(beginDate, "yyyyMMdd"));
                inParameter.getParams().put("EndDate", DateFormatUtils.format(endDate, "yyyyMMdd"));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void repaymentReconciliation() {
        try {
            // 查询汇付放款数据，保存至对账表
            addRepaymentReconciliation();
            // 债权转让对账
            int count = reconciliationComponent.countReconciliations(ReconciliationType.REPAYMENT,
                    ReconciliationStatus.UNRECONCILIATION);
            while (count > 0) {
                updateRepaymentReconciliation();
                count = count - 50;// 每次执行50条记录
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 还款对账
     * 
     * @author: liuzgmf
     * @date: 2014年11月25日下午1:57:11
     */
    private void updateRepaymentReconciliation() {
        List<ReconciliationDo> reconciliationDoList = reconciliationComponent.queryReconciliations(
                ReconciliationType.REPAYMENT, ReconciliationStatus.UNRECONCILIATION, 50);
        if (reconciliationDoList == null || reconciliationDoList.size() == 0) {
            return;
        }
        List<Long> ordIdList = new ArrayList<Long>(reconciliationDoList.size());
        for (ReconciliationDo reconciliationDo : reconciliationDoList) {
            ordIdList.add(reconciliationDo.getOrdId());
        }
        List<InvestRepaymentDo> investRepaymentDoList = investRepaymentComponent.queryByIds(ordIdList);
        for (ReconciliationDo reconciliationDo : reconciliationDoList) {
            InvestRepaymentDo targetInvestRepaymentDo = null;
            for (InvestRepaymentDo investRepaymentDo : investRepaymentDoList) {
                if (investRepaymentDo.getId().longValue() == reconciliationDo.getOrdId().longValue()) {
                    targetInvestRepaymentDo = investRepaymentDo;
                    break;
                }
            }

            if (repaymentReconciliation(reconciliationDo, targetInvestRepaymentDo)) {
                reconciliationDo.setReconciliationStatus(ReconciliationStatus.SUCCESS);
            } else {
                reconciliationDo.setReconciliationStatus(ReconciliationStatus.FAILURE);
            }
        }
        reconciliationComponent.updateReconciliationStatus(reconciliationDoList);
    }

    /**
     * 还款对账
     * 
     * @param reconciliationDo
     * @param investRepaymentDo
     * @return
     * @author: liuzgmf
     * @date: 2014年11月25日下午2:04:48
     */
    private boolean repaymentReconciliation(ReconciliationDo reconciliationDo, InvestRepaymentDo investRepaymentDo) {
        try {
            if (investRepaymentDo == null) {
                reconciliationDo.setReconciliationDesc("回款记录不存在!");
                return false;
            }
            AccountUserDo investUserDo = userComponent.getById(investRepaymentDo.getOwner());
            if (investUserDo == null) {
                reconciliationDo.setReconciliationDesc("投资用户不存在!");
                return false;
            }
            BorrowDo borrowDo = borrowComponent.getById(investRepaymentDo.getBorrowId());
            if (borrowDo == null) {
                reconciliationDo.setReconciliationDesc("借款记录不存在!");
                return false;
            }
            AccountUserDo loanUserDo = userComponent.getById(borrowDo.getPublisher());
            if (loanUserDo == null) {
                reconciliationDo.setReconciliationDesc("借款用户不存在!");
                return false;
            }
            boolean success = true;
            StringBuffer errors = new StringBuffer();
            if (investUserDo == null || !reconciliationDo.getInvestCustId().equals(investUserDo.getUsrCustId() + "")) {
                errors.append("投资用户不一致[" + reconciliationDo.getInvestCustId() + "|" + investUserDo.getUsrCustId()
                        + "];");
                success = false;
            }
            if (loanUserDo == null || !reconciliationDo.getBorrCustId().equals(loanUserDo.getUsrCustId() + "")) {
                errors.append("借款用户不一致[" + reconciliationDo.getBorrCustId() + "|" + loanUserDo.getUsrCustId() + "];");
                success = false;
            }
            Double transAmt = CalculateUtils.add(investRepaymentDo.getHasPrincipal(),
                    investRepaymentDo.getHasInterest());
            if (!CalculateUtils.eq(reconciliationDo.getTransAmt(), transAmt)) {
                errors.append("交易金额不一致[" + reconciliationDo.getTransAmt() + "|" + transAmt + "];");
                success = false;
            }
            // 借款状态，如果还款状态为成功(P就为成功)，那么借款标的的状态应为还款中，已还完。
            // 如果还款状态为失败，则借款的状态不能为还款中，已还完状态中任何一个。
            if ((reconciliationDo.getTransStat().equals("P") && borrowDo.getBorrowStatus().intValue() != 4 && borrowDo
                    .getBorrowStatus().intValue() != 5)
                    || (!reconciliationDo.getTransStat().equals("P") && (borrowDo.getBorrowStatus().intValue() == 4 || borrowDo
                            .getBorrowStatus().intValue() == 5))) {
                errors.append("交易状态不一致[" + reconciliationDo.getTransStat() + "|" + borrowDo.getBorrowStatus() + "];");
                success = false;
            }
            reconciliationDo.setReconciliationDesc((success ? null : errors.substring(0, errors.length() - 1)));
            return success;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reconciliationDo.setReconciliationDesc("对账异常!");
            return false;
        }
    }

    /**
     * 查询汇付放款数据，保存至对账表
     * 
     * @author: liuzgmf
     * @date: 2014年11月25日上午11:24:22
     */
    private void addRepaymentReconciliation() {
        try {
            InParameter inParameter = new InParameter();
            Date beginDate = reconciliationComponent.getReconciliationBeginDate(ReconciliationType.REPAYMENT);
            Date endDate = DateUtils.addDays(beginDate, 10);
            inParameter.getParams().put("BeginDate", DateFormatUtils.format(beginDate, "yyyyMMdd"));
            inParameter.getParams().put("EndDate", DateFormatUtils.format(endDate, "yyyyMMdd"));
            inParameter.getParams().put("QueryTransType", "REPAYMENT");
            inParameter.getParams().put("PageNum", 1);
            inParameter.getParams().put("PageSize", 200);
            while (beginDate.before(new Date())) {
                OutParameter outParameter = accountManagerService.reconciliation(inParameter, AccountType.CHINAPNR);
                if (!outParameter.isSuccess()) {
                    logger.error("查询还款对账数据失败!");
                    return;
                }

                // 新增放款对账记录
                @SuppressWarnings("unchecked")
                List<Map<String, String>> reconciliationDtoList = (List<Map<String, String>>) outParameter.getParams()
                        .get("ReconciliationDtoList");
                addReconciliation(reconciliationDtoList, ReconciliationType.REPAYMENT);

                int pageNum = Integer.parseInt((String) outParameter.getParams().get("PageNum"));
                long totalItems = Long.parseLong((String) outParameter.getParams().get("TotalItems"));
                int count = reconciliationDtoList.size();
                if (((pageNum - 1) * 200 + count) < totalItems) {// 继续同步下一页数据
                    inParameter.getParams().put("PageNum", (pageNum + 1));
                    continue;
                }

                // 下一个同步时间段
                beginDate = endDate;
                endDate = DateUtils.addDays(beginDate, 10);
                inParameter.getParams().put("PageNum", 1);
                inParameter.getParams().put("BeginDate", DateFormatUtils.format(beginDate, "yyyyMMdd"));
                inParameter.getParams().put("EndDate", DateFormatUtils.format(endDate, "yyyyMMdd"));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void creditAssignReconciliation() {
        try {
            // 查询汇付数据，保存至对账表中
            addCreditAssignReconciliation();
            // 债权转让对账
            int count = reconciliationComponent.countReconciliations(ReconciliationType.DEBT,
                    ReconciliationStatus.UNRECONCILIATION);
            while (count > 0) {
                updateCreditAssignReconciliation();
                count = count - 50;// 每次执行50条记录
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void updateCreditAssignReconciliation() {
        List<ReconciliationDo> reconciliationDoList = reconciliationComponent.queryReconciliations(
                ReconciliationType.DEBT, ReconciliationStatus.UNRECONCILIATION, 50);
        if (reconciliationDoList == null || reconciliationDoList.size() == 0) {
            return;
        }
        List<Long> ordIdList = new ArrayList<Long>(reconciliationDoList.size());
        for (ReconciliationDo reconciliationDo : reconciliationDoList) {
            ordIdList.add(reconciliationDo.getOrdId());
        }
        List<AuctionDebtDo> auctionDebtDoList = auctionDebtComponent.queryByIds(ordIdList);
        for (ReconciliationDo reconciliationDo : reconciliationDoList) {
            AuctionDebtDo targetAuctionDebtDo = null;
            for (AuctionDebtDo auctionDebtDo : auctionDebtDoList) {
                if (auctionDebtDo.getId().longValue() == reconciliationDo.getOrdId().longValue()) {
                    targetAuctionDebtDo = auctionDebtDo;
                    break;
                }
            }

            if (creditAssignReconciliation(reconciliationDo, targetAuctionDebtDo)) {
                reconciliationDo.setReconciliationStatus(ReconciliationStatus.SUCCESS);
            } else {
                reconciliationDo.setReconciliationStatus(ReconciliationStatus.FAILURE);
            }
        }
        reconciliationComponent.updateReconciliationStatus(reconciliationDoList);
    }

    /**
     * 债权转让对账
     * 
     * @param reconciliationDo
     * @param targetAuctionDebtDo
     * @return
     * @author: liuzgmf
     * @date: 2014年11月25日上午9:08:52
     */
    private boolean creditAssignReconciliation(ReconciliationDo reconciliationDo, AuctionDebtDo auctionDebtDo) {
        try {
            if (auctionDebtDo == null) {
                reconciliationDo.setReconciliationDesc("债权认购记录不存在!");
                return false;
            }
            AssignmentDebtDo assignmentDebtDo = assignmentDebtComponent.getById(auctionDebtDo.getDebtId());
            if (assignmentDebtDo == null) {
                reconciliationDo.setReconciliationDesc("债权转让记录不存在!");
                return false;
            }
            AccountUserDo alienator = userComponent.getById(assignmentDebtDo.getAlienatorId());
            if (alienator == null) {
                reconciliationDo.setReconciliationDesc("转让用户不存在!");
                return false;
            }
            AccountUserDo auctioner = userComponent.getById(assignmentDebtDo.getAuctionerId());
            if (auctioner == null) {
                reconciliationDo.setReconciliationDesc("认购用户不存在!");
                return false;
            }
            boolean success = true;
            StringBuffer errors = new StringBuffer();
            if (!reconciliationDo.getSellCustId().equals(alienator.getUsrCustId() + "")) {
                errors.append("转让用户不一致[" + reconciliationDo.getSellCustId() + "|" + alienator.getUsrCustId() + "];");
                success = false;
            }
            if (!reconciliationDo.getBuyCustId().equals(auctioner.getUsrCustId() + "")) {
                errors.append("认购用户不一致[" + reconciliationDo.getBuyCustId() + "|" + auctioner.getUsrCustId() + "];");
                success = false;
            }
            if (!CalculateUtils.eq(reconciliationDo.getCreditAmt(), assignmentDebtDo.getDebtSum())) {
                errors.append("债权金额不一致[" + reconciliationDo.getCreditAmt() + "|" + assignmentDebtDo.getDebtSum() + "];");
                success = false;
            }
            if (!CalculateUtils.eq(reconciliationDo.getCreditDealAmt(), assignmentDebtDo.getAuctionHighPrice())) {
                errors.append("认购金额不一致[" + reconciliationDo.getCreditDealAmt() + ":"
                        + assignmentDebtDo.getAuctionHighPrice() + "];");
                success = false;
            }
            if (!CalculateUtils.eq(reconciliationDo.getFee(), assignmentDebtDo.getManageFee())) {
                errors.append("服务费不一致[" + reconciliationDo.getFee() + "|" + assignmentDebtDo.getManageFee() + "];");
                success = false;
            }
            if ((reconciliationDo.getTransStat().equals("S") && !auctionDebtDo.getAuctionStatus().equals(
                    AuctionStatus.SUCCESS))
                    || (!reconciliationDo.getTransStat().equals("S") && auctionDebtDo.getAuctionStatus().equals(
                            AuctionStatus.SUCCESS))) {
                errors.append("转让状态不一致[" + reconciliationDo.getTransStat() + "|" + auctionDebtDo.getAuctionStatus()
                        + "];");
                success = false;
            }
            reconciliationDo.setReconciliationDesc((success ? null : errors.substring(0, errors.length() - 1)));
            return success;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reconciliationDo.setReconciliationDesc("对账异常!");
            return false;
        }
    }

    /**
     * 查询汇付数据，保存至对账表中
     * 
     * @author: liuzgmf
     * @date: 2014年11月25日上午8:45:43
     */
    private void addCreditAssignReconciliation() {
        try {
            InParameter inParameter = new InParameter();
            Date beginDate = reconciliationComponent.getReconciliationBeginDate(ReconciliationType.DEBT);
            Date endDate = DateUtils.addDays(beginDate, 10);
            inParameter.getParams().put("BeginDate", DateFormatUtils.format(beginDate, "yyyyMMdd"));
            inParameter.getParams().put("EndDate", DateFormatUtils.format(endDate, "yyyyMMdd"));
            inParameter.getParams().put("PageNum", 1);
            inParameter.getParams().put("PageSize", 200);
            while (beginDate.before(new Date())) {
                OutParameter outParameter = accountManagerService.creditAssignReconciliation(inParameter,
                        AccountType.CHINAPNR);
                if (!outParameter.isSuccess()) {
                    logger.error("查询债权转让对账数据失败!");
                    return;
                }

                // 新增提现对账记录
                @SuppressWarnings("unchecked")
                List<Map<String, String>> bidCaReconciliationList = (List<Map<String, String>>) outParameter
                        .getParams().get("BidCaReconciliationList");
                addReconciliation(bidCaReconciliationList, ReconciliationType.DEBT);

                int pageNum = Integer.parseInt((String) outParameter.getParams().get("PageNum"));
                long totalItems = Long.parseLong((String) outParameter.getParams().get("TotalItems"));
                int count = bidCaReconciliationList.size();
                if (((pageNum - 1) * 200 + count) < totalItems) {// 继续同步下一页数据
                    inParameter.getParams().put("PageNum", (pageNum + 1));
                    continue;
                }

                // 下一个同步时间段
                beginDate = endDate;
                endDate = DateUtils.addDays(beginDate, 10);
                inParameter.getParams().put("PageNum", 1);
                inParameter.getParams().put("BeginDate", DateFormatUtils.format(beginDate, "yyyyMMdd"));
                inParameter.getParams().put("EndDate", DateFormatUtils.format(endDate, "yyyyMMdd"));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void rechargeReconciliation() {
        try {
            // 新增充值记录
            addRechargeReconciliation();

            // 充值对账
            int count = reconciliationComponent.countReconciliations(ReconciliationType.RECHARGE,
                    ReconciliationStatus.UNRECONCILIATION);
            while (count > 0) {
                updateRechargeReconciliation();
                count = count - 50;// 每次执行50条记录
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 充值对账
     * 
     * @author: liuzgmf
     * @date: 2014年11月26日上午10:45:21
     */
    private void updateRechargeReconciliation() {
        List<ReconciliationDo> reconciliationDoList = reconciliationComponent.queryReconciliations(
                ReconciliationType.RECHARGE, ReconciliationStatus.UNRECONCILIATION, 50);
        if (reconciliationDoList == null || reconciliationDoList.size() == 0) {
            return;
        }
        List<Long> ordIdList = new ArrayList<Long>(reconciliationDoList.size());
        for (ReconciliationDo reconciliationDo : reconciliationDoList) {
            ordIdList.add(reconciliationDo.getOrdId());
        }
        List<RechargeDo> rechargeDoList = rechargeComponent.queryByIds(ordIdList);
        for (ReconciliationDo reconciliationDo : reconciliationDoList) {
            RechargeDo targetRechargeDo = null;
            for (RechargeDo rechargeDo : rechargeDoList) {
                if (rechargeDo.getId() == reconciliationDo.getOrdId().longValue()) {
                    targetRechargeDo = rechargeDo;
                    break;
                }
            }

            if (rechargeReconciliation(reconciliationDo, targetRechargeDo)) {
                reconciliationDo.setReconciliationStatus(ReconciliationStatus.SUCCESS);
            } else {
                reconciliationDo.setReconciliationStatus(ReconciliationStatus.FAILURE);
            }
        }
        reconciliationComponent.updateReconciliationStatus(reconciliationDoList);
    }

    /**
     * 充值对账
     * 
     * @param reconciliationDo
     * @param targetRechargeDo
     * @return
     * @author: liuzgmf
     * @date: 2014年11月26日上午10:49:17
     */
    private boolean rechargeReconciliation(ReconciliationDo reconciliationDo, RechargeDo rechargeDo) {
        try {
            if (rechargeDo == null) {
                reconciliationDo.setReconciliationDesc("充值记录不存在!");
                return false;
            }
            AccountUserDo userDo = userComponent.getById(rechargeDo.getUserId());
            if (userDo == null) {
                reconciliationDo.setReconciliationDesc("充值用户不存在!");
                return false;
            }
            boolean success = true;
            StringBuffer errors = new StringBuffer();
            if (!reconciliationDo.getUsrCustId().equals(userDo.getUsrCustId() + "")) {
                errors.append("充值用户不一致[" + reconciliationDo.getUsrCustId() + "|" + userDo.getUsrCustId() + "];");
                success = false;
            }
            if (!CalculateUtils.eq(reconciliationDo.getTransAmt(), rechargeDo.getRechargeMoney())) {
                errors.append("充值金额不一致[" + reconciliationDo.getTransAmt() + "|" + rechargeDo.getRechargeMoney() + "];");
                success = false;
            }
            // if (!CalculateUtils.eq(reconciliationDo.getFeeAmt(),
            // rechargeDo.getCost())) {
            // errors.append("充值手续费不一致[" + reconciliationDo.getFeeAmt() + "|" +
            // rechargeDo.getCost() + "];");
            // success = false;
            // }
            if ((reconciliationDo.getTransStat().equals("S") && rechargeDo.getResult() != 1)
                    || (!reconciliationDo.getTransStat().equals("S") && rechargeDo.getResult() == 1)) {
                errors.append("充值状态不一致[" + reconciliationDo.getTransStat() + "|" + rechargeDo.getResult() + "];");
                success = false;
            }
            reconciliationDo.setReconciliationDesc((success ? null : errors.substring(0, errors.length() - 1)));
            return success;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reconciliationDo.setReconciliationDesc("对账异常!");
            return false;
        }
    }

    /**
     * 新增充值记录
     * 
     * @author: liuzgmf
     * @date: 2014年11月26日上午10:42:26
     */
    private void addRechargeReconciliation() {
        try {
            InParameter inParameter = new InParameter();
            Date beginDate = reconciliationComponent.getReconciliationBeginDate(ReconciliationType.RECHARGE);
            Date endDate = DateUtils.addDays(beginDate, 10);
            inParameter.getParams().put("BeginDate", DateFormatUtils.format(beginDate, "yyyyMMdd"));
            inParameter.getParams().put("EndDate", DateFormatUtils.format(endDate, "yyyyMMdd"));
            inParameter.getParams().put("PageNum", 1);
            inParameter.getParams().put("PageSize", 200);
            while (beginDate.before(new Date())) {
                OutParameter outParameter = accountManagerService.saveReconciliation(inParameter, AccountType.CHINAPNR);
                if (!outParameter.isSuccess()) {
                    logger.error("查询充值对账数据失败!");
                    return;
                }

                // 新增提现对账记录
                @SuppressWarnings("unchecked")
                List<Map<String, String>> saveReconciliationDtoList = (List<Map<String, String>>) outParameter
                        .getParams().get("SaveReconciliationDtoList");
                addReconciliation(saveReconciliationDtoList, ReconciliationType.RECHARGE);

                int pageNum = Integer.parseInt((String) outParameter.getParams().get("PageNum"));
                long totalItems = Long.parseLong((String) outParameter.getParams().get("TotalItems"));
                int count = saveReconciliationDtoList.size();
                if (((pageNum - 1) * 200 + count) < totalItems) {// 继续同步下一页数据
                    inParameter.getParams().put("PageNum", (pageNum + 1));
                    continue;
                }

                // 下一个同步时间段
                beginDate = endDate;
                endDate = DateUtils.addDays(beginDate, 10);
                inParameter.getParams().put("PageNum", 1);
                inParameter.getParams().put("BeginDate", DateFormatUtils.format(beginDate, "yyyyMMdd"));
                inParameter.getParams().put("EndDate", DateFormatUtils.format(endDate, "yyyyMMdd"));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    @Override
    public void trfReconciliation() {
        try {
            // 新增商户扣款对账记录
            addTrfReconciliation();

            // 充值对账
            int count = reconciliationComponent.countReconciliations(ReconciliationType.TRANSFER,
                    ReconciliationStatus.UNRECONCILIATION);
            while (count > 0) {
                updateTrfReconciliation();
                count = count - 50;// 每次执行50条记录
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 商户扣款对账
     * 
     * @author: liuzgmf
     * @date: 2014年11月26日上午10:45:21
     */
    private void updateTrfReconciliation() {
        List<ReconciliationDo> reconciliationDoList = reconciliationComponent.queryReconciliations(
                ReconciliationType.TRANSFER, ReconciliationStatus.UNRECONCILIATION, 50);
        if (reconciliationDoList == null || reconciliationDoList.size() == 0) {
            return;
        }
        List<Long> ordIdList = new ArrayList<Long>(reconciliationDoList.size());
        for (ReconciliationDo reconciliationDo : reconciliationDoList) {
            ordIdList.add(reconciliationDo.getOrdId());
        }
        List<ActivityTradeDo> activityTradeDoList = activityTradeComponent.queryByIds(ordIdList);
        for (ReconciliationDo reconciliationDo : reconciliationDoList) {
            ActivityTradeDo targetActivityTradeDo = null;
            for (ActivityTradeDo activityTradeDo : activityTradeDoList) {
                if (activityTradeDo.getTradeId().longValue() == reconciliationDo.getOrdId().longValue()) {
                    targetActivityTradeDo = activityTradeDo;
                    break;
                }
            }

            if (trfReconciliation(reconciliationDo, targetActivityTradeDo)) {
                reconciliationDo.setReconciliationStatus(ReconciliationStatus.SUCCESS);
            } else {
                reconciliationDo.setReconciliationStatus(ReconciliationStatus.FAILURE);
            }
        }
        reconciliationComponent.updateReconciliationStatus(reconciliationDoList);
    }

    /**
     * 商户扣款对账
     * 
     * @param reconciliationDo
     * @param targetRechargeDo
     * @return
     * @author: liuzgmf
     * @date: 2014年11月26日上午10:49:17
     */
    private boolean trfReconciliation(ReconciliationDo reconciliationDo, ActivityTradeDo activityTradeDo) {
        try {
            if (activityTradeDo == null) {
                reconciliationDo.setReconciliationDesc("转账记录不存在!");
                return false;
            }
            AccountUserDo userDo = userComponent.getById(activityTradeDo.getFromUserId());
            if (userDo == null) {
                reconciliationDo.setReconciliationDesc("转账用户不存在!");
                return false;
            }
            boolean success = true;
            StringBuffer errors = new StringBuffer();
            if (!reconciliationDo.getUsrCustId().equals(userDo.getUsrCustId() + "")) {
                errors.append("转账用户不一致[" + reconciliationDo.getUsrCustId() + "|" + userDo.getUsrCustId() + "];");
                success = false;
            }
            if (!CalculateUtils.eq(reconciliationDo.getTransAmt(), activityTradeDo.getRealAmount())) {
                errors.append("转账金额不一致[" + reconciliationDo.getTransAmt() + "|" + activityTradeDo.getRealAmount()
                        + "];");
                success = false;
            }
            if ((reconciliationDo.getTransStat().equals("S") && activityTradeDo.getTradeStatus().intValue() != 1)
                    || (!reconciliationDo.getTransStat().equals("S") && activityTradeDo.getTradeStatus().intValue() == 1)) {
                errors.append("转账状态不一致[" + reconciliationDo.getTransStat() + "|" + activityTradeDo.getTradeStatus()
                        + "];");
                success = false;
            }
            reconciliationDo.setReconciliationDesc((success ? null : errors.substring(0, errors.length() - 1)));
            return success;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            reconciliationDo.setReconciliationDesc("对账异常!");
            return false;
        }
    }

    /**
     * 新增商户扣款对账记录
     * 
     * @author: liuzgmf
     * @date: 2014年11月26日上午10:42:26
     */
    private void addTrfReconciliation() {
        try {
            InParameter inParameter = new InParameter();
            Date beginDate = reconciliationComponent.getReconciliationBeginDate(ReconciliationType.TRANSFER);
            Date endDate = DateUtils.addDays(beginDate, 10);
            inParameter.getParams().put("BeginDate", DateFormatUtils.format(beginDate, "yyyyMMdd"));
            inParameter.getParams().put("EndDate", DateFormatUtils.format(endDate, "yyyyMMdd"));
            inParameter.getParams().put("PageNum", 1);
            inParameter.getParams().put("PageSize", 200);
            while (beginDate.before(new Date())) {
                OutParameter outParameter = accountManagerService.trfReconciliation(inParameter, AccountType.CHINAPNR);
                if (!outParameter.isSuccess()) {
                    logger.error("查询充值对账数据失败!");
                    return;
                }

                // 新增提现对账记录
                @SuppressWarnings("unchecked")
                List<Map<String, String>> trfReconciliationDtoList = (List<Map<String, String>>) outParameter
                        .getParams().get("TrfReconciliationDtoList");
                addReconciliation(trfReconciliationDtoList, ReconciliationType.TRANSFER);

                int pageNum = Integer.parseInt((String) outParameter.getParams().get("PageNum"));
                long totalItems = Long.parseLong((String) outParameter.getParams().get("TotalItems"));
                int count = trfReconciliationDtoList.size();
                if (((pageNum - 1) * 200 + count) < totalItems) {// 继续同步下一页数据
                    inParameter.getParams().put("PageNum", (pageNum + 1));
                    continue;
                }

                // 下一个同步时间段
                beginDate = endDate;
                endDate = DateUtils.addDays(beginDate, 10);
                inParameter.getParams().put("PageNum", 1);
                inParameter.getParams().put("BeginDate", DateFormatUtils.format(beginDate, "yyyyMMdd"));
                inParameter.getParams().put("EndDate", DateFormatUtils.format(endDate, "yyyyMMdd"));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    /**
     * 查询对账信息
     */
    @Override
    public NPageDo<ReconciliationDo> getReconciliations(Map searchItems) {
        NPageDo<ReconciliationDo> pageDo = new NPageDo<ReconciliationDo>();
        try {
            long count = reconciliationComponent.countRecon(searchItems);
            pageDo.setTotalCount(count);
            if (count == 0) {
                return pageDo;
            }
            List<ReconciliationDo> reconciliationDo = reconciliationComponent.getReconciliations(searchItems);
            pageDo.setModelList(reconciliationDo);

            // 查询借款用户信息
            List<Long> userIdList = new ArrayList<Long>();
            for (ReconciliationDo renDo : reconciliationDo) {
                if (renDo.getUsrCustId() != null) {
                    userIdList.add(Long.valueOf(renDo.getUsrCustId()));
                }
            }
            List<AccountUserDo> userDoList = userComponent.queryUserByCustId(userIdList);
            for (ReconciliationDo renDo : reconciliationDo) {
                for (AccountUserDo userDo : userDoList) {
                    if (renDo.getUsrCustId() != null
                            && Long.valueOf(renDo.getUsrCustId()) == userDo.getUsrCustId().longValue()) {
                        renDo.setUserDo(userDo);
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


}
