package com.hehenian.biz.common.trade.dataobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 交易信息
 * 
 * @author liuzgmf
 *
 */
public class FundrecordDo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;// 编号
	private Long userId;// 用户ID
	private String fundMode;// 类型名称
	private Double handleSum;// 操作金额
	private Double usableSum;// 可用金额

	private Double freezeSum;// 冻结金额
	private Double dueinSum;// 待收金额(和合年当前值=待还本金+待还利息)
	private Long trader;// 交易者
	private Date recordTime;// 记录时间
	private Double dueoutSum;// 待还金额

	private Double dueinCapitalSum;// 待还本金
	private Double dueinAccrualSum;// 待还利息
	private String remarks;//
	private Integer operateType;// 操作类型
	private Double oninvest;//

	private String paynumber;// 支付流水号
	private String paybank;// 银行名称
	private Double cost;// 服务费
	private Double income;// 收入金额
	private Double spending;// 支出金额

	private Long borrowId;// 借款编号
	private Long repaymentId;// 还款编号
	private Integer type;// 操作状态：0 成功 1 失败
	private String ordId;// 唯一流水号

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFundMode() {
		return fundMode;
	}

	public void setFundMode(String fundMode) {
		this.fundMode = fundMode;
	}

	public Double getHandleSum() {
		return handleSum;
	}

	public void setHandleSum(Double handleSum) {
		this.handleSum = handleSum;
	}

	public Double getUsableSum() {
		return usableSum;
	}

	public void setUsableSum(Double usableSum) {
		this.usableSum = usableSum;
	}

	public Double getFreezeSum() {
		return freezeSum;
	}

	public void setFreezeSum(Double freezeSum) {
		this.freezeSum = freezeSum;
	}

	public Double getDueinSum() {
		return dueinSum;
	}

	public void setDueinSum(Double dueinSum) {
		this.dueinSum = dueinSum;
	}

	public Long getTrader() {
		return trader;
	}

	public void setTrader(Long trader) {
		this.trader = trader;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public Double getDueoutSum() {
		return dueoutSum;
	}

	public void setDueoutSum(Double dueoutSum) {
		this.dueoutSum = dueoutSum;
	}

	public Double getDueinCapitalSum() {
		return dueinCapitalSum;
	}

	public void setDueinCapitalSum(Double dueinCapitalSum) {
		this.dueinCapitalSum = dueinCapitalSum;
	}

	public Double getDueinAccrualSum() {
		return dueinAccrualSum;
	}

	public void setDueinAccrualSum(Double dueinAccrualSum) {
		this.dueinAccrualSum = dueinAccrualSum;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public Double getOninvest() {
		return oninvest;
	}

	public void setOninvest(Double oninvest) {
		this.oninvest = oninvest;
	}

	public String getPaynumber() {
		return paynumber;
	}

	public void setPaynumber(String paynumber) {
		this.paynumber = paynumber;
	}

	public String getPaybank() {
		return paybank;
	}

	public void setPaybank(String paybank) {
		this.paybank = paybank;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	public Double getSpending() {
		return spending;
	}

	public void setSpending(Double spending) {
		this.spending = spending;
	}

	public Long getBorrowId() {
		return borrowId;
	}

	public void setBorrowId(Long borrowId) {
		this.borrowId = borrowId;
	}

	public Long getRepaymentId() {
		return repaymentId;
	}

	public void setRepaymentId(Long repaymentId) {
		this.repaymentId = repaymentId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getOrdId() {
		return ordId;
	}

	public void setOrdId(String ordId) {
		this.ordId = ordId;
	}

}
