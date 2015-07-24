/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.trade.impl
 * @Title: AssignmentDebtComponentImpl.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年9月26日 下午2:57:41
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.trade.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.trade.dataobject.AssignmentDebtDo;
import com.hehenian.biz.common.trade.dataobject.AuctionDebtDo;
import com.hehenian.biz.common.trade.dataobject.AuctionDebtDo.AuctionStatus;
import com.hehenian.biz.common.trade.dataobject.BorrowDo;
import com.hehenian.biz.common.trade.dataobject.FundrecordDo;
import com.hehenian.biz.common.trade.dataobject.InvestDo;
import com.hehenian.biz.common.trade.dataobject.InvestHistoryDo;
import com.hehenian.biz.common.trade.dataobject.InvestRepaymentDo;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.common.util.StringUtil;
import com.hehenian.biz.component.account.IUserComponent;
import com.hehenian.biz.component.trade.IAssignmentDebtComponent;
import com.hehenian.biz.component.trade.IAuctionDebtComponent;
import com.hehenian.biz.component.trade.IFundrecordComponent;
import com.hehenian.biz.component.trade.IInvestComponent;
import com.hehenian.biz.component.trade.IInvestHistoryComponnet;
import com.hehenian.biz.component.trade.IInvestRepaymentComponent;
import com.hehenian.biz.component.trade.IOperationLogComponent;
import com.hehenian.biz.dal.account.IUserDao;
import com.hehenian.biz.dal.trade.IAssignmentDebtDao;
import com.hehenian.biz.dal.trade.IAuctionDebtDao;
import com.hehenian.biz.dal.trade.IBorrowDao;
import com.hehenian.biz.dal.trade.IInvestDao;
import com.hehenian.biz.dal.trade.IInvestRepaymentDao;

/**
 * 
 * @author: liuzgmf
 * @date 2014年9月26日 下午2:57:41
 */
@Component("assignmentDebtComponent")
public class AssignmentDebtComponentImpl implements IAssignmentDebtComponent {
    @Autowired
    private IOperationLogComponent    operationLogComponent;
    @Autowired
    private IInvestComponent          investComponent;
    @Autowired
    private IUserComponent            userComponent;
    @Autowired
    private IAuctionDebtComponent     auctionDebtComponent;
    @Autowired
    private IInvestRepaymentComponent investRepaymentComponent;
    @Autowired
    private IFundrecordComponent      fundrecordComponent;
    @Autowired
    private IInvestHistoryComponnet   investHistoryComponnet;
    @Autowired
    private IAssignmentDebtDao        assignmentDebtDao;
    @Autowired
    private IInvestRepaymentDao       investRepaymentDao;
    @Autowired
    private IUserDao                  accountUserDao;
    @Autowired
    private IInvestDao                investDao;
    @Autowired
    private IBorrowDao                borrowDao;
    @Autowired
    private IAuctionDebtDao           auctionDebtDao;

    @Override
    public boolean isHaveAssignmentDebt(Long investId, Long alienatorId) {
        Map<String, Object> searchItems = new HashMap<String, Object>();
        searchItems.put("investId", investId);
        searchItems.put("alienatorId", alienatorId);
        searchItems.put("debtStatuses", new Integer[] { 1, 2 });
        int count = assignmentDebtDao.isHaveAssignmentDebt(searchItems);
        return (count == 0);
    }

    @Override
    public Long addAssignmentDebt(AssignmentDebtDo assignmentDebtDo) {
        AssignmentDebtDo localAssignmentDebtDo = assignmentDebtDao.getByInvestIdAndAuctionerId(
                assignmentDebtDo.getInvestId(), assignmentDebtDo.getAlienatorId());
        if (localAssignmentDebtDo != null) {
            assignmentDebtDo.setParentId(localAssignmentDebtDo.getId());
        } else {
            assignmentDebtDo.setParentId(0l);
        }
        int count = assignmentDebtDao.addAssignmentDebt(assignmentDebtDo);
        if (count != 1) {
            throw new BusinessException("新增债权转让申请出错!");
        }

        AccountUserDo userDo = accountUserDao.getById(assignmentDebtDo.getAlienatorId());
        operationLogComponent.addOperationLog("t_assignment_debt", userDo.getUsername(), 1, userDo.getLastIP(), 0d,
                "发布债权转让", 1);
        return assignmentDebtDo.getId();
    }

