/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.common.activity.dataobject;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

public class ActivityOrderDo implements java.io.Serializable {

    private static final String productType1 = "1";
    private static final String productType2 = "2";
    private static final String productType3 = "3";
    private static final String productType4 = "4";

    // columns START
    private Long           ordId;
    private Long           userId;
    private String         ordNo;
    private Integer        ordType;
    private java.util.Date beginDate;
    private java.util.Date endDate;
    private java.util.Date ordDate;
    private Integer        investAmount;         // 订单投资金额
    private Double         deductAmount;
    private Double         deductPerMonth;
    private Double         profit;
    private String         billingAddress;
    private String         cName;
    private String         cId;
    private Integer        ordStatus;
    private String         remark;
    private java.util.Date createTime;
    private java.util.Date updateTime;
    private Double         rechargeMoney;

    private Double         orderInterestAmount;  // 订单利息
    private Double         orderWithdrawalAmount; // 订单可提取金额
    // rate_type 选择的投资类型（1彩富人生I号 2 彩富人生II 3 彩富人生III）
    private String         rateType;
    // orderModel 订单类型(PropertyFees 欠费冲抵 PropertyActivity冲抵物业)
    private String         orderSubType;
    /**
     * 产品利率
     */
    private Double 	rate; 	

    /**
     * 每月冲抵物业费/冲抵费用
     */
    private String deductPerMonthText;
    /**
     * 冲抵周期/冲抵时长
     */
    private String billingDateText;
    /**
     * 冲抵物业费地址/冲抵地址
     */
    private String billingAddressText;
    /**
     * 车牌号 (值为：空或者车牌号：粤GF4586)
     */
    private String carNumber;

    
    
//    /**
//     * 根据产品不同返回不同的利率
//     * 
//     * @return
//     * @author: zhangyunhmf
//     * @date: 2014年12月12日上午10:11:21
//     */
//    public double getRate() {
//        if (productType1.equals(this.rateType)) {
//            return 0.035d;
//        } else if (productType2.equals(this.rateType)) {
//            return 0.0275d;
//        } else if (productType3.equals(this.rateType)) {
//            return 0.0375d;
//        } else if (productType4.equals(this.rateType)) {
//            return 0.0375d;
//        }
//            return 0;
//    }
    
    
    // columns END
    public Long getOrdId() {
        return this.ordId;
    }

    public void setOrdId(Long value) {
        this.ordId = value;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long value) {
        this.userId = value;
    }

    public String getOrdNo() {
        return this.ordNo;
    }

    public void setOrdNo(String value) {
        this.ordNo = value;
    }

    public Integer getOrdType() {
        return this.ordType;
    }

    public void setOrdType(Integer value) {
        this.ordType = value;
    }

    public java.util.Date getBeginDate() {
        return this.beginDate;
    }

    public void setBeginDate(java.util.Date value) {
        this.beginDate = value;
    }

    public java.util.Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(java.util.Date value) {
        this.endDate = value;
    }

    public java.util.Date getOrdDate() {
        return this.ordDate;
    }

    public void setOrdDate(java.util.Date value) {
        this.ordDate = value;
    }

    public Integer getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(Integer investAmount) {
        this.investAmount = investAmount;
    }

    public Double getDeductAmount() {
        return this.deductAmount;
    }

    public void setDeductAmount(Double value) {
        this.deductAmount = value;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Integer getOrdStatus() {
        return this.ordStatus;
    }

    public void setOrdStatus(Integer value) {
        this.ordStatus = value;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String value) {
        this.remark = value;
    }

    public java.util.Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(java.util.Date value) {
        this.createTime = value;
    }

    public java.util.Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(java.util.Date value) {
        this.updateTime = value;
    }

    public Double getOrderInterestAmount() {
        return orderInterestAmount;
    }

    public void setOrderInterestAmount(Double orderInterestAmount) {
        this.orderInterestAmount = orderInterestAmount;
    }

    public Double getOrderWithdrawalAmount() {
        return orderWithdrawalAmount;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public void setOrderWithdrawalAmount(Double orderWithdrawalAmount) {
        this.orderWithdrawalAmount = orderWithdrawalAmount;
    }

    public Double getDeductPerMonth() {
        return deductPerMonth;
    }

    public void setDeductPerMonth(Double deductPerMonth) {
        this.deductPerMonth = deductPerMonth;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public Double getRechargeMoney() {
        return rechargeMoney;
    }

    public void setRechargeMoney(Double rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    /**
     * @return rateType
     */
    public String getRateType() {
        return rateType;
    }

    /**
     * @param rateType
     *            the rateType to set
     */
    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    /**
     * @return orderSubType
     */
    public String getOrderSubType() {
        return orderSubType;
    }

    /**
     * @param orderSubType
     *            the orderSubType to set
     */
    public void setOrderSubType(String orderSubType) {
        this.orderSubType = orderSubType;
    }

    
    
    public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String getDeductPerMonthText() {
		return deductPerMonthText;
	}

	public void setDeductPerMonthText(String deductPerMonthText) {
		this.deductPerMonthText = deductPerMonthText;
	}

	public String getBillingDateText() {
		return billingDateText;
	}

	public void setBillingDateText(String billingDateText) {
		this.billingDateText = billingDateText;
	}

	public String getBillingAddressText() {
		return billingAddressText;
	}

	public void setBillingAddressText(String billingAddressText) {
		this.billingAddressText = billingAddressText;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	@Override
    public String toString() {
        return "订单信息{" + "ordId=" + ordId + ", 用户id=" + userId + ", 彩生活订单号='" + ordNo + '\'' + ", 投资金额=" + investAmount
                + ", 缴费金额=" + deductAmount + ", 每月缴费金额=" + deductPerMonth + ", 收益=" + profit + ", 缴费地址='"
                + billingAddress + '\'' + ", 小区名称='" + cName + '\'' + ", 开始时间=" + beginDate + ", 结束时间=" + endDate
                + ", 下单日期=" + ordDate + ", cId='" + cId + '\'' + ", ordType=" + ordType + ", ordStatus=" + ordStatus
                + ", remark='" + remark + '\'' + ", createTime=" + createTime + ", updateTime=" + updateTime
                + ", orderInterestAmount=" + orderInterestAmount + ", orderWithdrawalAmount=" + orderWithdrawalAmount
                + ", rateType=" + rateType + ", orderSubType=" + orderSubType + '}';
    }

    public int hashCode() {
        return new HashCodeBuilder().append(getOrdId()).toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof ActivityOrderDo == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ActivityOrderDo other = (ActivityOrderDo) obj;
        return new EqualsBuilder().append(getOrdId(), other.getOrdId()).isEquals();
    }
}
