package com.hehenian.biz.common.trade.dataobject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.hehenian.biz.common.base.constant.Constants;
import com.hehenian.biz.common.util.CalculateUtils;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

public class RepaymentDo implements java.io.Serializable {

	// columns START
	private java.lang.Long id;
	private java.util.Date repayDate;
	private java.util.Date realRepayDate;
	private java.lang.String repayPeriod;
	private Double hasPi;
	private Double stillPrincipal;
	private Double stillInterest;
	private Double hasFi;
	private Double consultFee;
	private Double lateFi;
	private java.lang.Integer lateDay;
	private java.lang.Integer repayStatus;
	private java.lang.Long borrowId;
	private java.lang.Integer isLate;
	private java.lang.Integer isWebRepay;
	private Double investorForpayFi;
	private Double investorHaspayFi;
	private Double principalBalance;
	private Double interestBalance;
	private java.lang.Integer version;
	private java.util.Date executeTime;
	private java.lang.String identify;
	private Double repayFee;
	// columns END

	/**
	 * 费用子表
	 */
	private List<RepaymentFeeDo> feeList;

    /**
     * 获取已收费用总和
     * 
     * @return
     * @author: zhangyunhmf
     * @date: 2014年12月1日上午9:19:42
     */
    public double getFeeSum() {
        double sumFee = 0;
        if (null == feeList) {
            return 0;
        }
        for (RepaymentFeeDo rpf : feeList) {
            CalculateUtils.add(sumFee, rpf.getHasAmount());
        }
        return sumFee;
    }

	/**
	 * 加费用
	 * 
	 * @param rf
	 * @author: zhangyunhmf
	 * @date: 2014年10月22日下午5:48:15
	 */
	public void addRepaymentFee(RepaymentFeeDo rf) {
		if (null == feeList) {
			feeList = new ArrayList<RepaymentFeeDo>();
		}
		rf.setRepaymentId(this.getId());
		rf.setLastUpdateDate(new Date());
		feeList.add(rf);
	}

	/**
	 * 根据费用编码取费用对象
	 * 
	 * @param feeCode
	 *            费用编码
	 * @author: zhangyunhmf
	 * @date: 2014年10月22日下午5:48:15
	 */
	public RepaymentFeeDo getRepaymentFee(String feeCode) {
		if (null == feeList) {
			return null;
		}
		for (RepaymentFeeDo rpf : feeList) {
			if (rpf.getFeeCode().equals(feeCode)) {
				return rpf;
			}
		}
		return null;
	}

	/**
	 * 应还本金 +应还本息
	 * 
	 * @return
	 * @author: zhangyunhmf
	 * @date: 2014年10月22日下午2:21:52
	 */
	public double getStillPI() {
		double needSum = CalculateUtils.add(this.getStillInterest(),
				this.getStillPrincipal());
		return needSum;
	}

	/**
	 * 应还款总额
	 * 
	 * @return
	 * @author: zhangyunhmf
	 * @date: 2014年10月22日下午2:21:52
	 */
	public double getStillTotalAmount() {
		double needSum = CalculateUtils.add(this.getStillInterest(),
				this.getStillPrincipal());
		needSum = CalculateUtils.add(needSum, this.getRepayFee());
		needSum = CalculateUtils.add(needSum, this.getConsultFee());
		needSum = CalculateUtils.add(needSum, this.getLateFi() == null ? 0
				: this.getLateFi());

        needSum = CalculateUtils.sub(needSum, this.getFeeSum());
		needSum = CalculateUtils.sub(needSum, this.getHasPi() == null ? 0
				: this.getHasPi());
		return needSum;
	}

	public java.lang.Long getId() {
		return this.id;
	}

	public void setId(java.lang.Long value) {
		this.id = value;
	}

	public java.util.Date getRepayDate() {
		return this.repayDate;
	}

	public void setRepayDate(java.util.Date value) {
		this.repayDate = value;
	}

	public java.util.Date getRealRepayDate() {
		return this.realRepayDate;
	}

	public void setRealRepayDate(java.util.Date value) {
		this.realRepayDate = value;
	}

	public java.lang.String getRepayPeriod() {
		return this.repayPeriod;
	}

	public void setRepayPeriod(java.lang.String value) {
		this.repayPeriod = value;
	}

	public Double getHasPi() {
		return this.hasPi;
	}

	public void setHasPi(Double value) {
		this.hasPi = value;
	}

	public Double getStillPrincipal() {
		return this.stillPrincipal;
	}

	public void setStillPrincipal(Double value) {
		this.stillPrincipal = value;
	}

	public Double getStillInterest() {
		return this.stillInterest;
	}

	public void setStillInterest(Double value) {
		this.stillInterest = value;
	}

	public Double getHasFi() {
		return this.hasFi;
	}

	public void setHasFi(Double value) {
		this.hasFi = value;
	}

	public Double getConsultFee() {
		return this.consultFee;
	}

	public void setConsultFee(Double value) {
		this.consultFee = value;
	}

	public Double getLateFi() {
		return this.lateFi;
	}

	public void setLateFi(Double value) {
		this.lateFi = value;
	}

	public java.lang.Integer getLateDay() {
		return this.lateDay;
	}

	public void setLateDay(java.lang.Integer value) {
		this.lateDay = value;
	}

	public java.lang.Integer getRepayStatus() {
		return this.repayStatus;
	}

