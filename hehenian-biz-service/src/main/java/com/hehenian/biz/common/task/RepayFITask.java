/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.common.task
 * @Title: RepayFITask.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年10月15日 上午9:14:21
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.task;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hehenian.biz.common.system.dataobject.FeeRuleDo;
import com.hehenian.biz.common.system.dataobject.FeeRuleDo.RuleType;
import com.hehenian.biz.common.trade.dataobject.BorrowDo;
import com.hehenian.biz.common.trade.dataobject.RepaymentDo;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.common.util.DateUtil;
import com.hehenian.biz.component.system.IFeeRuleComponent;
import com.hehenian.biz.component.trade.IBorrowComponent;
import com.hehenian.biz.component.trade.IRepaymentComponent;

/**
 * 还款罚息定时任务
 * 
 * @author: zhangyunhmf
 * @date 2014年10月15日 上午9:14:21
 */
@Component("repayFITask")
public class RepayFITask extends DefaultTask {

    @Autowired
    private IRepaymentComponent repaymentComponent;
    @Autowired
    private IBorrowComponent    borrowComponent;
    @Autowired
    private IFeeRuleComponent   feeRuleComponent;

    private String              jobName = "还款罚息定时任务 ";

    /*
     * (no-Javadoc) <p>Title: getJobName</p> <p>Description: </p>
     * 
     * @return
     * 
     * @see com.hehenian.biz.common.task.DefaultTask#getJobName()
     */
    @Override
    protected String getJobName() {
        return jobName;
    }

    /*
     * (no-Javadoc) <p>Title: doJob</p> <p>Description: 借款人罚息只计算首次逾期</p>
     * 
     * @see com.hehenian.biz.common.task.DefaultTask#doJob()
     */
    @Override
    protected void doJob() {

        List<RepaymentDo> overDueRepaymentList = null;
        Date currentDate = new Date();
        try {
            overDueRepaymentList = repaymentComponent.selectOverDueRepayList(currentDate);
            for (RepaymentDo repay : overDueRepaymentList) {

                BorrowDo borrow = borrowComponent.getById(repay.getBorrowId());
                //double feeRate = FeeUtil.parseBorrowFeelog(borrow.getFeelog());

                double feeRate = 0.001d;
                FeeRuleDo feeRuleDo = feeRuleComponent.getBySchemeIdAndRuleType(borrow.getPaymentMode().longValue(),
                        RuleType.OVERDUE_FEE);
                if (null != feeRuleDo) {
                    Double confFeeRate = feeRuleDo.getGatherRate();
                    if (null != confFeeRate) {
                        feeRate = confFeeRate.doubleValue() / 100;
                    }
                }


                // 逾期日期
                long lateDay = DateUtil.diffDays(repay.getRepayDate(), currentDate);
                // 一天的罚息
                double oneDayFee = CalculateUtils.mul(repay.getPrincipalBalance(), feeRate);
                // 逾期罚息
                double lateFee = CalculateUtils.round(CalculateUtils.mul(oneDayFee, lateDay),2);
                RepaymentDo newRepaymentDo = new RepaymentDo();
                newRepaymentDo.setIsLate(2);
                newRepaymentDo.setLateDay((int) lateDay);
                newRepaymentDo.setLateFi(lateFee);
                newRepaymentDo.setId(repay.getId());
                // 更新罚息信息
                repaymentComponent.updateRepaymentById(newRepaymentDo);
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
        }
    }
}
