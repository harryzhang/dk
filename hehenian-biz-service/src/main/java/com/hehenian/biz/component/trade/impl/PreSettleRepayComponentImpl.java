/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.trade.impl
 * @Title: PreRepaymenet.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年9月28日 上午8:41:20
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.trade.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.base.constant.Constants;
import com.hehenian.biz.common.settle.SettleCalculatorUtils;
import com.hehenian.biz.common.system.dataobject.FeeRuleDo;
import com.hehenian.biz.common.system.dataobject.FeeRuleDo.RuleType;
import com.hehenian.biz.common.trade.dataobject.InvestRepaymentDo;
import com.hehenian.biz.common.trade.dataobject.RepaymentContext;
import com.hehenian.biz.common.trade.dataobject.RepaymentDo;
import com.hehenian.biz.common.trade.dataobject.RepaymentFeeDo;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.component.system.IFeeRuleComponent;

/**
 * 
 * 提前结清： 借款人需要支付的费用 ： 当前期大于3期： 1. 咨询方的咨询费 = 最后未还款期数 至 当前期数之间的咨询费之和 2. 咨询方的手续费 =
 * 最后未还款期数 至 当前期数之间的手续费之和 3. 支付给代偿方的罚金= 最后未还款期数 至 当前期数之间的罚金之和 3. 提前结清手续费 =
 * 当前期数的剩余本金*提前结清费率 4. 支付的利息 = 最后未还款期数 至 当前期数之间的应还利息之和 5. 支付的本金 =
 * 所有未还款期数的应还款本金之和
 * 
 * 当前期数未满三期： 1. 咨询方的咨询费 = 最后未还款期数 至 第三期数之间的咨询费之和 2. 咨询方的手续费 = 最后未还款期数 至
 * 第三期数之间的手续费之和 3. 支付给代偿方的罚金= 最后未还款期数 至 第三期数之间的罚金之和 3. 提前结清手续费 =
 * 当前期数的剩余本金*提前结清费率 4. 支付的利息 = 最后未还款期数 至 第三期数之间的应还利息之和 5. 支付的本金 =
 * 所有未还款期数的应还款本金之和
 * 
 * 投资人的收益： 当前期大于3期： 1. 所有未偿还本金+ 最后未还款期数 至 当前期数之间的罚金之和 + 最后未还款期数 至 当前期数之间的应还利息之和
 * 当前期数未满三期： 1. 所有未偿还本金+ 最后未还款期数 至 第三期数之间的罚金之和 + 最后未还款期数 至 第三期数之间的应还利息之和 代偿方收益 ：
 * 1. 代偿本金 2. 代偿利息 3. 借款人罚金 咨询方收益： 咨询费
 * 
 * @author: zhangyunhmf
 * @date 2014年9月28日 上午8:41:20
 */
@Component("preSettleRepayComponentImpl")
public class PreSettleRepayComponentImpl extends DefaultAbstractRepayComponent {

    private static final String notify_template = "sms_repay_presettle.ftl";

    @Autowired
    private IFeeRuleComponent   feeRuleComponent;

    /**
     * 构建还款列表
     * 
     * @author: zhangyunhmf
     * @date: 2014年10月8日下午1:58:05
     */
    @Override
    public int buildInvestList(RepaymentContext rc) {
        RepaymentDo repayDo = rc.getRepaymentDo();
        long borrowId = rc.getBorrowId();
        // 查询所有投资人的投资信息
        List<InvestRepaymentDo> investList = this.investRepaymentComponent.selectPreRepayByBorrowId(repayDo.getId(),
                borrowId);

        investList = removeInvalidateRecord(investList);

        if (null == investList || investList.size() < 1) {
            return 0;
        }

        rc.setInvestList(this.convertInvestRepaymentWrap(investList));
        return investList.size();
    }

    /**
     * 移除金额为0的记录
     * 
     * @param investList
     */
    private List<InvestRepaymentDo> removeInvalidateRecord(List<InvestRepaymentDo> investList) {

        if (null == investList) {
            return null;
        }

        List<InvestRepaymentDo> tmpList = new ArrayList<InvestRepaymentDo>();

        for (int i = 0, size = investList.size(); i < size; i++) {
            InvestRepaymentDo investRepay = investList.get(i);
            Double recivedPrincipal = investRepay.getRecivedPrincipal();
            Double recivedInterest = investRepay.getRecivedInterest();
            boolean isNotPrincipalZero = false;
            boolean isNotInterestZero = false;
            if (recivedPrincipal != null && recivedPrincipal > 0) {
                isNotPrincipalZero = true;
            }
            if (recivedInterest != null && recivedInterest > 0) {
                isNotInterestZero = true;
            }
            if (isNotPrincipalZero || isNotInterestZero) {
                tmpList.add(investRepay);
            }
        }

        return tmpList;
    }

