package com.hehenian.biz.component.trade.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.account.dataobject.AdminDo;
import com.hehenian.biz.common.exception.BusinessException;
import com.hehenian.biz.common.settle.SettleCalculator;
import com.hehenian.biz.common.settle.SettleCalculatorUtils;
import com.hehenian.biz.common.system.dataobject.SettDetailDo;
import com.hehenian.biz.common.system.dataobject.SettSchemeDo;
import com.hehenian.biz.common.system.dataobject.SettSchemeDo.SettleType;
import com.hehenian.biz.common.trade.dataobject.BorrowDo;
import com.hehenian.biz.common.trade.dataobject.FundrecordDo;
import com.hehenian.biz.common.trade.dataobject.InvestDo;
import com.hehenian.biz.common.trade.dataobject.InvestRepaymentDo;
import com.hehenian.biz.common.trade.dataobject.PreRepaymentDo;
import com.hehenian.biz.common.trade.dataobject.RepaymentDo;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.component.account.IUserComponent;
import com.hehenian.biz.component.system.ISettSchemeComponent;
import com.hehenian.biz.component.trade.IBorrowComponent;
import com.hehenian.biz.component.trade.IFundrecordComponent;
import com.hehenian.biz.component.trade.IInvestComponent;
import com.hehenian.biz.component.trade.IInvestRepaymentComponent;
import com.hehenian.biz.component.trade.IOperationLogComponent;
import com.hehenian.biz.component.trade.IPreRepaymentComponent;
import com.hehenian.biz.component.trade.IRepaymentComponent;
import com.hehenian.biz.dal.account.IAdminDao;
import com.hehenian.biz.dal.account.IUserDao;
import com.hehenian.biz.dal.trade.IBorrowDao;
import com.hehenian.biz.dal.trade.IInvestDao;
import com.hehenian.biz.dal.trade.IPlatformCostDao;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

@Component("borrowComponent")
public class BorrowComponentImpl implements IBorrowComponent {
    @Autowired
    private IPreRepaymentComponent    preRepaymentComponent;
    @Autowired
    private IUserComponent            userComponent;
    @Autowired
    private IFundrecordComponent      fundrecordComponent;
    @Autowired
    private IInvestRepaymentComponent investRepaymentComponent;
    @Autowired
    private IInvestComponent          investComponent;
    @Autowired
    private IRepaymentComponent       repaymentComponent;
    @Autowired
    private IInvestDao                investDao;
    @Autowired
    private IBorrowDao                borrowDao;
    @Autowired
    private ISettSchemeComponent      settSchemeComponent;
    @Autowired
    private IUserDao                  userDao;
    @Autowired
    private IPlatformCostDao          platformCostDao;
    @Autowired
    private IAdminDao                 adminDao;
    @Autowired
    private IOperationLogComponent    operationLogComponent;

    /**
     * 根据ID 查询
     * 
     * @parameter id
     */
    public BorrowDo getById(Long id) {
        return borrowDao.getById(id);
    }

    /**
     * 根据条件查询列表
     */
    public List<BorrowDo> selectBorrow(Map<String, Object> parameterMap) {
        return borrowDao.selectBorrow(parameterMap);
    }

    /**
     * 更新
     */
    public int updateBorrowById(BorrowDo newBorrowDo) {
        int result = borrowDao.updateBorrowById(newBorrowDo);
        if (result < 1) {
            throw new BusinessException("标的更新失败");
        }
        return result;
    }

    /**
     * 新增
     */
    public int addBorrow(BorrowDo newBorrowDo) {
        return borrowDao.addBorrow(newBorrowDo);
    }

    /**
     * 删除
     */
    public int deleteById(Long id) {
        int result = borrowDao.deleteById(id);
        if (result < 1) {
            throw new BusinessException("标的删除失败");
        }
        return result;
    }

