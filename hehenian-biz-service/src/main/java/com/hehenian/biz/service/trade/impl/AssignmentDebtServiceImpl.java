/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.service.trade.impl
 * @Title: AssignmentDebtServiceImpl.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年9月26日 上午11:32:16
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.service.trade.impl;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.base.result.IResult;
import com.hehenian.biz.common.base.result.ResultSupport;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.trade.IAssignmentDebtService;
import com.hehenian.biz.common.trade.dataobject.*;
import com.hehenian.biz.common.trade.dataobject.AuctionDebtDo.AuctionStatus;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.component.account.IUserComponent;
import com.hehenian.biz.component.trade.*;
import com.hehenian.biz.facade.account.AccountType;
import com.hehenian.biz.facade.account.IAccountManagerService;
import com.hehenian.biz.facade.account.parameter.InParameter;
import com.hehenian.biz.facade.account.parameter.OutParameter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

/**
 * 
 * @author: liuzgmf
 * @date 2014年9月26日 上午11:32:16
 */
@Service("assignmentDebtService")
public class AssignmentDebtServiceImpl implements IAssignmentDebtService {
    private final Logger              logger = Logger.getLogger(this.getClass());
    @Autowired
    private IAccountManagerService    accountManagerService;
    @Autowired
    private IAssignmentDebtComponent  assignmentDebtComponent;
    @Autowired
    private IUserComponent            userComponent;
    @Autowired
    private IInvestComponent          investComponent;
    @Autowired
    private IBorrowComponent          borrowComponent;
    @Autowired
    private IInvestRepaymentComponent investRepaymentComponent;
    @Autowired
    private IAuctionDebtComponent     auctionDebtComponent;
    @Autowired
    private IReconciliationComponent  reconciliationComponent;