    @Override
    public boolean updateDebtAudit(AssignmentDebtDo assignmentDebtDo) {
        // 修改债权转让申请记录
        updateAssignmentDebt(assignmentDebtDo);

        if (assignmentDebtDo.getDebtStatus().intValue() == 6) {
            InvestDo udpateInvestDo = new InvestDo();
            udpateInvestDo.setId(assignmentDebtDo.getInvestId());
            udpateInvestDo.setIsDebt(1);// 1表示为未债权转让
            investComponent.updateInvest(udpateInvestDo);
        }
        return false;
    }

    /**
     * 修改债权转让申请记录
     *
     * @param assignmentDebtDo
     * @return
     * @author: liuzgmf
     * @date: 2014年9月30日下午4:08:08
     */
    @Override
    public Long updateAssignmentDebt(AssignmentDebtDo assignmentDebtDo) {
        int count = assignmentDebtDao.updateAssignmentDebt(assignmentDebtDo);
        if (count != 1) {
            throw new BusinessException("修改债权转让申请出错!");
        }
        return assignmentDebtDo.getId();
    }

    @Override
    public boolean updatePurchaseDebt(long auctionDebtId, double auctionPrice) {
        // 修改债权认购记录的状态
        updateAuctionDebt(auctionDebtId, auctionPrice);

        // 修改债权转让申请记录，包括购买金额，购买用户ID
        updateAssignmentDebt(auctionDebtId, auctionPrice);

        // 新增投资收款记录
        addInvestRepayment(auctionDebtId, auctionPrice);

        // 修改投资记录
        updateInvest(auctionDebtId);

        // 修改竞拍者账户金额，并增加资金变动记录
        updateAuctionAccount(auctionDebtId, auctionPrice);

        // 修改转让者账户金额，并增加资金变动记录
        updateAlientorAccount(auctionDebtId, auctionPrice);

        // 扣除转让管理费
        deductManageFee(auctionDebtId, auctionPrice);

        return true;
    }

    /**
     * 修改债权认购记录的状态
     * 
     * @param auctionDebtId
     * @param auctionPrice
     * @author: liuzgmf
     * @date: 2014年10月24日下午5:04:31
     */
    private void updateAuctionDebt(long auctionDebtId, double auctionPrice) {
        AuctionDebtDo updAuctionDebtDo = new AuctionDebtDo();
        updAuctionDebtDo.setId(auctionDebtId);
        updAuctionDebtDo.setAuctionPrice(auctionPrice);
        updAuctionDebtDo.setAuctionStatus(AuctionStatus.SUCCESS);
        auctionDebtComponent.updateAuctionDebt(updAuctionDebtDo);
    }

    /**
     * 修改债权转让申请记录，包括购买金额，购买用户ID
     * 
     * @param auctionerId
     * @param debtId
     * @param auctionPrice
     * @author: liuzgmf
     * @date: 2014年10月8日下午3:33:37
     */
    private void updateAssignmentDebt(long auctionDebtId, double auctionPrice) {
        AssignmentDebtDo updAssignmentDebtDo = new AssignmentDebtDo();
        updAssignmentDebtDo.setAuctionHighPrice(auctionPrice);
        AuctionDebtDo auctionDebtDo = auctionDebtDao.getById(auctionDebtId);
        updAssignmentDebtDo.setAuctionerId(auctionDebtDo.getUserId());
        updAssignmentDebtDo.setAuctionEndTime(new Date());
        updAssignmentDebtDo.setDebtStatus(3);
        updAssignmentDebtDo.setManageFee(calManageFee(auctionDebtDo.getDebtId()));// 债权转让管理费
        updAssignmentDebtDo.setId(auctionDebtDo.getDebtId());
        updatePurchaseDebt(updAssignmentDebtDo);
    }