    @Override
    public Boolean updateBorrowFullScale(Long borrowId, Integer borrrowStatus, Long adminId) {
        BorrowDo borrowDo = borrowDao.getById(borrowId);
        SettSchemeDo settSchemeDo = settSchemeComponent.getBySchemeId(borrowDo.getPaymentMode().longValue());
        if (settSchemeDo.getSettleType().equals(SettleType.SEPARATE)) {
            return updateBorrowFullScale2(borrowId, borrrowStatus, adminId);
        } else {
            return updateBorrowFullScale1(borrowId, borrrowStatus, adminId);
        }
    }

    /**
     * 放款（还款利息<>回款利息）
     * 
     * @param borrowId
     * @param borrrowStatus
     * @param adminId
     * @author: liuzgmf
     * @date: 2015年4月24日上午10:21:02
     */
    private Boolean updateBorrowFullScale2(Long borrowId, Integer borrrowStatus, Long adminId) {
        // 获取还款明细
        // 修改标的状态，放款时间为满标，当前时间
        updateBorrowFullScale(borrowId);
        // 处理审核通过
        if (borrrowStatus.intValue() == 4) {
            // 新增还款记录
            addRepayment(borrowId);
            // 新增投资回款记录
            addInvestRepayment(borrowId);
            // 修改借款人账户信息，可用金额+借款金额
            changeBorrowUsableSum(borrowId);
            // 修改投资人账户信息，冻结金额减去投资金额
            changeInvestAccount(borrowId);
        }

        // 新增操作日志记录
        AdminDo adminDo = adminDao.getById(adminId);
        operationLogComponent.addOperationLog("t_borrow", adminDo.getUserName(), 2, adminDo.getLastIp(), 0.00,
                "满标复审通过", 2);
        return true;

    }

    /**
     * 新增回款记录
     * 
     * @param borrowId
     * @param repaymentDoList
     * @author: liuzgmf
     * @date: 2015年4月24日上午10:33:16
     */
    private void addInvestRepayment(Long borrowId) {
        BorrowDo borrowDo = borrowDao.getById(borrowId);
        SettSchemeDo settSchemeDo = settSchemeComponent.getBySchemeId(borrowDo.getPaymentMode().longValue());
        SettleCalculator settleCalculator = SettleCalculatorUtils.createSettleCalculator(settSchemeDo.getReceiptWay());
        List<InvestDo> investDoList = investDao.queryByBorrowId(borrowId);
        for (int i = 0; i < investDoList.size(); i++) {
            InvestDo investDo = investDoList.get(i);
            List<SettDetailDo> detailDoList = settleCalculator.calSettDetail(investDo.getInvestAmount(),
                    borrowDo.getDeadline(), borrowDo.getAnnualRate());
            for (SettDetailDo detailDo : detailDoList) {
                InvestRepaymentDo investRepaymentDo = new InvestRepaymentDo();
                investRepaymentDo.setRepayPeriod(detailDo.getPeriod() + "/" + borrowDo.getDeadline());
                RepaymentDo repaymentDo = repaymentComponent.getByBorrowIdAndRepayPeriod(borrowId,
                        investRepaymentDo.getRepayPeriod());
                investRepaymentDo.setRepayId(repaymentDo.getId());
                investRepaymentDo.setRepayDate(detailDo.getRepayDate());
                investRepaymentDo.setRecivedPrincipal(detailDo.getPrincipal());
                investRepaymentDo.setRecivedInterest(detailDo.getInterest());
                investRepaymentDo.setPrincipalBalance(detailDo.getRemainingPrincipal());
                investRepaymentDo.setInterestBalance(0d);
                investRepaymentDo.setInvestId(investDo.getId());
                investRepaymentDo.setOwner(investDo.getInvestor());
                investRepaymentDo.setOwnerlist(investDo.getInvestor() + "");

                investRepaymentDo.setImanageFee(0.00);// 管理费为0
                investRepaymentDo.setImanageFeeRate(0.00);// 管理费率为0
                investRepaymentDo.setIsDebt(1);
                investRepaymentDo.setCirculationForpayStatus(-1);
                investRepaymentDo.setBorrowId(borrowId);

                investRepaymentDo.setHasPrincipal(0.00);// 已收本金
                investRepaymentDo.setHasInterest(0.00);// 已收利息
                investRepaymentDo.setInterestOwner(1l);// 1 默认 VIP会员
                investRepaymentDo.setRecivedFi(0.00);// 应收罚息
                investRepaymentDo.setIsLate(1);// 1 未逾期
                investRepaymentDo.setLateDay(0);// 逾期天数
                investRepaymentDo.setIsWebRepay(1);// 1 非网站垫付
                investRepaymentDo.setRepayStatus(1);// 1: 默认未偿还
                investRepaymentDo.setParentId(0l);

                // 新增投资回款记录
                investRepaymentComponent.addInvestRepayment(investRepaymentDo);

                if (i >= investDoList.size() - 1) {
                    // 修改投资记录的校验本金，校验利息，最大投资ID，调整本金
                    double totalRecivedInterestAmt = getTotalRecivedInterestAmt(detailDoList);
                    updateBorrowFullScale(investDo.getId(), investDo.getRealAmount(), totalRecivedInterestAmt,
                            investRepaymentDo.getId(), investDo.getInvestAmount());
                }
            }
        }

    }

