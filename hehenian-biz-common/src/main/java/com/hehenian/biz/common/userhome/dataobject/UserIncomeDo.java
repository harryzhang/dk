package com.hehenian.biz.common.userhome.dataobject;

import java.io.Serializable;

public class UserIncomeDo implements Serializable {

	/**
	 * 应收本金
	 */
	private Double recivedPrincipal = 0d;
	/**
	 * 应收利息
	 */
	private Double recievedInterest = 0d;
	/**
	 * 已收本金
	 */
	private Double hasPrincipal = 0d;
	/**
	 * 已收利息
	 */
	private Double totalInterestAmount = 0d;
	/**
	 * 总投资金额
	 */
	private Double totalInvestAmount = 0d;
	/**
	 * 可提取金额
	 */
	private Double withdrawalAmount = 0d;

	/**
	 * 冻结金额
	 */
	private Double freezeAmount = 0d;

	/**
	 * 昨日增值
	 */
	private Double dailyIncome = 0d;

	/**
	 * 资产估值
	 */
	private Double assetValue = 0d;

	/**
	 * 会员奖励
	 */
	private Double reward = 0d;

	/**
	 * 累计收益
	 */
	private Double earnSum = 0d;

	public Double getRecivedPrincipal() {
		return recivedPrincipal;
	}

	public void setRecivedPrincipal(Double recivedPrincipal) {
		this.recivedPrincipal = recivedPrincipal;
	}

	public Double getRecievedInterest() {
		return recievedInterest;
	}

	public void setRecievedInterest(Double recievedInterest) {
		this.recievedInterest = recievedInterest;
	}

	public Double getHasPrincipal() {
		return hasPrincipal;
	}

	public void setHasPrincipal(Double hasPrincipal) {
		this.hasPrincipal = hasPrincipal;
	}

	public Double getTotalInterestAmount() {
		return totalInterestAmount;
	}

	public void setTotalInterestAmount(Double totalInterestAmount) {
		this.totalInterestAmount = totalInterestAmount;
	}

	public Double getTotalInvestAmount() {
		return totalInvestAmount;
	}

	public void setTotalInvestAmount(Double totalInvestAmount) {
		this.totalInvestAmount = totalInvestAmount;
	}

	public Double getWithdrawalAmount() {
		return withdrawalAmount;
	}

	public void setWithdrawalAmount(Double withdrawalAmount) {
		this.withdrawalAmount = withdrawalAmount;
	}

	public Double getFreezeAmount() {
		return freezeAmount;
	}

	public void setFreezeAmount(Double freezeAmount) {
		this.freezeAmount = freezeAmount;
	}

	/**
	 * @return the dailyIncome
	 */
	public Double getDailyIncome() {
		return dailyIncome;
	}

	/**
	 * @param dailyIncome
	 *            the dailyIncome to set
	 */
	public void setDailyIncome(Double dailyIncome) {
		this.dailyIncome = dailyIncome;
	}

	/**
	 * @return the assetValue
	 */
	public Double getAssetValue() {
		return assetValue;
	}

	/**
	 * @param assetValue
	 *            the assetValue to set
	 */
	public void setAssetValue(Double assetValue) {
		this.assetValue = assetValue;
	}

	/**
	 * @return the earnSum
	 */
	public Double getEarnSum() {
		if (null == this.reward) {
			this.reward = 0d;
		}
		if (null == this.totalInterestAmount) {
			this.totalInterestAmount = 0d;
		}
		return this.totalInterestAmount.doubleValue()
				+ this.reward.doubleValue();
	}

	/**
	 * @param earnSum
	 *            the earnSum to set
	 */
	public void setEarnSum(Double earnSum) {
		this.earnSum = earnSum;
	}

	/**
	 * @return the reward
	 */
	public Double getReward() {
		return reward;
	}

	/**
	 * @param reward
	 *            the reward to set
	 */
	public void setReward(Double reward) {
		this.reward = reward;
	}

}
