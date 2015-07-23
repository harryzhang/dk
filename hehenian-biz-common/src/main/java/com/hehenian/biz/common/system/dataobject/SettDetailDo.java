/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.system.dataobject
 * @Title: SettDetailDo.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月6日 上午10:40:12
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.system.dataobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;

import com.hehenian.biz.common.loan.dataobject.LoanRepaymentFeeDo;

/**
 * 结算明细对象
 * 
 * @author: liuzgmf
 * @date 2015年1月6日 上午10:40:12
 */
public class SettDetailDo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer           period;               // 期数
    private Double            principal;            // 本金
    private Double            interest;             // 利息
    private Double            remainingPrincipal;   // 剩余本金
    private Double            consultFee;           // 咨询费
    private Double            servFee;              // 服务费/管理费
    private Date              repayDate;            // 还款日期
    
    private List<LoanRepaymentFeeDo> rfList = new ArrayList<LoanRepaymentFeeDo>();       //其他费用
    
    /**
     * @return period
     */
    public Integer getPeriod() {
        return period;
    }

    /**
     * @param period
     *            the period to set
     */
    public void setPeriod(Integer period) {
        this.period = period;
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
		return "SettDetailDo [period=" + period + ", principal=" + principal
				+ ", interest=" + interest + ", remainingPrincipal="
				+ remainingPrincipal + ", consultFee=" + consultFee
				+ ", servFee=" + servFee + ", repayDate=" + DateFormatUtils.format(repayDate, "yyyy/MM/dd")  + "]";
	}

	public List<LoanRepaymentFeeDo> getRfList() {
		return rfList;
	}

	public void setRfList(List<LoanRepaymentFeeDo> rfList) {
		this.rfList = rfList;
	}

}