    /**
     * 计算利息总额
     * 
     * @param detailDoList
     * @return
     * @author: liuzgmf
     * @date: 2015年4月24日上午11:01:15
     */
    private double getTotalRecivedInterestAmt(List<SettDetailDo> detailDoList) {
        double totalRecivedInterestAmt = 0d;
        for (SettDetailDo settDetailDo : detailDoList) {
            totalRecivedInterestAmt = CalculateUtils.add(totalRecivedInterestAmt, settDetailDo.getInterest());
        }
        return totalRecivedInterestAmt;
    }

    /**
     * 新增还款记录
     * 
     * @param borrowId
     * @return
     * @author: liuzgmf
     * @date: 2015年4月24日上午10:26:00
     */
    private void addRepayment(Long borrowId) {
        BorrowDo borrowDo = borrowDao.getById(borrowId);
        SettSchemeDo settSchemeDo = settSchemeComponent.getBySchemeId(borrowDo.getPaymentMode().longValue());
        SettleCalculator settleCalculator = SettleCalculatorUtils.createSettleCalculator(settSchemeDo.getRepayWay());
        List<SettDetailDo> detailDoList = settleCalculator.calSettDetail(borrowDo.getBorrowAmount(),
                borrowDo.getDeadline(), settSchemeDo.getDefaultAnnualRate());

        for (SettDetailDo detailDo : detailDoList) {
            RepaymentDo repaymentDo = new RepaymentDo();
            repaymentDo.setRepayPeriod(detailDo.getPeriod() + "/" + borrowDo.getDeadline());
            repaymentDo.setStillPrincipal(detailDo.getPrincipal());
            repaymentDo.setStillInterest(detailDo.getInterest());
            repaymentDo.setBorrowId(borrowId);
            repaymentDo.setPrincipalBalance(detailDo.getRemainingPrincipal());
            repaymentDo.setInterestBalance(0.00);
            repaymentDo.setRepayDate(detailDo.getRepayDate());// 还款日期
            repaymentDo.setConsultFee(detailDo.getConsultFee());// 咨询费
            repaymentDo.setRepayFee(detailDo.getServFee());// 服务费
            repaymentDo.setHasPi(0.00);
            repaymentDo.setHasFi(0.00);
            repaymentDo.setLateFi(0.00);
            repaymentDo.setLateDay(0);
            repaymentDo.setRepayStatus(1);// 1 默认未偿还
            repaymentDo.setIsLate(1);
            repaymentDo.setIsWebRepay(1);
            repaymentDo.setInvestorForpayFi(0.00);// 投资人待收罚息
            repaymentDo.setInvestorHaspayFi(0.00);// 投资人已收罚息
            repaymentDo.setVersion(0);
            repaymentComponent.addRepayment(repaymentDo);
        }
    }