	public void setRepayStatus(java.lang.Integer value) {
		this.repayStatus = value;
	}

	public java.lang.Long getBorrowId() {
		return this.borrowId;
	}

	public void setBorrowId(java.lang.Long value) {
		this.borrowId = value;
	}

	public java.lang.Integer getIsLate() {
		return this.isLate;
	}

	public void setIsLate(java.lang.Integer value) {
		this.isLate = value;
	}

	public java.lang.Integer getIsWebRepay() {
		return this.isWebRepay;
	}

	public void setIsWebRepay(java.lang.Integer value) {
		this.isWebRepay = value;
	}

	public Double getInvestorForpayFi() {
		return this.investorForpayFi;
	}

	public void setInvestorForpayFi(Double value) {
		this.investorForpayFi = value;
	}

	public Double getInvestorHaspayFi() {
		return this.investorHaspayFi;
	}

	public void setInvestorHaspayFi(Double value) {
		this.investorHaspayFi = value;
	}

	public Double getPrincipalBalance() {
		return this.principalBalance;
	}

	public void setPrincipalBalance(Double value) {
		this.principalBalance = value;
	}

	public Double getInterestBalance() {
		return this.interestBalance;
	}

	public void setInterestBalance(Double value) {
		this.interestBalance = value;
	}

	public java.lang.Integer getVersion() {
		return this.version;
	}

	public void setVersion(java.lang.Integer value) {
		this.version = value;
	}

	public java.util.Date getExecuteTime() {
		return this.executeTime;
	}

	public void setExecuteTime(java.util.Date value) {
		this.executeTime = value;
	}

	public java.lang.String getIdentify() {
		return this.identify;
	}

	public void setIdentify(java.lang.String value) {
		this.identify = value;
	}

	public Double getRepayFee() {
		return this.repayFee;
	}

	public void setRepayFee(Double value) {
		this.repayFee = value;
	}

	/**
	 * @return feeList
	 */
	public List<RepaymentFeeDo> getFeeList() {
		return feeList;
	}

	/**
	 * @param feeList
	 *            the feeList to set
	 */
	public void setFeeList(List<RepaymentFeeDo> feeList) {
		this.feeList = feeList;
	}

	/**
	 * 计算提前结清手续费
	 * 
	 * @return
	 * @author: zhangyunhmf
	 * @date: 2014年10月15日上午9:07:57
	 */
	public double buildPreSettleFee(double rate) {
		if (null == this.principalBalance)
			return 0;
		return CalculateUtils.round(
				(this.principalBalance.doubleValue() * rate), 2); // 提前还款手续费
	}

	/**
	 * 查询费用子表
	 * 
	 * @author: zhangyunhmf
	 * @date: 2014年10月22日下午4:25:31
	 */
	public void buildRepaymentFee() {
        // 罚金
		if (null != this.getLateFi() && this.getLateFi().intValue() != 0) {
            RepaymentFeeDo rfFi = new RepaymentFeeDo(null, Constants.FEE_CODE_FX,
					this.getLateFi(), 0d);
			this.addRepaymentFee(rfFi);
		}
        // 咨询费
		if (null != this.getConsultFee()
				&& this.getConsultFee().intValue() != 0) {
            RepaymentFeeDo rfConsult = new RepaymentFeeDo(null, Constants.FEE_CODE_CONSULT,
					this.getConsultFee(), 0d);
			this.addRepaymentFee(rfConsult);
		}
        // 手续费
		if (null != this.getRepayFee() && this.getRepayFee().intValue() != 0) {
            RepaymentFeeDo rfRepayFee = new RepaymentFeeDo(null, Constants.FEE_CODE_SERVICE_CHARGE,
					this.getRepayFee(), 0d);
			this.addRepaymentFee(rfRepayFee);
		}
        // // 提前结清手续费
        // double preFee = this.buildPreSettleFee(Constants.PRE_REPAY_RATE);
        // if (preFee != 0) {
        // RepaymentFeeDo rf = new RepaymentFeeDo(null, Constants.FEE_CODE_PRE,
        // preFee, 0d);
        // this.addRepaymentFee(rf);
        // }

	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("Id", getId()).append("RepayDate", getRepayDate())
				.append("RealRepayDate", getRealRepayDate())
				.append("RepayPeriod", getRepayPeriod())
				.append("HasPi", getHasPi())
				.append("StillPrincipal", getStillPrincipal())
				.append("StillInterest", getStillInterest())
				.append("HasFi", getHasFi())
				.append("ConsultFee", getConsultFee())
				.append("LateFi", getLateFi()).append("LateDay", getLateDay())
				.append("RepayStatus", getRepayStatus())
				.append("BorrowId", getBorrowId())
				.append("IsLate", getIsLate())
				.append("IsWebRepay", getIsWebRepay())
				.append("InvestorForpayFi", getInvestorForpayFi())
				.append("InvestorHaspayFi", getInvestorHaspayFi())
				.append("PrincipalBalance", getPrincipalBalance())
				.append("InterestBalance", getInterestBalance())
				.append("Version", getVersion())
				.append("ExecuteTime", getExecuteTime())
				.append("Identify", getIdentify())
				.append("RepayFee", getRepayFee()).toString();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof RepaymentDo == false)
			return false;
		if (this == obj)
			return true;
		RepaymentDo other = (RepaymentDo) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}
}