    /**
     * 认购债权成功修改债权转让记录
     * 
     * @param updAssignmentDebtDo
     * @author: liuzgmf
     * @date: 2014年11月3日下午3:12:20
     */
    private int updatePurchaseDebt(AssignmentDebtDo updAssignmentDebtDo) {
        int count = assignmentDebtDao.updatePurchaseDebt(updAssignmentDebtDo);
        if (count != 1) {
            throw new BusinessException("修改债权[" + updAssignmentDebtDo.getId() + "]记录失败!");
        }
        return count;
    }

    /**
     * 新增投资收款记录
     * 
     * @param auctionerId
     * @param debtId
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @author: liuzgmf
     * @date: 2014年10月8日下午3:31:20
     */
    private void addInvestRepayment(long auctionDebtId, double auctionPrice) {
        AuctionDebtDo auctionDebtDo = auctionDebtDao.getById(auctionDebtId);
        AssignmentDebtDo assignmentDebtDo = assignmentDebtDao.getById(auctionDebtDo.getDebtId());
        List<InvestRepaymentDo> investRepaymentDoList = investRepaymentDao.queryByInvestIdAndOwner(
                assignmentDebtDo.getInvestId(), assignmentDebtDo.getAlienatorId());
        for (InvestRepaymentDo investRepaymentDo : investRepaymentDoList) {
            if (investRepaymentDo.getRepayStatus().intValue() != 1) {
                continue;
            }
            InvestRepaymentDo newInvestRepaymentDo = new InvestRepaymentDo();
            BeanUtils.copyProperties(investRepaymentDo, newInvestRepaymentDo);
            newInvestRepaymentDo.setOwner(auctionDebtDo.getUserId());
            newInvestRepaymentDo.setOwnerlist(newInvestRepaymentDo.getOwnerlist() + "," + auctionDebtDo.getUserId());
            // 新增认购者的回款记录
            investRepaymentComponent.addInvestRepayment(newInvestRepaymentDo);

            // 标示转让者回款记录为已转让
            investRepaymentComponent.updateRepayStatus(4, 2, investRepaymentDo.getId());
        }
    }

    /**
     * 修改投资记录
     * 
     * @param debtId
     * @author: liuzgmf
     * @date: 2014年10月27日上午11:24:16
     */
    private void updateInvest(long auctionDebtId) {
        AuctionDebtDo auctionDebtDo = auctionDebtDao.getById(auctionDebtId);
        AssignmentDebtDo assignmentDebtDo = assignmentDebtDao.getById(auctionDebtDo.getDebtId());
        InvestDo localInvestDo = investDao.getById(assignmentDebtDo.getInvestId());
        InvestHistoryDo investHistoryDo = new InvestHistoryDo();
        BeanUtils.copyProperties(localInvestDo, investHistoryDo);
        investHistoryDo.setRecivedPrincipal(localInvestDo.getHasPrincipal());
        investHistoryDo.setRecievedInterest(localInvestDo.getHasInterest());
        investHistoryDo.setRepayStatus(2);
        investHistoryDo.setRecivedFI(localInvestDo.getHasFI());
        investHistoryDo.setManageFee(0.00);
        investHistoryComponnet.addInvestHistory(investHistoryDo);

        InvestDo updInvestDo = new InvestDo();
        updInvestDo.setId(localInvestDo.getId());
        updInvestDo.setHasFI(0.00);
        Double realAmount = CalculateUtils.sub(localInvestDo.getRecivedPrincipal(), localInvestDo.getHasPrincipal());
        updInvestDo.setRealAmount(realAmount);// 投资金额
        updInvestDo.setRecivedPrincipal(realAmount);// 应收本金
        Double recievedInterest = CalculateUtils.sub(localInvestDo.getRecievedInterest(),
                localInvestDo.getHasInterest());
        updInvestDo.setRecievedInterest(recievedInterest);// 应收利息
        updInvestDo.setHasPrincipal(0.00);
        updInvestDo.setHasInterest(0.00);
        updInvestDo.setIsDebt(2);
        updInvestDo.setManageFee(0.00);
        updInvestDo.setRecivedFI(CalculateUtils.sub(localInvestDo.getRecivedFI(), localInvestDo.getHasFI()));// 应收罚金
        updInvestDo.setHasFI(0.00);
        investComponent.updateInvest(updInvestDo);
    }