    /**
     * 放款（还款利息=回款利息）
     * 
     * @param borrowId
     * @param borrrowStatus
     * @param adminId
     * @return
     * @author: liuzgmf
     * @date: 2015年4月24日上午10:19:54
     */
    public Boolean updateBorrowFullScale1(Long borrowId, Integer borrrowStatus, Long adminId) {
        // 获取还款明细
        BorrowDo borrowDo = borrowDao.getById(borrowId);
        SettSchemeDo settSchemeDo = settSchemeComponent.getBySchemeId(borrowDo.getPaymentMode().longValue());
        List<SettDetailDo> detailDoList = SettleCalculatorUtils.calSettDetail(borrowDo.getBorrowAmount(),
                borrowDo.getDeadline(), borrowDo.getLoanAnnualRate(), borrowDo.getAnnualRate(), settSchemeDo);
        // 新增预还款记录
        addPreRepayment(borrowId, detailDoList);
        // 修改标的状态，放款时间为满标，当前时间
        updateBorrowFullScale(borrowId);
        // 处理审核通过
        if (borrrowStatus.intValue() == 4) {
            // 新增还款记录
            List<RepaymentDo> repaymentDoList = addRepayment(borrowId, detailDoList);
            // 新增投资回款记录
            addInvestRepayment(borrowId, repaymentDoList);
            // 修改借款人账户信息，可用金额+借款金额
            changeBorrowUsableSum(borrowId);
            // 修改投资人账户信息，冻结金额减去投资金额
            changeInvestAccount(borrowId);
        }

        // 新增操作日志记录
        AdminDo adminDo = adminDao.getById(adminId);
        operationLogComponent.addOperationLog("t_borrow", adminDo.getUserName(), 2, adminDo.getLastIp(), 0.00,
                "满标复审通过", 2);

        return true;
    }

    /**
     * 修改投资人账户信息，冻结金额减去投资金额
     * 
     * @param borrowId
     * @author: liuzgmf
     * @date: 2014年9月25日上午10:51:12
     */
    private void changeInvestAccount(Long borrowId) {
        BorrowDo borrowDo = borrowDao.getById(borrowId);
        List<InvestDo> investDoList = investDao.queryByBorrowId(borrowId);
        for (InvestDo investDo : investDoList) {
            // 修改投资人账户金额，冻结金额-投资金额
            userComponent.updateAmount(0.00, -investDo.getInvestAmount(), investDo.getInvestor());

            // 新增提现交易记录
            FundrecordDo fundrecordDo = new FundrecordDo();
            fundrecordDo.setUserId(investDo.getInvestor());
            fundrecordDo.setRecordTime(new Date());
            fundrecordDo.setOperateType(654);
            fundrecordDo.setFundMode("扣除投标冻结金额");
            fundrecordDo.setHandleSum(investDo.getInvestAmount());
            AccountUserDo localUserDo = userDao.getById(investDo.getInvestor());
            fundrecordDo.setUsableSum(localUserDo.getUsableSum());// 可用金额
            fundrecordDo.setFreezeSum(localUserDo.getFreezeSum());// 冻结金额
            Double dueinSum = investDao.getDueinSum(investDo.getInvestor());
            fundrecordDo.setDueinSum(dueinSum == null ? 0.00 : dueinSum);// 代收金额
            fundrecordDo.setTrader(borrowDo.getPublisher());
            fundrecordDo.setDueoutSum(0.00);
            fundrecordDo.setRemarks("借款[" + borrowDo.getBorrowTitle() + "]审核通过,扣除投标冻结金额[" + investDo.getInvestAmount()
                    + "]元");
            fundrecordDo.setOninvest(0.00);
            fundrecordDo.setPaynumber(investDo.getId() + "");
            fundrecordDo.setPaybank("");
            fundrecordDo.setCost(0.00);
            fundrecordDo.setSpending(investDo.getInvestAmount());
            fundrecordDo.setBorrowId(borrowId);
            fundrecordComponent.addFundrecord(fundrecordDo);
        }

    }

