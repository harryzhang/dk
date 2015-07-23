/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.trade.dataobject
 * @Title: RepayDetailDo.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年12月15日 上午11:37:10
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.trade.dataobject;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 月还款费用明细类
 * 
 * @author: liuzgmf
 * @date 2014年12月15日 上午11:38:02
 */
public class RepayDetailDo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer           times;                // 期数
    private Double            principal;            // 本金
    private Double            interest;             // 利息
    private Double            remainingPrincipal;   // 剩余本金
    private Double            consultFee;           // 咨询费
    private Double            servFee;              // 服务费/管理费
    private Date              repayDate;            // 还款日期

    /**
     * @return times
     */
    public Integer getTimes() {
        return times;
    }

    /**
     * @param times
     *            the times to set
     */
    public void setTimes(Integer times) {
        this.times = times;
    }

    /**
     * @return principal
     */
    public Double getPrincipal() {
        return principal;
    }

    /**
     * @param principal
     *            the principal to set
     */
    public void setPrincipal(Double principal) {
        this.principal = principal;
    }

    /**
     * @return interest
     */
    public Double getInterest() {
        return interest;
    }

    /**
     * @param interest
     *            the interest to set
     */
    public void setInterest(Double interest) {
        this.interest = interest;
    }

    /**
     * @return remainingPrincipal
     */
    public Double getRemainingPrincipal() {
        return remainingPrincipal;
    }

    /**
     * @param remainingPrincipal
     *            the remainingPrincipal to set
     */
    public void setRemainingPrincipal(Double remainingPrincipal) {
        this.remainingPrincipal = remainingPrincipal;
    }

    /**
     * @return consultFee
     */
    public Double getConsultFee() {
        return consultFee;
    }

    /**
     * @param consultFee
     *            the consultFee to set
     */
    public void setConsultFee(Double consultFee) {
        this.consultFee = consultFee;
    }

    /**
     * @return servFee
     */
    public Double getServFee() {
        return servFee;
    }

    /**
     * @param servFee
     *            the servFee to set
     */
    public void setServFee(Double servFee) {
        this.servFee = servFee;
    }

    /**
     * @return repayDate
     */
    public Date getRepayDate() {
        return repayDate;
    }

    /**
     * @param repayDate
     *            the repayDate to set
     */
    public void setRepayDate(Date repayDate) {
        this.repayDate = repayDate;
    }

    @Override
    public String toString() {
        return "RepayDetail [times=" + times + ", principal=" + principal + ", interest=" + interest
                + ", remainingPrincipal=" + remainingPrincipal + ", consultFee=" + consultFee + ", servFee=" + servFee
                + ", repayDate=" + DateFormatUtils.format(repayDate, "yyyy/MM/dd") + "]";
    }

}