    /**
     * 扣除债权转让管理费
     * 
     * @param auctionDebtId
     * @param auctionPrice
     * @author: liuzgmf
     * @date: 2014年10月8日下午3:18:40
     */
    private void deductManageFee(long auctionDebtId, double auctionPrice) {
        AuctionDebtDo auctionDebtDo = auctionDebtDao.getById(auctionDebtId);
        Double managefee = calManageFee(auctionDebtDo.getDebtId());
        if (CalculateUtils.le(managefee, 0)) {
            return;
        }

        AssignmentDebtDo assignmentDebtDo = assignmentDebtDao.getById(auctionDebtDo.getDebtId());
        userComponent.updateAmount(-managefee, 0.00, assignmentDebtDo.getAlienatorId());
    }

    /**
     * 计算债权转让管理费
     * 
     * @param debtId
     * @return
     * @author: liuzgmf
     * @date: 2014年10月8日下午3:22:45
     */
    private Double calManageFee(Long debtId) {
        ObjectMapper mapper = new ObjectMapper();
        AssignmentDebtDo assignmentDebtDo = assignmentDebtDao.getById(debtId);
        BorrowDo borrowDo = borrowDao.getById(assignmentDebtDo.getBorrowId());
        try {
            Map<String, String> params = mapper.readValue(borrowDo.getFeelog(),
                    new TypeReference<HashMap<String, String>>() {
                    });
            return CalculateUtils.mul(assignmentDebtDo.getDebtSum(),
                    StringUtil.strToDouble(params.get("creditTransferFeeRate")));// 债权转让管理费
        } catch (Exception e) {
            throw new RuntimeException("计算债权转让[" + debtId + "]管理费失败!");
        }
    }

