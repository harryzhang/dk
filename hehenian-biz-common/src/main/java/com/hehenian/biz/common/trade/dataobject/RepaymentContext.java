/**  
 * @Project: hehenian-biz-service
 * @Package com.hehenian.biz.service.trade.impl
 * @Title: RepaymentContext.java
 * @Description: TODO
 * @author: zhangyunhmf
 * @date 2014年10月22日 上午11:37:52
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.trade.dataobject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.util.CalculateUtils;

/**
 * 
 * @author: zhangyunhmf
 * @date 2014年10月22日 上午11:37:52
 */
public class RepaymentContext implements java.io.Serializable {

    /**
     * @Fields serialVersionUID : TODO
     */
    private static final long         serialVersionUID                  = -85214436067671611L;

    /**
     * 上下文环境变量KEY
     */
    public static final String        CONTEXT_BORROW                    = "borrow";                            // 标的对象
    public static final String        CONTEXT_REPAYMENT                 = "repaymentDo";                       // 还款对象
    public static final String        CONTEXT_BORROW_ID                 = "borrowId";                          // 标的ID
    public static final String        CONTEXT_REPAYMENT_ID              = "repaymentId";                       // 还款id
    public static final String        CONTEXT_INVEST_LIST               = "investList";                        // 投资列表
    public static final String        CONTEXT_USER_ID                   = "userId";                            // 当期用户ID
    public static final String        CONTEXT_OUT_CUST_ID               = "outCustId";                         // 出资汇付账户
    public static final String        CONTEXT_REPAY_OPERATION_TYPE      = "repayOperationType";                // 还款类型：
                                                                                                                // 正常还款，提前结清，代偿
    public static final String        CONTEXT_USER_NAME                 = "username";                          // 当前用户名
    public static final String        CONTEXT_NEED_SUM                  = "needSum";                           // 应还款总金额
    public static final String        CONTEXT_WEB_URL                   = "CONTEXT_WEB_URL";                   // 系统url
    public static final String        CONTEXT_CALL_ERROR_IDX            = "CALL_ERROR_IDX";                    // 调用汇付失败的记录下标号

    // fundrecord 资金变动记录remark模板
    public static final String        fundrecord_remark_template        = "fundrecord_remark_repay.ftl";
    public static final String        fundrecord_remark_url             = "fundrecord_remark_url.ftl";
    public static final String        fundrecord_remark_template_fi     = "fundrecord_remark_repay_fi.ftl";
    public static final String        fundrecord_remark_template_invest = "fundrecord_remark_repay_invest.ftl";
    public static final String        sms_invest_template               = "sms_repay_invest.ftl";              // 投资人邮件模板

    /**
     * 罚金
     */
    public static final String        FEE_FI                            = "FEE_FI";
    /**
     * 手续费
     */
    public static final String        FEE_REPAY                         = "FEE_REPAY";
    /**
     * 咨询费
     */
    public static final String        FEE_CONSULT                       = "FEE_CONSULT";

    /**
     * 提前结清手续费
     */
    public static final String        FEE_PRE_SETTLE                    = "FEE_PRE_SETTLE";

    /**
     * 还款期数的ID ,对于提前结清可能是当前期， 如果不满3期则为第三期， 总期数不足三期时为最后一期
     */
    private long                      repaymentId;
    /**
     * 标的ID
     */
    private long                      borrowId;
    /**
     * 还款用户， 对于代偿是代偿账号对应的用户
     */
    private long                      userId;
    /**
     * userId对应的名称
     */
    private String                    username;
    /**
     * 操作类型： 正常还款，代偿， 提前结清
     */
    private String                    operationType;
    /**
     * 出款人汇付id， 正常还款是借款人， 代偿是代偿方
     */
    private String                    outCustId;
    /**
     * 系统URL
     */
    private String                    webURL;

    /**
     * repaymentId对应的对象， 对于提前结清是一个汇总数据
     */
    private RepaymentDo               repaymentDo;
    /**
     * borrowId对应的标的对象
     */
    private BorrowDo                  borrow;
    /**
     * userId 对应的用户对象
     */
    private AccountUserDo             userDo;
    /**
     * 回款List
     */
    private List<InvestRepaymentWrap> investList;
    /**
     * 调用汇付失败时回款List的下标
     */
    private int                       callChinapnrErrorIndex            = -1;

    public RepaymentContext(long repaymentId, long borrowId, long userId, String username, String operationType,
            String outCustId, String webURL) {

        this.repaymentId = repaymentId;
        this.borrowId = borrowId;
        this.userId = userId;
        this.username = username;
        this.operationType = operationType;
        this.outCustId = outCustId;
        this.webURL = webURL;
    }

    /**
     * 释放对象
     * 
     * @author: zhangyunhmf
     * @date: 2014年10月22日下午4:03:38
     */
    public void freeContext() {
        this.repaymentDo = null;
        this.borrow = null;
        this.userDo = null;
        if (null != this.investList) {
            this.investList.clear();
        }
        this.investList = null;
    }