    @Override
    public IResult<?> addAssignmentDebt(AssignmentDebtDo assignmentDebtDo) {
        IResult<String> result = new ResultSupport<String>();
        try {
            boolean success = assignmentDebtComponent.isHaveAssignmentDebt(assignmentDebtDo.getInvestId(),
                    assignmentDebtDo.getAlienatorId());
            if (!success) {
                throw new BusinessException("该笔投资已转让!");
            }
            // 债权转让申请必须在还款日期五天前
            InvestRepaymentDo investRepaymentDo = investRepaymentComponent.getNoRepayRecordRecently(
                    assignmentDebtDo.getInvestId(), assignmentDebtDo.getAlienatorId());
            if (investRepaymentDo == null || new Date().after(DateUtils.addDays(investRepaymentDo.getRepayDate(), -5))) {
                throw new BusinessException("还款日前五日内不能进行债权转让!");
            }

            assignmentDebtDo.setDebtStatus(1);// 1-审核中
            assignmentDebtComponent.addAssignmentDebt(assignmentDebtDo);
            result.setSuccess(true);
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setErrorMessage(e.getMessage());
            logger.warn("债权转让申请失败，投资ID[" + assignmentDebtDo.getInvestId() + "]，" + e.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("操作失败,请稍后再试!");
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public static void main(String[] args) throws ParseException {
        Date date = DateUtils.addDays(DateUtils.parseDate("20141119", new String[] { "yyyyMMdd" }), -5);
        System.out.println(DateFormatUtils.format(date, "yyyyMMdd"));
    }

    @Override
    public IResult<?> updateDebtAudit(AssignmentDebtDo assignmentDebtDo) {
        IResult<String> result = new ResultSupport<String>();
        try {
            assignmentDebtComponent.updateDebtAudit(assignmentDebtDo);
            result.setSuccess(true);
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setErrorMessage(e.getMessage());
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("操作失败,请稍后再试!");
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public IResult<?> addPurchaseDebt(Long auctionUserId, Long debtId, Double auctionPrice) {
        IResult<String> result = new ResultSupport<String>();
        try {
            // 校验债权记录以及用户账户余额等
            checkPurchaseDebt(auctionUserId, debtId, auctionPrice);

            // 新增债权认购信息
            Long auctionDebtId = addAuctionDebt(auctionUserId, debtId, auctionPrice);

            // 发送债权转让申请请求到汇付
            return creditAssign(auctionDebtId);
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setErrorMessage(e.getMessage());
            logger.warn("债权[" + debtId + "]认购失败" + e.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("操作失败,请稍后再试!");
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 新增债权转让认购信息
     * 
     * @param auctionerId
     * @param debtId
     * @param auctionPrice
     * @return
     * @author: liuzgmf
     * @date: 2014年10月8日下午3:34:12
     */
    private Long addAuctionDebt(Long auctionerId, long debtId, double auctionPrice) {
        AuctionDebtDo auctionDebtDo = new AuctionDebtDo();
        auctionDebtDo.setId(investComponent.getAutoIncrementId());
        auctionDebtDo.setUserId(auctionerId);
        auctionDebtDo.setDebtId(debtId);
        auctionDebtDo.setAuctionPrice(auctionPrice);
        auctionDebtDo.setAuctionTime(new Date());
        auctionDebtDo.setAutiontimes(1);
        auctionDebtDo.setAuctionStatus(AuctionStatus.PROCESSING);
        return auctionDebtComponent.addAuctionDebt(auctionDebtDo);
    }

    /**
     * 债权转让购买
     * 
     * @param auctionUserId
     * @param debtId
     * @param auctionPrice
     * @return
     * @author: liuzgmf
     * @date: 2014年9月29日上午11:23:14
     */
    private IResult<?> creditAssign(Long auctionDebtId) {
        InParameter inParameter = new InParameter();
        inParameter.setOrdId(auctionDebtId + "");
        inParameter.setRetUrl("creditAssign.do");
        inParameter.setBgRetUrl("creditAssignBg.do");
        AuctionDebtDo auctionDebtDo = auctionDebtComponent.getById(auctionDebtId);
        AssignmentDebtDo assignmentDebtDo = assignmentDebtComponent.getById(auctionDebtDo.getDebtId());
        AccountUserDo userDo = userComponent.getById(assignmentDebtDo.getAlienatorId());
        inParameter.getParams().put("SellCustId", userDo.getUsrCustId());
        InvestDo investDo = investComponent.getById(assignmentDebtDo.getInvestId());
        inParameter.getParams().put("CreditAmt",
                CalculateUtils.sub(investDo.getRecivedPrincipal(), investDo.getHasPrincipal()));
        inParameter.getParams().put("CreditDealAmt", auctionDebtDo.getAuctionPrice());
        inParameter.getParams().put("BidDetails",
                createBidDetails(auctionDebtDo.getUserId(), auctionDebtDo.getDebtId()));
        inParameter.getParams().put("Fee", "0.00");
        inParameter.getParams().put("DivDetails", "");
        AccountUserDo auctionUserDo = userComponent.getById(auctionDebtDo.getUserId());
        inParameter.getParams().put("BuyCustId", auctionUserDo.getUsrCustId());
        inParameter.getParams().put("OrdDate", DateFormatUtils.format(new Date(), "yyyyMMdd"));
        inParameter.getParams().put("MerPriv", "");
        OutParameter outParameter = accountManagerService.creditAssign(inParameter, AccountType.CHINAPNR);

        IResult<String> result = new ResultSupport<String>();
        if (outParameter.isSuccess()) {
            result.setSuccess(true);
            result.setModel((String) outParameter.getParams().get("htmlText"));
        } else {
            result.setSuccess(false);
            result.setErrorMessage(outParameter.getRespDesc());
        }
        return result;
    }

    /**
     * 创建债权转让明细信息
     * 
     * @param userId
     * @param debtId
     * @return
     * @author: liuzgmf
     * @date: 2014年9月29日下午2:37:56
     */
    private String createBidDetails(Long userId, Long debtId) {
        Map<String, Object> borrowerDetailMap = new LinkedHashMap<String, Object>();
        AssignmentDebtDo assignmentDebtDo = assignmentDebtComponent.getById(debtId);
        BorrowDo borrowDo = borrowComponent.getById(assignmentDebtDo.getBorrowId());
        AccountUserDo userDo = userComponent.getById(borrowDo.getPublisher());
        borrowerDetailMap.put("BorrowerCustId", userDo.getUsrCustId());
        InvestDo investDo = investComponent.getById(assignmentDebtDo.getInvestId());
        DecimalFormat df = new DecimalFormat("##0.00");
        borrowerDetailMap.put("BorrowerCreditAmt",
                df.format(CalculateUtils.sub(investDo.getRecivedPrincipal(), investDo.getHasPrincipal())));
        borrowerDetailMap.put("PrinAmt", "0.00");

        List<Object> borrowerDetails = new ArrayList<Object>();
        borrowerDetails.add(borrowerDetailMap);

        Map<String, Object> bidDetailMap = new LinkedHashMap<String, Object>();
        if (assignmentDebtDo.getParentId() != null && assignmentDebtDo.getParentId().longValue() > 0) {
            AuctionDebtDo auctionDebtDo = auctionDebtComponent.getByDebtId(assignmentDebtDo.getParentId());
            bidDetailMap.put("BidOrdId", auctionDebtDo.getId());
        } else {
            bidDetailMap.put("BidOrdId", assignmentDebtDo.getInvestId());
        }
        bidDetailMap.put("BidOrdDate", DateFormatUtils.format(investDo.getInvestTime(), "yyyyMMdd"));
        bidDetailMap.put("BidCreditAmt",
                df.format(CalculateUtils.sub(investDo.getRecivedPrincipal(), investDo.getHasPrincipal())));
        bidDetailMap.put("BorrowerDetails", borrowerDetails);

        List<Object> bidDetails = new ArrayList<Object>();
        bidDetails.add(bidDetailMap);

        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put("BidDetails", bidDetails);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(params);
        } catch (JsonGenerationException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 校验用户是否可以购买债权转让
     * 
     * @param userId
     * @param auctionDebtId
     * @param auctionPrice
     * @return
     * @author: liuzgmf
     * @date: 2014年9月26日下午4:46:13
     */
    private boolean checkPurchaseDebt(Long auctionerId, Long debtId, Double auctionPrice) {
        AssignmentDebtDo assignmentDebtDo = assignmentDebtComponent.getById(debtId);
        if (assignmentDebtDo == null) {
            throw new BusinessException("债权转让记录不存在!");
        }
        AccountUserDo userDo = userComponent.getById(auctionerId);
        if (userDo == null) {
            throw new BusinessException("用户记录不存在!");
        }
        Date endDate = DateUtils.addDays(assignmentDebtDo.getPublishTime(), assignmentDebtDo.getAuctionDays());
        if (new Date().after(endDate)) {
            throw new BusinessException("债权转让记录已过期!");
        }
        if (assignmentDebtDo.getAlienatorId().longValue() == auctionerId.longValue()) {
            throw new BusinessException("不能购买自己转让的的债权!");
        }
        if (CalculateUtils.lt(userDo.getUsableSum(), auctionPrice)) {
            throw new BusinessException("账户可用余额不足!");
        }
        BorrowDo borrowDo = borrowComponent.getById(assignmentDebtDo.getBorrowId());
        if (borrowDo.getPublisher().longValue() == auctionerId.longValue()) {
            throw new BusinessException("借款者不能购买该债权!");
        }
        if (assignmentDebtDo.getDebtStatus().intValue() != 2) {
            throw new BusinessException("非转让中的债权，购买失败!");
        }
        return true;
    }

    /**
     * 校验用户是否可以购买债权转让
     * 
     * @param auctionDebtId
     * @param auctionPrice
     * @return
     * @author: liuzgmf
     * @date: 2014年11月3日下午2:41:22
     */
    private boolean checkPurchaseDebt(long auctionDebtId, double auctionPrice) {
        AuctionDebtDo auctionDebtDo = auctionDebtComponent.getById(auctionDebtId);
        AccountUserDo userDo = userComponent.getById(auctionDebtDo.getUserId());
        if (CalculateUtils.lt(userDo.getUsableSum(), auctionPrice)) {
            throw new BusinessException("账户可用余额不足!");
        }
        AssignmentDebtDo assignmentDebtDo = assignmentDebtComponent.getById(auctionDebtDo.getDebtId());
        if (assignmentDebtDo.getDebtStatus().intValue() != 2) {
            throw new BusinessException("非转让中的债权，购买失败!");
        }
        return true;
    }

    @Override
    public IResult<?> updatePurchaseDebt(long auctionDebtId, double auctionPrice) {
        IResult<String> result = new ResultSupport<String>();
        try {
            // 校验债权转让是否已成功处理
            AuctionDebtDo auctionDebtDo = auctionDebtComponent.getById(auctionDebtId);
            if (auctionDebtDo.getAuctionStatus().equals(AuctionStatus.SUCCESS)) {
                result.setSuccess(true);
                return result;
            }

            // 校验债权转让数据是否合法
            checkPurchaseDebt(auctionDebtId, auctionPrice);

            // 平台认购债权处理
            assignmentDebtComponent.updatePurchaseDebt(auctionDebtId, auctionPrice);
            result.setSuccess(true);
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setErrorMessage(e.getMessage());
            logger.warn("债权认购[" + auctionDebtId + "]失败" + e.getMessage());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("操作失败,请稍后再试!");
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public Boolean updateAuctionStatus(String ordId, AuctionStatus targetStatus, AuctionStatus sourceStatus) {
        return auctionDebtComponent.updateAuctionStatus(ordId, targetStatus, sourceStatus);
    }

    @Override
    public Map<String, Object> trfReconciliation(String beginDate, String endDate, String pageNum) {
        InParameter inParameter = new InParameter();
        if (StringUtils.isEmpty(beginDate) || StringUtils.isEmpty(endDate)) {
            beginDate = DateFormatUtils.format(DateUtils.addDays(new Date(), -5), "yyyyMMdd");
            endDate = DateFormatUtils.format(DateUtils.addDays(new Date(), 1), "yyyyMMdd");
        }
        inParameter.getParams().put("BeginDate", beginDate);
        inParameter.getParams().put("EndDate", endDate);
        inParameter.getParams().put("PageNum", (StringUtils.isEmpty(pageNum) ? 1 : pageNum));
        inParameter.getParams().put("PageSize", 1000);

        OutParameter outParameter = accountManagerService.trfReconciliation(inParameter, AccountType.CHINAPNR);

        return outParameter.getParams();
    }

    @Override
    public Map<String, Object> reconciliation(String beginDate, String endDate, String pageNum, String queryTransType) {
        InParameter inParameter = new InParameter();
        if (StringUtils.isEmpty(beginDate) || StringUtils.isEmpty(endDate)) {
            beginDate = DateFormatUtils.format(DateUtils.addDays(new Date(), -5), "yyyyMMdd");
            endDate = DateFormatUtils.format(DateUtils.addDays(new Date(), 1), "yyyyMMdd");
        }
        inParameter.getParams().put("BeginDate", beginDate);
        inParameter.getParams().put("EndDate", endDate);
        inParameter.getParams().put("PageNum", (StringUtils.isEmpty(pageNum) ? 1 : pageNum));
        inParameter.getParams().put("PageSize", 1000);
        inParameter.getParams().put("QueryTransType", queryTransType);

        OutParameter outParameter = accountManagerService.reconciliation(inParameter, AccountType.CHINAPNR);

        return outParameter.getParams();
    }

    @Override
    public Map<String, Object> saveReconciliation(String beginDate, String endDate, String pageNum) {
        InParameter inParameter = new InParameter();
        if (StringUtils.isEmpty(beginDate) || StringUtils.isEmpty(endDate)) {
            beginDate = DateFormatUtils.format(DateUtils.addDays(new Date(), -5), "yyyyMMdd");
            endDate = DateFormatUtils.format(DateUtils.addDays(new Date(), 1), "yyyyMMdd");
        }
        inParameter.getParams().put("BeginDate", beginDate);
        inParameter.getParams().put("EndDate", endDate);
        inParameter.getParams().put("PageNum", (StringUtils.isEmpty(pageNum) ? 1 : pageNum));
        inParameter.getParams().put("PageSize", 1000);

        OutParameter outParameter = accountManagerService.saveReconciliation(inParameter, AccountType.CHINAPNR);

        return outParameter.getParams();
    }

    @Override
    public Map<String, Object> cashReconciliation(String beginDate, String endDate, String pageNum) {
        InParameter inParameter = new InParameter();
        if (StringUtils.isEmpty(beginDate) || StringUtils.isEmpty(endDate)) {
            beginDate = DateFormatUtils.format(DateUtils.addDays(new Date(), -5), "yyyyMMdd");
            endDate = DateFormatUtils.format(DateUtils.addDays(new Date(), 1), "yyyyMMdd");
        }
        inParameter.getParams().put("BeginDate", beginDate);
        inParameter.getParams().put("EndDate", endDate);
        inParameter.getParams().put("PageNum", (StringUtils.isEmpty(pageNum) ? 1 : pageNum));
        inParameter.getParams().put("PageSize", 1000);

        OutParameter outParameter = accountManagerService.cashReconciliation(inParameter, AccountType.CHINAPNR);

        return outParameter.getParams();
    }

    @Override
    public Map<String, Object> queryAcctDetails(Long userId) {
        InParameter inParameter = new InParameter();
        AccountUserDo userDo = userComponent.getById(userId);
        inParameter.getParams().put("UsrCustId", userDo.getUsrCustId());

        OutParameter outParameter = accountManagerService.queryAcctDetails(inParameter, AccountType.CHINAPNR);
        return outParameter.getParams();
    }

}
