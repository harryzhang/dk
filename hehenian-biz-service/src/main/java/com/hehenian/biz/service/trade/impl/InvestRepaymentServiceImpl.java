package com.hehenian.biz.service.trade.impl;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.trade.IInvestRepaymentService;
import com.hehenian.biz.common.trade.dataobject.InvestRepaymentDo;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.common.util.DateUtil;
import com.hehenian.biz.component.account.IUserComponent;
import com.hehenian.biz.component.trade.IFundrecordComponent;
import com.hehenian.biz.component.trade.IInvestRepaymentComponent;
import com.ibm.icu.util.Calendar;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

@Service("investRepaymentService")
public class InvestRepaymentServiceImpl implements IInvestRepaymentService {
    private final Logger              logger = Logger.getLogger(this.getClass());
    @Autowired
    private IInvestRepaymentComponent investRepaymentComponent;
    @Autowired
    private IFundrecordComponent      fundrecordComponent;
    @Autowired
    private IUserComponent            userComponent;

    /**
     * 根据ID 查询
     * 
     * @parameter id
     */
    public InvestRepaymentDo getById(Long id) {
        return investRepaymentComponent.getById(id);
    }

    /**
     * 根据条件查询列表
     */
    public List<InvestRepaymentDo> selectInvestRepayment(Map<String, Object> parameterMap) {
        return investRepaymentComponent.selectInvestRepayment(parameterMap);
    }

    /**
     * 更新
     */
    public int updateInvestRepaymentById(InvestRepaymentDo newInvestRepaymentDo) {
        return investRepaymentComponent.updateInvestRepaymentById(newInvestRepaymentDo);
    }

    /**
     * 新增
     */
    public int addInvestRepayment(InvestRepaymentDo newInvestRepaymentDo) {
        return investRepaymentComponent.addInvestRepayment(newInvestRepaymentDo);
    }

    /**
     * 删除
     */
    public int deleteById(int id) {
        return investRepaymentComponent.deleteById(id);
    }

    @Override
    public Double getDailyIncome(Long userId) {
        Date yesterday = DateUtils.truncate(DateUtils.addDays(new Date(), -1), Calendar.DATE);// 昨天
        Date endDate = DateUtils.truncate(DateUtils.addMonths(yesterday, 1), Calendar.DATE);
        List<InvestRepaymentDo> repaymentDoList = investRepaymentComponent.queryByUserIdAndRepayDate(userId, yesterday,
                endDate);
        if (repaymentDoList == null || repaymentDoList.size() == 0) {
            return 0.00;
        }

        Double totalDailyIncome = 0.00;
        List<Long> investIdList = new ArrayList<Long>();
        for (InvestRepaymentDo repaymentDo : repaymentDoList) {
            if (investIdList.contains(repaymentDo.getInvestId())) {
                continue;
            }
            investIdList.add(repaymentDo.getInvestId());
            Date startDate = DateUtils.addMonths(repaymentDo.getRepayDate(), -1);
            long days = DateUtil.diffDays(startDate, repaymentDo.getRepayDate());
            Double dailyIncome = 0.00;
            if (repaymentDo.getRealRepayDate() != null
                    && repaymentDo.getRealRepayDate().before(repaymentDo.getRepayDate())
                    && repaymentDo.getRealRepayDate().equals(yesterday)) {// 提前还款时，计算当日收益
                long prepaymentDays = DateUtil.diffDays(repaymentDo.getRealRepayDate(), repaymentDo.getRepayDate());
                dailyIncome = CalculateUtils.mul(CalculateUtils.div(repaymentDo.getRecivedInterest(), days),
                        prepaymentDays);
            } else {
                dailyIncome = CalculateUtils.div(repaymentDo.getRecivedInterest(), days);
            }
            totalDailyIncome = CalculateUtils.add(totalDailyIncome, dailyIncome);
        }

        // 奖励金额
        Double incentiveAmt = fundrecordComponent.getDailyIncentiveAmount(userId, yesterday);
        if (incentiveAmt != null && CalculateUtils.gt(incentiveAmt, 0.00)) {
            totalDailyIncome = CalculateUtils.add(totalDailyIncome, incentiveAmt);
        }

        // 最后返回结果两位小数四舍五入
        return CalculateUtils.round(totalDailyIncome, 2);
    }

    @Override
    public Double getAssetValue(Long userId) {
        AccountUserDo userDo = userComponent.getById(userId);
        if (userDo == null) {
            return 0.00;
        }
        Double recivedPrincipal = getRecivedPrincipal(userId);
        Double assetValue = CalculateUtils.add(CalculateUtils.add(userDo.getUsableSum(), userDo.getFreezeSum()),
                recivedPrincipal);
        Double totalIncome = getTotalIncome(userId);
        return CalculateUtils.round(CalculateUtils.add(assetValue, totalIncome), 2);
    }

    @Override
    public Double getRecivedPrincipal(Long userId) {
        Double recivedPrincipal = investRepaymentComponent.getRecivedPrincipal(userId);
        recivedPrincipal = (recivedPrincipal == null ? 0.00 : recivedPrincipal);
        return CalculateUtils.round(recivedPrincipal, 2);
    }

    private Double getTotalIncome(Long userId) {
        // 获取待收收益（只包括上一期到今天收益）
        List<InvestRepaymentDo> repaymentDoList = investRepaymentComponent.queryNoRepayRecordsRecently(userId);
        if (repaymentDoList == null || repaymentDoList.size() == 0) {
            return 0.00;
        }
        Double totalRecivedInterest = 0.00;
        List<Long> investIdList = new ArrayList<Long>();
        for (InvestRepaymentDo repaymentDo : repaymentDoList) {
            if (investIdList.contains(repaymentDo.getInvestId())) {
                continue;
            }
            investIdList.add(repaymentDo.getInvestId());
            Date startDate = DateUtils.addMonths(repaymentDo.getRepayDate(), -1);
            long days1 = DateUtil.diffDays(startDate, repaymentDo.getRepayDate());// 两期相差天数
            long days2 = DateUtil.diffDays(startDate, new Date());// 上一期到今天的天数
            if (days2 <= 0) {
                continue;
            }
            // 收益 = 回款利息 / 两期相差天数 * 上一期到今天的天数
            Double income = CalculateUtils.mul(CalculateUtils.div(repaymentDo.getRecivedInterest(), days1), days2);
            totalRecivedInterest = CalculateUtils.add(totalRecivedInterest, income);
        }

        // 最后返回结果两位小数四舍五入
        return CalculateUtils.round(totalRecivedInterest, 2);
    }
}