    /*
     * (no-Javadoc) <p>Title: updateBorrow </p> <p>Description: 把标的状态更新为提前还款状态 =
     * 10 </p>
     * 
     * @param borrowId
     * 
     * @see
     * com.hehenian.biz.component.trade.impl.DefaultRepayment#updateBorrow(long)
     */
    @Override
    public void updateBorrow(long borrowId) {
        this.borrowComponent.updateBorrowStatusByPreRepay(borrowId);

    }

    /*
     * (no-Javadoc) <p>Title: buildRepaymentFee</p> <p>Description: 提前结清手续费</p>
     * 
     * @see com.hehenian.biz.service.trade.impl.DefaultAbstractRepayService#
     * buildRepaymentFee()
     */
    @Override
    public void buildRepaymentFee(RepaymentDo repayDo, Integer paymentMode) {
        super.buildRepaymentFee(repayDo, paymentMode);
        
        //2015-1-12 修改，修改成从配置读
        /*
        double feeRate = Constants.PRE_REPAY_RATE;
        if (paymentMode.intValue() == 3) {
            feeRate = Constants.PRE_REPAY_RATE2;
        }
        if (paymentMode.intValue() == 2) {
            feeRate = Constants.PRE_REPAY_RATE3;
        }
        double preFee = repayDo.buildPreSettleFee(feeRate);
        */

        double preFee = 0;
        FeeRuleDo feeRuleDo = feeRuleComponent.getBySchemeIdAndRuleType(paymentMode.longValue(), RuleType.SETTLE_FEE);

        if (null == feeRuleDo) {
            this.logger
                    .warn("feeRuleDo is null========================================================================paymentMode"
                            + paymentMode + " RuleType.SETTLE_FEE" + RuleType.SETTLE_FEE);
            preFee = repayDo.buildPreSettleFee(0.03d);
        } else {
            Double principalBlance = repayDo.getPrincipalBalance();

            if (null != principalBlance) {
                preFee = CalculateUtils.round(SettleCalculatorUtils.calSettleFee(principalBlance, null, feeRuleDo), 2);
            }
        }
       // end 2015-1-12 修改，修改成从配置读
        
        if (CalculateUtils.le(preFee, 0d)) {
            return;
        }

        RepaymentFeeDo tmpFee = repayDo.getRepaymentFee(Constants.FEE_CODE_PRE);
        if (tmpFee == null) {
            RepaymentFeeDo rf = new RepaymentFeeDo(null, Constants.FEE_CODE_PRE, preFee, 0d);
            repayDo.addRepaymentFee(rf);
        } else {
            tmpFee.setStillAmount(preFee);
        }
    }

    /*
     * (no-Javadoc) <p>Title: updateRepaymentStatus </p> <p>Description:
     * 提前还款，根据标的ID更新所有的还款记录的状态 =10 提前还款 </p>
     * 
     * @see
     * com.hehenian.biz.component.trade.impl.DefaultRepayment#updateRepaymentStatus
     * ()
     */
    @Override
    public void updateRepaymentStatus(RepaymentContext rc) {
        RepaymentDo repaymentDo = rc.getRepaymentDo();
        long borrowId = rc.getBorrowId();
        this.repaymentComponent.updateRepaymentStatusByPreRepay(repaymentDo.getId(), borrowId);
    }