    /**
     * 修改借款人账户信息，可用金额+借款金额
     * 
     * @param borrowId
     * @param borrowAmount
     * @author: liuzgmf
     * @date: 2014年9月25日上午10:22:11
     */
    private void changeBorrowUsableSum(Long borrowId) {
        BorrowDo borrowDo = borrowDao.getById(borrowId);
        // 修改借款人账户可用金额
        userComponent.updateAmount(borrowDo.getBorrowAmount(), 0.00, borrowDo.getPublisher());

        // 新增提现交易记录
        FundrecordDo fundrecordDo = new FundrecordDo();
        fundrecordDo.setUserId(borrowDo.getPublisher());
        fundrecordDo.setRecordTime(new Date());
        fundrecordDo.setOperateType(101);
        fundrecordDo.setFundMode("借款成功");
        fundrecordDo.setHandleSum(borrowDo.getBorrowAmount());
        AccountUserDo localUserDo = userDao.getById(borrowDo.getPublisher());
        fundrecordDo.setUsableSum(localUserDo.getUsableSum());// 可用金额
        fundrecordDo.setFreezeSum(localUserDo.getFreezeSum());// 冻结金额
        Double dueinSum = investDao.getDueinSum(borrowDo.getPublisher());
        fundrecordDo.setDueinSum(dueinSum == null ? 0.00 : dueinSum);// 代收金额
        fundrecordDo.setTrader(-1l);
        fundrecordDo.setDueoutSum(0.00);
        fundrecordDo.setRemarks("借款[" + borrowDo.getBorrowTitle() + "]满标通过,借款成功筹到资金[" + borrowDo.getBorrowAmount()
                + "]元");
        fundrecordDo.setOninvest(0.00);
        fundrecordDo.setPaynumber(borrowId + "");
        fundrecordDo.setPaybank("");
        fundrecordDo.setCost(0.00);
        fundrecordDo.setIncome(borrowDo.getBorrowAmount());
        fundrecordDo.setBorrowId(borrowId);
        fundrecordComponent.addFundrecord(fundrecordDo);
    }

