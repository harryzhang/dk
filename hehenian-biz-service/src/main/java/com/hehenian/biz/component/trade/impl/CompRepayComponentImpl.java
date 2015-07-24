/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.component.trade.impl
 * @Title: InsteadOfRepayment.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年9月28日 下午4:26:02
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.component.trade.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.trade.dataobject.InvestRepaymentDo;
import com.hehenian.biz.common.trade.dataobject.RepaymentContext;
import com.hehenian.biz.common.trade.dataobject.RepaymentDo;

/**
 * 代偿
 * 
 * @author: zhangyunhmf
 * @date 2014年9月28日 下午4:26:02
 */
@Component("compRepayComponentImpl")
public class CompRepayComponentImpl extends DefaultAbstractRepayComponent {

    private static final String notify_template = "sms_repay_normal.ftl";

    /**
     * 
     * checkAmount 检查用户账户金额是否足够
     * 
     * @return boolean
     * @exception
     * @since 1.0.0
     */
    @Override
    protected boolean checkAmount(long userId, double needAmount) {
        return true;
    }

    /**
     * 
     * checkUser 检查用户合法性
     * 
     * @return boolean
     * @exception
     * @since 1.0.0
     */
    @Override
    protected boolean checkUser(RepaymentContext rc) {
        return true;
    }

    /**
     * 根据用户ID 获取用户对象， 如果是代偿， 代偿子类会覆盖这个方法根据，配置文件里的代偿账号查询用户对象
     * 
     * @return
     * @author: zhangyunhmf
     * @date: 2014年10月22日下午6:22:47
     */
    @Override
    public int buildPubisher(RepaymentContext rc) {
        // 代偿的时候根据代偿账户取用户信息
        AccountUserDo user = this.userComponent.getUserByCustId(this.chinaPnrConfig.getCompCustId());
        if (null == user) {
            return 10;
        }
        // 构建代偿用户
        rc.setUserDo(user);
        rc.setUserId(user.getId());
        rc.setUsername(user.getUsername());
        return 0;
    }

    /**
     * 
     * checkRepaymentStatus 检查还款记录的状态, 如果已经代偿不可再代偿
     * 
     * @return boolean
     * @exception
     * @since 1.0.0
     */
    @Override
    protected boolean checkRepaymentStatus(RepaymentDo repay) {
        // RepaymentDo repay = this.getRepaymentDo();
        return (repay.getRepayStatus() == 1) && (repay.getIsWebRepay() == 1);
    }

    /**
     * 出资账户，如果代偿是代偿方，从配置文件读取， 否则就是借款人
     */
    @Override
    public String getOutCustId(RepaymentContext rc) {
        return chinaPnrConfig.getCompCustId(); // 代偿账户汇付ID
    }

    /**
     * 出资账户，如果代偿是代偿方，从配置文件读取， 否则就是借款人
     */
    @Override
    public String getOutAcctId() {
        return chinaPnrConfig.getCompAccount(); // 代偿账户
    }

    /*
     * (no-Javadoc) <p>Title: updateBorrow</p> <p>Description: </p>
     * 
     * @param borrowId
     * 
     * @see
     * com.hehenian.biz.component.trade.impl.DefaultRepayment#updateBorrow(long)
     */
    @Override
    public void updateBorrow(long borrowId) {

    }

    /*
     * (no-Javadoc) <p>Title: updateRepaymentStatus</p> <p>Description: </p>
     * 
     * @see
     * com.hehenian.biz.component.trade.impl.DefaultRepayment#updateRepaymentStatus
     * ()
     */
    @Override
    public void updateRepaymentStatus(RepaymentContext rc) {
        RepaymentDo repaymentDo = rc.getRepaymentDo();
        this.repaymentComponent.updateIsWebRepayById(repaymentDo.getId(), repaymentDo.getVersion());
    }

    /*
     * (no-Javadoc) <p>Title: getRepayment</p> <p>Description: </p>
     * 
     * @return
     * 
     * @see
     * com.hehenian.biz.component.trade.impl.DefaultRepayment#getRepayment(long)
     */
    @Override
    public RepaymentDo buildRepayment(RepaymentContext rc) {

        RepaymentDo repaymentDo = super.buildRepayment(rc);

        // 代偿手续费和咨询费为,罚金 0
        repaymentDo.setRepayFee(0d);
        repaymentDo.setConsultFee(0d);
        repaymentDo.setLateFi(0d);
        return repaymentDo;

    }

    /**
     * 构建还款列表
     * 
     * @author: zhangyunhmf
     * @date: 2014年10月8日下午1:58:05
     */
    @Override
    public int buildInvestList(RepaymentContext rc) {
        // RepaymentContext rc = this.getContext();
        RepaymentDo repayDo = rc.getRepaymentDo();
        // 查询代偿的投资信息
        List<InvestRepaymentDo> investList = this.investRepaymentComponent.selectCompInvestByRepayId(repayDo.getId());
        // rc.setInvestList(investList);

        if (null == investList) {
            return 0;
        }
        rc.setInvestList(this.convertInvestRepaymentWrap(investList));
        return investList.size();
    }

    /*
     * (no-Javadoc) <p>Title: buildRepaymentFee</p> <p>Description: </p>
     * 
     * @see com.hehenian.biz.component.trade.impl.DefaultAbstractRepayComponent#
     * buildRepaymentFee()
     */
    @Override
    public void buildRepaymentFee(RepaymentDo repayDo, Integer paymentMode) {
        repayDo.setFeeList(null);
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

        // 生成代偿记录, 代偿记录状态是3
        long newOwner = this.getPublisher(rc).getId();
        investRepaymentComponent.addWebPayRecord(investDo.getId().longValue(), newOwner);

        InvestRepaymentDo newInvestRepaymentDo = new InvestRepaymentDo();
        newInvestRepaymentDo.setInterestOwner(investDo.getOwner());
        // 如果是代偿操作这里应该设置成2
        newInvestRepaymentDo.setIsWebRepay(2);
        newInvestRepaymentDo.setRecivedFi(investDo.getRecivedFi());
        newInvestRepaymentDo.setOwner(investDo.getOwner());
        newInvestRepaymentDo.setInvestId(investDo.getInvestId());
        newInvestRepaymentDo.setRepayId(investDo.getRepayId());
        newInvestRepaymentDo.setId(investDo.getId());
        newInvestRepaymentDo.setRecivedInterest(investDo.getRecivedInterest());
        newInvestRepaymentDo.setRecivedPrincipal(investDo.getRecivedPrincipal());
        investRepaymentComponent.updateRecievedAmount(newInvestRepaymentDo);

    }

    // /**
    // * 更新风险保障金
    // *
    // * @author: zhangyunhmf
    // * @date: 2014年10月23日上午11:29:56
    // */
    // @Override
    // protected void updateGuarantee(RepaymentContext rc) {
    // RepaymentDo repaymentDo = rc.getRepaymentDo();
    // BorrowDo borrow = rc.getBorrow();
    // // 计算代偿金额, 代偿的时候没有手续费和咨询费用
    // double repayAmount = repaymentDo.getHasPi(); // 本次实际偿还金额
    //
    // // 减少更新担保公司的资金
    // guaranteeInstitutionsComponent.updateOrganMoney(-repayAmount);
    // insertRiskDetail(borrow.getId(), borrow.getPublisher(), "代偿逾期借款", "支出",
    // 0, repayAmount);
    // }
}