    /**
     * 统计回款list的总回款金额
     */
    public double getInvestListTotalAmount() {
        double repayTotalSum = 0; // 应还总金额
        // 从回款list统计应回款金额总额
        for (int i = 0, size = investList.size(); i < size; i++) {
            InvestRepaymentWrap irWrap = investList.get(i);
            InvestRepaymentDo investRepay = irWrap.getInvestRepaymentDO();
            repayTotalSum = CalculateUtils.add(repayTotalSum, investRepay.getRecivedTotalAmount());
        }
        return repayTotalSum;
    }

    /**
     * 统计回款list的本金总额
     */
    public double getInvestListTotalPrincipal() {
        double repayTotalSum = 0; // 应还本金总额
        // 从回款list统计应回款金额总额
        for (int i = 0, size = investList.size(); i < size; i++) {
            InvestRepaymentWrap irWrap = investList.get(i);
            InvestRepaymentDo investRepay = irWrap.getInvestRepaymentDO();
            repayTotalSum = CalculateUtils.add(repayTotalSum, investRepay.getRecivedPrincipal() == null ? 0
                    : investRepay.getRecivedPrincipal());
        }
        return repayTotalSum;
    }

    /**
     * 统计回款list的利息总额
     */
    public double getInvestListTotalInterest() {
        double repayTotalSum = 0; // 应还利息总额
        // 从回款list统计应回款金额总额
        for (int i = 0, size = investList.size(); i < size; i++) {
            InvestRepaymentWrap irWrap = investList.get(i);
            InvestRepaymentDo investRepay = irWrap.getInvestRepaymentDO();
            repayTotalSum = CalculateUtils.add(repayTotalSum, investRepay.getRecivedInterest() == null ? 0
                    : investRepay.getRecivedInterest());
        }
        return repayTotalSum;
    }

    /**
     * 统计回款列表总的代偿金额
     * 
     * @return
     * @author: zhangyunhmf
     * @date: 2014年11月26日下午2:57:42
     */
    public double getCompAmont() {
        double repayTotalSum = 0; // 应还代偿金额总额
        // 从回款list统计应回款金额总额
        for (int i = 0, size = investList.size(); i < size; i++) {
            InvestRepaymentWrap irWrap = investList.get(i);
            InvestRepaymentDo investRepay = irWrap.getInvestRepaymentDO();
            if (investRepay.getIsWebRepay().intValue() == 2) {
                repayTotalSum = CalculateUtils.add(repayTotalSum, investRepay.getRecivedTotalAmount());
            }
        }
        return repayTotalSum;
    }

    /**
     * 分摊费用
     * 
     * @author: zhangyunhmf
     * @date: 2014年10月8日下午2:03:51
     */
    public void splitFee() {

        List<RepaymentFeeDo> repayFeelist = this.repaymentDo.getFeeList();
        if (null == repayFeelist || repayFeelist.isEmpty()) {// 没有费用需要分摊
            return;
        }

        if (null == this.investList || this.investList.size() < 1) {
            return;
        }

        double repayTotalSum = this.getInvestListTotalAmount(); // 从回款list统计应回款金额总额
                                                                // =应还总金额

        // 开始为每一笔回款分摊费用

        // 每个费用的分摊逻辑，按占比， 分摊费用=(投资人应收总额/借款人应还款总额)* 总费用
        for (int j = 0; j < repayFeelist.size(); j++) {
            RepaymentFeeDo rf = repayFeelist.get(j);
            double baseAmount = CalculateUtils.sub(rf.getStillAmount(), rf.getHasAmount()); // 应分摊金额
            double proportionFee = 0;
            if (0 >= baseAmount) {// 0 不需要分摊
                continue;
            }

            double totalFeeSum = 0; // 累计分摊
            int size = investList.size() - 1;
            for (int i = 0; i < size; i++) {
                InvestRepaymentWrap irWrap = investList.get(i);
                InvestRepaymentDo investRepay = irWrap.getInvestRepaymentDO();

                // 分摊费用(投资人应收总额/借款人应还款总额)* 总费用
                // 按占比分摊
                proportionFee = CalculateUtils.round(
                        CalculateUtils.splitByRate(baseAmount, investRepay.getRecivedTotalAmount(), repayTotalSum), 2,BigDecimal.ROUND_DOWN);
                totalFeeSum = CalculateUtils.add(totalFeeSum, proportionFee);

                RepaymentFeeDo rfd = new RepaymentFeeDo();
                rfd.setHasAmount(proportionFee);
                rfd.setFeeCode(rf.getFeeCode());
                irWrap.addProportionFee(rfd);
                rfd.setRepaymentId(this.getRepaymentId()); // 当前还款ID

            }// end 费用列表的循环

            // 最后一个用减法
            InvestRepaymentWrap irWrap = investList.get(size);
            proportionFee = CalculateUtils.sub(baseAmount, totalFeeSum);
            RepaymentFeeDo rfd = new RepaymentFeeDo();
            rfd.setHasAmount(proportionFee);
            rfd.setFeeCode(rf.getFeeCode());
            irWrap.addProportionFee(rfd);
            rfd.setRepaymentId(this.getRepaymentId()); // 当前还款ID

        }// end investList 循环
    }