    /**
     * 新增投资回款记录
     * 
     * @param borrowId
     * @param repaymentDoList
     * @author: liuzgmf
     * @date: 2014年9月24日下午3:48:13
     */
    private void addInvestRepayment(Long borrowId, List<RepaymentDo> repaymentDoList) {
        BorrowDo borrowDo = borrowDao.getById(borrowId);
        Map<Long, Double> recivedPrincipalMap = new HashMap<Long, Double>();
        Map<Long, Double> recivedInterestMap = new HashMap<Long, Double>();
        List<InvestDo> investDoList = investDao.queryByBorrowId(borrowId);
        for (int i = 0; i < repaymentDoList.size(); i++) {
            RepaymentDo repaymentDo = repaymentDoList.get(i);
            Double remainPrincipal = repaymentDo.getStillPrincipal();// 剩余本金
            Double remainInterest = repaymentDo.getStillInterest();// 剩余利息
            for (int j = 0; j < investDoList.size(); j++) {
                InvestDo investDo = investDoList.get(j);
                Double investRate = CalculateUtils.div(investDo.getInvestAmount(), borrowDo.getBorrowAmount());
                InvestRepaymentDo investRepaymentDo = new InvestRepaymentDo();
                investRepaymentDo.setRepayId(repaymentDo.getId());
                investRepaymentDo.setRepayPeriod(repaymentDo.getRepayPeriod());
                investRepaymentDo.setRepayDate(repaymentDo.getRepayDate());

                // 汇款本金金额
                Double recivedPrincipal = 0.00;
                if (i >= repaymentDoList.size() - 1) {// 最后一期为投资金额减去累计已回款本金
                    Double totalRecivedPrincipal = recivedPrincipalMap.get(investDo.getId());
                    totalRecivedPrincipal = totalRecivedPrincipal == null ? 0.00 : totalRecivedPrincipal;
                    recivedPrincipal = CalculateUtils.sub(investDo.getInvestAmount(), totalRecivedPrincipal);
                } else {
                    // 如果是最后一个投资人，需要用还款本金-其他投资回款本金
                    if (j >= investDoList.size() - 1) {
                        recivedPrincipal = remainPrincipal;
                    } else {
                        recivedPrincipal = CalculateUtils.round(
                                CalculateUtils.mul(repaymentDo.getStillPrincipal(), investRate), 2);
                        remainPrincipal = CalculateUtils.sub(remainPrincipal, recivedPrincipal);
                    }
                }
                investRepaymentDo.setRecivedPrincipal(recivedPrincipal);

                // 回款利息金额
                Double recivedInterest = 0.00;// 每期回款利息
                if (j >= investDoList.size() - 1) {
                    recivedInterest = remainInterest;
                } else {
                    recivedInterest = CalculateUtils.round(
                            CalculateUtils.mul(repaymentDo.getStillInterest(), investRate), 2, BigDecimal.ROUND_DOWN);
                    remainInterest = CalculateUtils.sub(remainInterest, recivedInterest);
                }
                investRepaymentDo.setRecivedInterest(recivedInterest);

                // 剩余本金余额（投资金额-累计已回款金额）
                Double totalRecivedPrincipal = recivedPrincipalMap.get(investDo.getId());
                totalRecivedPrincipal = totalRecivedPrincipal == null ? 0.00 : totalRecivedPrincipal;
                investRepaymentDo.setPrincipalBalance(CalculateUtils.sub(investDo.getInvestAmount(),
                        totalRecivedPrincipal));
                // 剩余利息余额，现利息余额为0
                Double interestBalance = CalculateUtils.round(
                        CalculateUtils.mul(repaymentDo.getInterestBalance(), investRate), 2);
                investRepaymentDo.setInterestBalance(interestBalance);
                investRepaymentDo.setInvestId(investDo.getId());
                investRepaymentDo.setOwner(investDo.getInvestor());
                investRepaymentDo.setOwnerlist(investDo.getInvestor() + "");

                investRepaymentDo.setImanageFee(0.00);// 管理费为0
                investRepaymentDo.setImanageFeeRate(0.00);// 管理费率为0
                investRepaymentDo.setIsDebt(1);
                investRepaymentDo.setCirculationForpayStatus(-1);
                investRepaymentDo.setBorrowId(borrowId);

                investRepaymentDo.setHasPrincipal(0.00);// 已收本金
                investRepaymentDo.setHasInterest(0.00);// 已收利息
                investRepaymentDo.setInterestOwner(1l);// 1 默认 VIP会员
                investRepaymentDo.setRecivedFi(0.00);// 应收罚息
                investRepaymentDo.setIsLate(1);// 1 未逾期
                investRepaymentDo.setLateDay(0);// 逾期天数
                investRepaymentDo.setIsWebRepay(1);// 1 非网站垫付
                investRepaymentDo.setRepayStatus(1);// 1: 默认未偿还
                investRepaymentDo.setParentId(0l);

                // 累计已还本金，用于最后一期已还本金和剩余本金的计算
                recivedPrincipalMap.put(investDo.getId(), CalculateUtils.add(totalRecivedPrincipal, recivedPrincipal));
                // 累计已收利息
                Double totalRecivedInterest = recivedInterestMap.get(investDo.getId());
                totalRecivedInterest = totalRecivedInterest == null ? 0.00 : totalRecivedInterest;
                recivedInterestMap.put(investDo.getId(), CalculateUtils.add(totalRecivedInterest, recivedInterest));
                // 新增投资回款记录
                investRepaymentComponent.addInvestRepayment(investRepaymentDo);

                if (i >= repaymentDoList.size() - 1) {
                    // 修改投资记录的校验本金，校验利息，最大投资ID，调整本金
                    updateBorrowFullScale(investDo.getId(), investDo.getRealAmount(),
                            recivedInterestMap.get(investDo.getId()), investRepaymentDo.getId(),
                            investDo.getInvestAmount());
                }
            }
        }
    }