    /**
     * 修改转让者账户金额，并增加资金变动记录
     * 
     * @param auctionDebtId
     * @param auctionPrice
     * @author: liuzgmf
     * @date: 2014年10月8日下午3:10:26
     */
    private void updateAlientorAccount(long auctionDebtId, double auctionPrice) {
        AuctionDebtDo auctionDebtDo = auctionDebtDao.getById(auctionDebtId);
        // 修改转让者账户金额
        AccountUserDo userDo = new AccountUserDo();
        AssignmentDebtDo assignmentDebtDo = assignmentDebtDao.getById(auctionDebtDo.getDebtId());
        userDo.setId(assignmentDebtDo.getAlienatorId());
        userDo.setUsableSum(auctionPrice);
        userDo.setDueinSum(-assignmentDebtDo.getDebtSum());// 待收金额（待还本金+待还利息）
        userComponent.updateAmount(userDo);

        // 转让者资金变更记录
        FundrecordDo fundrecordDo = new FundrecordDo();
        fundrecordDo.setUserId(assignmentDebtDo.getAlienatorId());
        fundrecordDo.setFundMode("债权转让成功");
        fundrecordDo.setHandleSum(auctionPrice);
        AccountUserDo alienatorUserDo = accountUserDao.getById(assignmentDebtDo.getAlienatorId());
        fundrecordDo.setUsableSum(alienatorUserDo.getUsableSum());
        fundrecordDo.setFreezeSum(alienatorUserDo.getFreezeSum());
        Double dueinSum = investDao.getDueinSum(assignmentDebtDo.getAlienatorId());
        fundrecordDo.setDueinSum(dueinSum == null ? 0.00 : dueinSum);// 代收金额
        fundrecordDo.setTrader(assignmentDebtDo.getAlienatorId());
        fundrecordDo.setRecordTime(new Date());
        BorrowDo borrowDo = borrowDao.getById(assignmentDebtDo.getBorrowId());
        String remark = "债权转让[" + borrowDo.getBorrowTitle() + "]竞拍成功";
        fundrecordDo.setRemarks(remark);
        fundrecordDo.setIncome(auctionPrice);
        fundrecordDo.setOperateType(201);
        fundrecordComponent.addFundrecord(fundrecordDo);

        // 新增转让者代收金额资金变动记录
        // fundrecordDo = new FundrecordDo();
        // fundrecordDo.setUserId(assignmentDebtDo.getAlienatorId());
        // fundrecordDo.setFundMode("待收金额减少");
        // InvestDo localInvestDo =
        // investDao.getById(assignmentDebtDo.getInvestId());
        // Double realAmount =
        // CalculateUtils.sub(localInvestDo.getRecivedPrincipal(),
        // localInvestDo.getHasPrincipal());
        // Double recievedInterest =
        // CalculateUtils.sub(localInvestDo.getRecievedInterest(),
        // localInvestDo.getHasInterest());
        // fundrecordDo.setHandleSum(CalculateUtils.add(realAmount,
        // recievedInterest));
        // fundrecordDo.setUsableSum(alienatorUserDo.getUsableSum());
        // fundrecordDo.setFreezeSum(alienatorUserDo.getFreezeSum());
        // dueinSum = investDao.getDueinSum(assignmentDebtDo.getAlienatorId());
        // fundrecordDo.setDueinSum(dueinSum == null ? 0.00 : dueinSum);// 代收金额
        // fundrecordDo.setTrader(auctionerId);
        // fundrecordDo.setRecordTime(new Date());
        // remark = "债权转让[" + borrowDo.getBorrowTitle() + "]竞拍成功,待收金额减少";
        // fundrecordDo.setRemarks(remark);
        // fundrecordDo.setOperateType(1003);
        // fundrecordComponent.addFundrecord(fundrecordDo);
    }

