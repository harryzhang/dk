package com.hehenian.biz.common.trade.dataobject;

import java.io.Serializable;
import java.util.Date;

/**
 * User: liuwtmf Date: 2014/9/23 Time: 11:02
 */
public class RechargeDo implements Serializable {
    private long   id;            // ID
    private long   userId;        // 充值用户id
    private Date   rechargeTime;  // 充值时间
    private double rechargeMoney; // 充值金额
    private double cost;          // 费用
    private int    result;        // 结果 0，未成功 1，已成功
    private String paynumber;     // 充值汇付流水号
    private String bankName;      // 充值银行号
    private String gateBusiId;    // 充值网关
    private int    versoin;       // 版本
    private String info;          // 附加信息

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getVersoin() {
        return versoin;
    }

    public void setVersoin(int versoin) {
        this.versoin = versoin;
    }

    public String getGateBusiId() {
        return gateBusiId;
    }

    public void setGateBusiId(String gateBusiId) {
        this.gateBusiId = gateBusiId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(Date rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    public double getRechargeMoney() {
        return rechargeMoney;
    }

    public void setRechargeMoney(double rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getPaynumber() {
        return paynumber;
    }

    public void setPaynumber(String paynumber) {
        this.paynumber = paynumber;
    }
}