    /**
     * 修改投资记录的校验本金，校验利息，最大投资ID，调整本金
     * 
     * @param investId
     * @param realAmount
     * @param totalRecivedInterest
     * @param investRepaymentId
     * @param totalRecivedPrincipal
     * @author: liuzgmf
     * @date: 2014年9月24日下午5:37:15
     */
    private void updateBorrowFullScale(Long investId, Double realAmount, Double totalRecivedInterest,
            Long investRepaymentId, Double totalRecivedPrincipal) {
        InvestDo udpateInvestDo = new InvestDo();
        udpateInvestDo.setId(investId);
        udpateInvestDo.setCheckPrincipal(realAmount);// 校验本金
        udpateInvestDo.setCheckInterest(totalRecivedInterest);// 校验利息
        udpateInvestDo.setRecivedPrincipal(realAmount);// 已收本金
        udpateInvestDo.setRecievedInterest(totalRecivedInterest);// 已收利息
        udpateInvestDo.setMaxInvestId(investRepaymentId);// 最大投资id
        udpateInvestDo.setAdjustPrincipal(totalRecivedPrincipal);// 调整本金
        investComponent.updateInvest(udpateInvestDo);
    }

    /**
     * 新增还款记录
     * 
     * @param preRepaymentDoList
     * @author: liuzgmf
     * @throws ParseException
     * @date: 2014年9月24日下午3:09:39
     */
    private List<RepaymentDo> addRepayment(Long borrowId, List<SettDetailDo> detailDoList) {
        List<RepaymentDo> repaymentDoList = new ArrayList<RepaymentDo>();
        for (SettDetailDo detailDo : detailDoList) {
            RepaymentDo repaymentDo = new RepaymentDo();
            repaymentDo.setRepayPeriod(detailDo.getPeriod() + "/" + detailDoList.size());
            repaymentDo.setStillPrincipal(detailDo.getPrincipal());
            repaymentDo.setStillInterest(detailDo.getInterest());
            repaymentDo.setBorrowId(borrowId);
            repaymentDo.setPrincipalBalance(detailDo.getRemainingPrincipal());
            repaymentDo.setInterestBalance(0.00);
            repaymentDo.setRepayDate(detailDo.getRepayDate());// 还款日期
            repaymentDo.setConsultFee(detailDo.getConsultFee());// 咨询费
            repaymentDo.setRepayFee(detailDo.getServFee());// 服务费
            repaymentDo.setHasPi(0.00);
            repaymentDo.setHasFi(0.00);
            repaymentDo.setLateFi(0.00);
            repaymentDo.setLateDay(0);
            repaymentDo.setRepayStatus(1);// 1 默认未偿还
            repaymentDo.setIsLate(1);
            repaymentDo.setIsWebRepay(1);
            repaymentDo.setInvestorForpayFi(0.00);// 投资人待收罚息
            repaymentDo.setInvestorHaspayFi(0.00);// 投资人已收罚息
            repaymentDo.setVersion(0);
            repaymentComponent.addRepayment(repaymentDo);
            repaymentDoList.add(repaymentDo);
        }
        return repaymentDoList;
    }

    /**
     * 变更借款标的记录，包括版本，状态，审核时间
     * 
     * @param borrowId
     * @param borrrowStatus
     * @param version
     * @author: liuzgmf
     * @date: 2014年9月24日下午3:01:50
     */
    private void updateBorrowFullScale(Long borrowId) {
        int count = borrowDao.updateBorrowFullScale(borrowId);
        if (count < 1) {
            throw new BusinessException("标的更新失败");
        }
    }

    /**
     * 新增预还款记录
     * 
     * @param repyaDetailList
     * @author: liuzgmf
     * @date: 2014年9月24日上午10:41:02
     */
    private List<PreRepaymentDo> addPreRepayment(Long borrowId, List<SettDetailDo> detailDoList) {
        BorrowDo borrowDo = borrowDao.getById(borrowId);
        List<PreRepaymentDo> preRepaymentDoList = new ArrayList<PreRepaymentDo>();
        for (SettDetailDo detailDo : detailDoList) {
            PreRepaymentDo preRepaymentDo = new PreRepaymentDo();
            preRepaymentDo.setRepayPeriod(detailDo.getPeriod() + "/" + detailDoList.size());
            preRepaymentDo.setRepayDate(DateFormatUtils.format(detailDo.getRepayDate(), "yyyy-MM-dd"));
            preRepaymentDo.setStillPrincipal(detailDo.getPrincipal());
            preRepaymentDo.setStillInterest(detailDo.getInterest());
            preRepaymentDo.setPrincipalBalance(detailDo.getRemainingPrincipal());

            preRepaymentDo.setInterestBalance(0.00);
            preRepaymentDo.setConsultFee(detailDo.getConsultFee());
            preRepaymentDo.setMrate(CalculateUtils.div(borrowDo.getAnnualRate(), 12));
            Double totalSum = CalculateUtils.add(detailDo.getPrincipal(), detailDo.getInterest());
            preRepaymentDo.setTotalSum(totalSum);
            preRepaymentDo.setTotalAmount(CalculateUtils.add(totalSum, detailDo.getConsultFee()));

            preRepaymentDo.setBorrowId(borrowId);
            preRepaymentDo.setSort(detailDo.getPeriod());
            preRepaymentDo.setRepayFee(0.00);
            preRepaymentDoList.add(preRepaymentDo);

            preRepaymentComponent.addPreRepayment(preRepaymentDo);
        }
        return preRepaymentDoList;
    }