    /**
     * 汇付，为了能重复还款，允许部分成功 需要记录成功交易的金额
     * 
     * @author: zhangyunhmf
     * @date: 2014年10月23日下午1:04:23
     */
    public void freshRepaymentAmount() {

        List<RepaymentFeeDo> rpfList = this.repaymentDo.getFeeList();

        double hasP = 0;
        double hasI = 0;
        Map<String, Double> feeRealDeductAmountMap = new HashMap<String, Double>();

        // 统计成功的金额
        for (InvestRepaymentWrap irWrap : investList) {
            if (!irWrap.isSuccess()) {// 成功参与统计
                continue;
            }
            // 统计实际已收本金和利息
            InvestRepaymentDo ir = irWrap.getInvestRepaymentDO();
            hasP = CalculateUtils.add(hasP, ir.getRecivedPrincipal());
            hasI = CalculateUtils.add(hasI, ir.getRecivedInterest());

            // 统计费用 :手续费，提前结清手续费，咨询费
            List<RepaymentFeeDo> proportionList = irWrap.getProportionFeeList();
            if (null != proportionList) {
                for (RepaymentFeeDo pf : proportionList) {
                    Double realDeductAmount = feeRealDeductAmountMap.get(pf.getFeeCode());
                    if (null == realDeductAmount) {
                        realDeductAmount = 0d;
                    }
                    realDeductAmount = CalculateUtils.add(realDeductAmount, pf.getHasAmount());
                    feeRealDeductAmountMap.put(pf.getFeeCode(), Double.valueOf("" + realDeductAmount));

                }
            }

        }// end 统计成功的金额

        // 更新费用实际已收
        if (null != rpfList) {
            for (RepaymentFeeDo rpf : rpfList) {
                Double realDeductAmount = feeRealDeductAmountMap.get(rpf.getFeeCode());
                rpf.setHasAmount(realDeductAmount);
            }
        }
        // end 开始统计已经成功扣除的本金利息，罚金，手续费，提前结清手续费，咨询费

        this.repaymentDo.setHasPi(hasP + hasI);
    }

    /**
     * @return repaymentId
     */
    public long getRepaymentId() {
        if (null != this.repaymentDo) {
            return this.repaymentDo.getId();
        }
        return repaymentId;
    }

    /**
     * @param repaymentId
     *            the repaymentId to set
     */
    public void setRepaymentId(long repaymentId) {
        this.repaymentId = repaymentId;
    }

    /**
     * @return borrowId
     */
    public long getBorrowId() {
        return borrowId;
    }

    /**
     * @param borrowId
     *            the borrowId to set
     */
    public void setBorrowId(long borrowId) {
        this.borrowId = borrowId;
    }

    /**
     * @return userId
     */
    public long getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return operationType
     */
    public String getOperationType() {
        return operationType;
    }

    /**
     * @param operationType
     *            the operationType to set
     */
    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    /**
     * @return outCustId
     */
    public String getOutCustId() {
        return outCustId;
    }

    /**
     * @param outCustId
     *            the outCustId to set
     */
    public void setOutCustId(String outCustId) {
        this.outCustId = outCustId;
    }

    /**
     * @return webURL
     */
    public String getWebURL() {
        return webURL;
    }

    /**
     * @param webURL
     *            the webURL to set
     */
    public void setWebURL(String webURL) {
        this.webURL = webURL;
    }

    /**
     * @return repaymentDo
     */
    public RepaymentDo getRepaymentDo() {
        return repaymentDo;
    }

    /**
     * @param repaymentDo
     *            the repaymentDo to set
     */
    public void setRepaymentDo(RepaymentDo repaymentDo) {
        this.repaymentDo = repaymentDo;
    }

    /**
     * @return borrow
     */
    public BorrowDo getBorrow() {
        return borrow;
    }

    /**
     * @param borrow
     *            the borrow to set
     */
    public void setBorrow(BorrowDo borrow) {
        this.borrow = borrow;
    }

    /**
     * @return userDo
     */
    public AccountUserDo getUserDo() {
        return userDo;
    }

    /**
     * @param userDo
     *            the userDo to set
     */
    public void setUserDo(AccountUserDo userDo) {
        this.userDo = userDo;
    }

    /**
     * @return investList
     */
    public List<InvestRepaymentWrap> getInvestList() {
        return investList;
    }

    /**
     * @param investList
     *            the investList to set
     */
    public void setInvestList(List<InvestRepaymentWrap> investList) {
        this.investList = investList;
    }

    /**
     * @return the callChinapnrErrorIndex
     */
    public int getCallChinapnrErrorIndex() {
        return callChinapnrErrorIndex;
    }

    /**
     * @param callChinapnrErrorIndex
     *            the callChinapnrErrorIndex to set
     */
    public void setCallChinapnrErrorIndex(int callChinapnrErrorIndex) {
        this.callChinapnrErrorIndex = callChinapnrErrorIndex;
    }

}
