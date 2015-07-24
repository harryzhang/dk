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

import com.hehenian.biz.common.trade.dataobject.InvestRepaymentDo;
import com.hehenian.biz.common.trade.dataobject.RepaymentDo;
import com.hehenian.biz.common.util.CalculateUtils;
import com.hehenian.biz.common.util.DateUtil;
import com.hehenian.biz.component.trade.IBorrowComponent;
import com.hehenian.biz.component.trade.IInvestRepaymentComponent;


/** 
 * 投资回款逾期罚息定时任务 
 * @author: zhangyunhmf
 * @date 2014年10月15日 上午9:14:21
 */
@Component("investRepayFITask")
public class InvestRepayFITask extends DefaultTask {
    
    
    @Autowired
    private IInvestRepaymentComponent investRepaymentComponent;
    @Autowired
    private IBorrowComponent borrowComponent;
    
    private String jobName = "投资回款逾期罚息定时任务";

    /* (no-Javadoc) 
     * <p>Title: getJobName</p> 
     * <p>Description: </p> 
     * @return 
     * @see com.hehenian.biz.common.task.DefaultTask#getJobName() 
     */
    @Override
    protected String getJobName() {
        return jobName;
    }

    /* 
     * 
     * (no-Javadoc) 
     * <p>Title: doJob</p> 
     * <p>Description: 投资人的罚息收益是 T -3 允许逾期3天</p>  
     * @see com.hehenian.biz.common.task.DefaultTask#doJob() 
     */
    @Override
    protected void doJob() {
        List<InvestRepaymentDo> overDueRepaymentList = null;
        Date currentDate = new Date();
        try {
            overDueRepaymentList = investRepaymentComponent.selectOverDueInvestRepayList(currentDate);
            for (InvestRepaymentDo investRepay : overDueRepaymentList) {
                //BorrowDo borrow = borrowComponent.getById(repay.getBorrowId());
                //double feeRate = FeeUtil.parseBorrowFeelog(borrow.getFeelog());
                //目前采用固定值
                double feeRate = 0.001;
                //逾期日期
                long lateDay = DateUtil.diffDays(investRepay.getRepayDate(), currentDate);
                
                //一天的罚息
                double oneDayFee = CalculateUtils.mul(investRepay.getRecivedPrincipal() ,feeRate);
                //逾期罚息
                double lateFee = 0;
                if(lateDay>3){
                    lateFee = CalculateUtils.round(CalculateUtils.mul(oneDayFee, (lateDay -3)),2);
                }
                InvestRepaymentDo newInvestRepaymentDo = new InvestRepaymentDo();
                newInvestRepaymentDo.setIsLate(2);
                newInvestRepaymentDo.setLateDay( (int)lateDay);
                newInvestRepaymentDo.setRecivedFi(lateFee);
                newInvestRepaymentDo.setId(investRepay.getId());
                //更新罚息信息
                investRepaymentComponent.updateInvestRepaymentById(newInvestRepaymentDo);
            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
        }
    }
}