    /**
     * 修改竞拍者账户金额，并增加资金变动记录
     * 
     * @param auctionDebtId
     * @param auctionPrice
     * @author: liuzgmf
     * @date: 2014年10月8日下午2:09:30
     */
    private void updateAuctionAccount(long auctionDebtId, double auctionPrice) {
        AuctionDebtDo auctionDebtDo = auctionDebtDao.getById(auctionDebtId);
        // 变更竞拍者账户的金额，（可用金额-债权认购金额；代收金额+债权转让金额）
        AccountUserDo userDo = new AccountUserDo();
        userDo.setId(auctionDebtDo.getUserId());
        userDo.setUsableSum(-auctionPrice);
        AssignmentDebtDo assignmentDebtDo = assignmentDebtDao.getById(auctionDebtDo.getDebtId());
        userDo.setDueinSum(assignmentDebtDo.getDebtSum());// 待收金额（待还本金+待还利息）
        userComponent.updateAmount(userDo);

        // 债权认购者资金变动记录
        FundrecordDo fundrecordDo = new FundrecordDo();
        fundrecordDo.setUserId(auctionDebtDo.getUserId());
        fundrecordDo.setRecordTime(new Date());
        fundrecordDo.setOperateType(702);
        fundrecordDo.setFundMode("债权认购成功");
        fundrecordDo.setHandleSum(auctionPrice);
        AccountUserDo auctionUserDo = accountUserDao.getById(auctionDebtDo.getUserId());
        fundrecordDo.setUsableSum(auctionUserDo.getUsableSum());// 可用金额
        fundrecordDo.setFreezeSum(auctionUserDo.getFreezeSum());// 冻结金额
        Double dueinSum = investDao.getDueinSum(auctionDebtDo.getUserId());
        fundrecordDo.setDueinSum(dueinSum == null ? 0.00 : dueinSum);// 代收金额
        fundrecordDo.setTrader(-1l);
        fundrecordDo.setDueoutSum(0.00);
        BorrowDo borrowDo = borrowDao.getById(assignmentDebtDo.getBorrowId());
        fundrecordDo.setRemarks("债权转让[" + borrowDo.getBorrowTitle() + "]竞拍成功");
        fundrecordDo.setOninvest(0.00);
        fundrecordDo.setPaynumber(auctionDebtDo.getDebtId() + "");
        fundrecordDo.setPaybank("");
        fundrecordDo.setCost(0.00);
        fundrecordDo.setSpending(auctionPrice);
        fundrecordComponent.addFundrecord(fundrecordDo);

        // 新增竞拍者代收金额资金变动记录
        // InvestDo localInvestDo =
        // investDao.getById(assignmentDebtDo.getInvestId());
        // Double realAmount =
        // CalculateUtils.sub(localInvestDo.getRecivedPrincipal(),
        // localInvestDo.getHasPrincipal());
        // Double recievedInterest =
        // CalculateUtils.sub(localInvestDo.getRecievedInterest(),
        // localInvestDo.getHasInterest());
        // fundrecordDo = new FundrecordDo();
        // fundrecordDo.setUserId(auctionerId);
        // fundrecordDo.setFundMode("待收金额增加");
        // fundrecordDo.setHandleSum(CalculateUtils.add(realAmount,
        // recievedInterest));
        // fundrecordDo.setUsableSum(auctionUserDo.getUsableSum());
        // fundrecordDo.setFreezeSum(auctionUserDo.getFreezeSum());
        // fundrecordDo.setDueinSum(dueinSum == null ? 0.00 : dueinSum);// 代收金额
        // fundrecordDo.setTrader(assignmentDebtDo.getAlienatorId());
        // fundrecordDo.setRecordTime(new Date());
        // String remarks = "债权转让[" + borrowDo.getBorrowTitle() + "]成功,待收金额增加";
        // fundrecordDo.setRemarks(remarks);
        // fundrecordDo.setOperateType(1005);
        // fundrecordComponent.addFundrecord(fundrecordDo);
    }

    @Override
    public AssignmentDebtDo getById(Long debtId) {
        return assignmentDebtDao.getById(debtId);
    }

    @Override
    public AssignmentDebtDo getLastAssignmentDebtByInvestId(Long investId, Long owner) {
        AssignmentDebtDo ad = assignmentDebtDao.getAssignmentDebtByInvestId(investId, owner);
        if (null == ad) {
            return null;
        }
        AuctionDebtDo aud = auctionDebtComponent.getByDebtId(ad.getId());
        ad.setAuctionDebtDo(aud);
        return ad;
    }

    @Override
    public AssignmentDebtDo getSuccessInvestAssignmentDebt(Long investId, Long owner) {
        AssignmentDebtDo ad = assignmentDebtDao.getSuccessInvestAssignmentDebt(investId, owner);
        if (null == ad) {
            return null;
        }
        AuctionDebtDo aud = auctionDebtComponent.getByDebtId(ad.getId());
        ad.setAuctionDebtDo(aud);
        return ad;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hehenian.biz.component.trade.IAssignmentDebtComponent#
	 * updateDebtStatusFailure(long)
	 */
	@Override
	public void updateDebtStatusFailure(long borrowId) {
		assignmentDebtDao.updateDebtStatusFailure(borrowId);

	}

}