    /*
     * 当前期大于3期： 1. 咨询方的咨询费 = 最后未还款期数 至 当前期数之间的咨询费之和 2. 咨询方的手续费 = 最后未还款期数 至
     * 当前期数之间的手续费之和 3. 支付给代偿方的罚金= 最后未还款期数 至 当前期数之间的罚金之和 3. 提前结清手续费 =
     * 当前期数的剩余本金*提前结清费率 4. 支付的利息 = 最后未还款期数 至 当前期数之间的应还利息之和 5. 支付的本金 =
     * 所有未还款期数的应还款本金之和
     * 
     * 当前期数未满三期： 1. 咨询方的咨询费 = 最后未还款期数 至 第三期数之间的咨询费之和 2. 咨询方的手续费 = 最后未还款期数 至
     * 第三期数之间的手续费之和 3. 支付给代偿方的罚金= 最后未还款期数 至 第三期数之间的罚金之和 3. 提前结清手续费 =
     * 当前期数的剩余本金*提前结清费率 4. 支付的利息 = 最后未还款期数 至 第三期数之间的应还利息之和 5. 支付的本金 =
     * 所有未还款期数的应还款本金之和 <p>Title: getRepayment</p> <p>Description: 提前还款时参数
     * repayId 可能不时当期的repayId ，所以根据borrowId获取repaymentDo </p>
     * 
     * @return
     * 
     * @see
     * com.hehenian.biz.component.trade.impl.DefaultRepayment#getRepayment(long)
     */
    @Override
    public RepaymentDo buildRepayment(RepaymentContext rc) {
        long borrowId = rc.getBorrowId();
        // 获取当期ID
        RepaymentDo currentRepaymentDo = this.repaymentComponent.selectCurrentPeriod(borrowId);
		
		/*
		
        long currentRepayId = currentRepaymentDo.getId();
        RepaymentDo thirdRepaymentDo = this.repaymentComponent.selectThirdPeriod(borrowId);
        // 不满三期，取最后一期
        if (null == thirdRepaymentDo) {
            thirdRepaymentDo = this.repaymentComponent.selectLastPeriod(borrowId);
        }

        if (currentRepayId < thirdRepaymentDo.getId()) {
            currentRepaymentDo = thirdRepaymentDo;
        }
		
		*/
		
        RepaymentDo repayDo = this.repaymentComponent.selectPreRepayTotalAmountByBorrowId(borrowId,
                currentRepaymentDo.getId());
        if (null == repayDo) {
            return null;
        }
        repayDo.setPrincipalBalance(currentRepaymentDo.getPrincipalBalance()); // 当期剩余本金，计算提前结清手续费用
        repayDo.setRepayStatus(1);// 设置成未还款
        repayDo.setIsWebRepay(1);
        repayDo.setVersion(currentRepaymentDo.getVersion());
        repayDo.setId(currentRepaymentDo.getId());
        repayDo.setRepayPeriod(currentRepaymentDo.getRepayPeriod());
        repayDo.setHasFi(0d);
        repayDo.setHasPi(0d);
        rc.setRepaymentDo(repayDo);
        rc.setRepaymentId(repayDo.getId());

        return repayDo;
    }

    /**
     * 更新投资状态
     * 
     * @author: zhangyunhmf
     * @date: 2014年10月23日上午11:35:26
     */
    @Override
    public void updateInvestStatus(long borrowId) {
        investComponent.updateRepayStatusForPreSettle(borrowId);
    }

    /**
     * 
     * 更新回款表 , 如果是代偿操作isWebPay = 2 ,并且要加一条新的还款记录表示代偿
     * 
     * @param investDo
     * @author: zhangyunhmf
     * @date: 2014年10月23日上午10:53:41
     */
    @Override
    public void updateInvestRepayment(RepaymentContext rc, InvestRepaymentDo investDo) {
        InvestRepaymentDo newInvestRepaymentDo = new InvestRepaymentDo();
        newInvestRepaymentDo.setInterestOwner(investDo.getOwner());
        // 如果是代偿操作这里应该设置成2
        newInvestRepaymentDo.setIsWebRepay(investDo.getIsWebRepay());
        newInvestRepaymentDo.setRecivedFi(investDo.getRecivedFi());
        newInvestRepaymentDo.setOwner(investDo.getOwner());
        newInvestRepaymentDo.setInvestId(investDo.getInvestId());

        newInvestRepaymentDo.setRepayId(rc.getRepaymentId());
        newInvestRepaymentDo.setId(investDo.getId());
        newInvestRepaymentDo.setRecivedInterest(investDo.getRecivedInterest());
        newInvestRepaymentDo.setRecivedPrincipal(investDo.getRecivedPrincipal());
        investRepaymentComponent.updateRepayStatusByPreRepay(newInvestRepaymentDo);
    }
}
