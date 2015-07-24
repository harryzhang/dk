/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.trade.impl
 * @Title: NormalRepayment.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年9月28日 上午8:40:00
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.trade.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hehenian.biz.common.trade.dataobject.InvestRepaymentDo;
import com.hehenian.biz.common.trade.dataobject.RepaymentContext;
import com.hehenian.biz.common.trade.dataobject.RepaymentDo;

/** 
 * 借款人还款： 分为还投资人， 还代偿人
 * 
 * @author: zhangyunhmf
 * @date 2014年9月28日 上午8:40:00
 */
@Component("normalRepayComponentImpl")
public class NormalRepayComponentImpl extends DefaultAbstractRepayComponent {
    
    private static final String notify_template= "sms_repay_normal.ftl";
    
    /* (no-Javadoc) 
     * <p>Title: updateBorrow</p> 
     * <p>Description:更新标的的状态， 和还款期数 </p> 
     * @param borrowId 标的ID
     * @see com.hehenian.biz.component.trade.impl.DefaultRepayment#updateBorrow(long) 
     */
    @Override
    public void updateBorrow(long borrowId) {
        borrowComponent.updateBorrowStatusAndHasDeadlineById(borrowId);
        borrowComponent.updateBorrowStatus(borrowId);
        
    }
    
    /**
     * 构建还款列表
     *   
     * @author: zhangyunhmf
     * @date: 2014年10月8日下午1:58:05
     */
    @Override
    public int buildInvestList(RepaymentContext rc) {
        RepaymentDo repayDo = rc.getRepaymentDo();
        // 查询所有投资人的投资信息
        List<InvestRepaymentDo> investList = this.investRepaymentComponent.selectInvestInfoByRepayId(repayDo.getId()) ;

        if(null == investList){return 0;}
		rc.setInvestList(this.convertInvestRepaymentWrap(investList));
        return investList.size();
    }
}