    /*
     * (no-Javadoc) <p>Title: updateBorrowStatusAndHasDeadlineById</p>
     * <p>Description: </p>
     * 
     * @param borrowId
     * 
     * @return
     * 
     * @see com.hehenian.biz.component.trade.IBorrowComponent#
     * updateBorrowStatusAndHasDeadlineById(long)
     */
    @Override
    public int updateBorrowStatusAndHasDeadlineById(long borrowId) {
        return borrowDao.updateBorrowStatusAndHasDeadlineById(borrowId);
    }

    /*
     * (no-Javadoc) <p>Title: updateBorrowStatus</p> <p>Description: </p>
     * 
     * @param borrowId
     * 
     * @return
     * 
     * @see
     * com.hehenian.biz.component.trade.IBorrowComponent#updateBorrowStatus(
     * long)
     */
    @Override
    public int updateBorrowStatus(long borrowId) {
        return borrowDao.updateBorrowStatus(borrowId);
    }

    /*
     * (no-Javadoc) <p>Title: updateBorrowStatusByPreRepay</p> <p>Description:
     * </p>
     * 
     * @param borrowId
     * 
     * @return
     * 
     * @see com.hehenian.biz.component.trade.IBorrowComponent#
     * updateBorrowStatusByPreRepay(long)
     */
    @Override
    public int updateBorrowStatusByPreRepay(long borrowId) {
        // TODO Auto-generated method stub
        return borrowDao.updateBorrowStatusByPreRepay(borrowId);
    }

    /**
     * 修改标的状态
     * 
     * @param borrowDo
     * @return
     */
    @Override
    public int updateStatus(BorrowDo borrowDo) {
        int result = borrowDao.updateStatus(borrowDo);
        if (result <= 0) {
            throw new BusinessException("更新标的状态失败");
        }
        return result;
    }

    @Override
    public int updateBorrowInvest(BorrowDo borrowDo) {
        int result = borrowDao.updateBorrowInvest(borrowDo);
        return result;
    }

    @Override
    public List<BorrowDo> queryBorrows(Map<String, Object> searchItems) {
        return borrowDao.queryBorrows(searchItems);
    }

    @Override
    public long getBorrowQty(Map<String, Object> searchItems) {
        return borrowDao.getBorrowQty(searchItems);
    }

    @Override
    public BorrowDo getByIdNo(String idNo) {
        return borrowDao.getByIdNo(idNo);
    }

    @Override
    public Map<String, Object> queryBorrowDetails(Long borrowId) {
        return borrowDao.queryBorrowDetails(borrowId);
    }
    
    @Override
    public List<Map<String,Object>> queryLoanBorrows(Map<String, Object> searchItems) {
        return borrowDao.queryLoanBorrows(searchItems);
    }
    
    @Override
    public List<Map<String,Object>> queryloanBorrowUser(Map<String, Object> searchItems) {
        return borrowDao.queryloanBorrowUser(searchItems);
    }

	@Override
	public List<Map<String, Object>> queryRepayment(Map<String, Object> searchItems) {
		return borrowDao.queryRepayment(searchItems);
	}
}
