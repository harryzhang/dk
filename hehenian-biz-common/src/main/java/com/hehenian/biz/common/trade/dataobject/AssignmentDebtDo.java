/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.trade.dataobject
 * @Title: AssignmentDebtDo.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年9月26日 上午11:19:29
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.trade.dataobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 债权转让申请信息
 * 
 * @author: liuzgmf
 * @date 2014年9月26日 上午11:19:29
 */
public class AssignmentDebtDo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long              id;                   // 债权转让ID
    private Long              parentId;             // 父级债权转让ID
    private Double            debtSum;              // 债权总额
    private Double            auctionBasePrice;     // 认购低价
    private Integer           debtLimit;            // 债权期限
    private Integer           auctionMode;          // 竞拍模式（1为明拍，2为暗拍）

    private Date              publishTime;          // 发布时间（管理员审核时间）
    private Integer           viewCount;            // 浏览量
    private String            details;              // 转让描述
    private Integer           auctionDays;          // 竞拍天数
    private Integer           debtStatus;           // 转让状态(1审核中，2为认购中，3认购成功，4，认购失败，5，撤销,6审核失败,7提前还款)

    private Long              borrowId;             // 借款Id
    private Long              alienatorId;          // 转让人
    private Long              auctionerId;          // 认购者Id
    private Double            auctionHighPrice;     // 认购价格
    private Date              auctionEndTime;       // 竞拍结束时间

    private Date              applyTime;            // 申请时间
    private Double            manageFee;            // 手续费
    private Long              investId;             // 投资ID
    
    private AuctionDebtDo     auctionDebtDo;        //认购对象

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return parentId
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     *            the parentId to set
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * @return debtSum
     */
    public Double getDebtSum() {
        return debtSum;
    }

    /**
     * @param debtSum
     *            the debtSum to set
     */
    public void setDebtSum(Double debtSum) {
        this.debtSum = debtSum;
    }

    /**
     * @return auctionBasePrice
     */
    public Double getAuctionBasePrice() {
        return auctionBasePrice;
    }

    /**
     * @param auctionBasePrice
     *            the auctionBasePrice to set
     */
    public void setAuctionBasePrice(Double auctionBasePrice) {
        this.auctionBasePrice = auctionBasePrice;
    }

    /**
     * @return debtLimit
     */
    public Integer getDebtLimit() {
        return debtLimit;
    }

    /**
     * @param debtLimit
     *            the debtLimit to set
     */
    public void setDebtLimit(Integer debtLimit) {
        this.debtLimit = debtLimit;
    }

    /**
     * @return auctionMode
     */
    public Integer getAuctionMode() {
        return auctionMode;
    }

    /**
     * @param auctionMode
     *            the auctionMode to set
     */
    public void setAuctionMode(Integer auctionMode) {
        this.auctionMode = auctionMode;
    }

    /**
     * @return publishTime
     */
    public Date getPublishTime() {
        return publishTime;
    }

    /**
     * @param publishTime
     *            the publishTime to set
     */
    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    /**
     * @return viewCount
     */
    public Integer getViewCount() {
        return viewCount;
    }

    /**
     * @param viewCount
     *            the viewCount to set
     */
    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    /**
     * @return details
     */
    public String getDetails() {
        return details;
    }

    /**
     * @param details
     *            the details to set
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * @return auctionDays
     */
    public Integer getAuctionDays() {
        return auctionDays;
    }

    /**
     * @param auctionDays
     *            the auctionDays to set
     */
    public void setAuctionDays(Integer auctionDays) {
        this.auctionDays = auctionDays;
    }

    /**
     * @return debtStatus
     */
    public Integer getDebtStatus() {
        return debtStatus;
    }

    /**
     * @param debtStatus
     *            the debtStatus to set
     */
    public void setDebtStatus(Integer debtStatus) {
        this.debtStatus = debtStatus;
    }

    /**
     * @return borrowId
     */
    public Long getBorrowId() {
        return borrowId;
    }

    /**
     * @param borrowId
     *            the borrowId to set
     */
    public void setBorrowId(Long borrowId) {
        this.borrowId = borrowId;
    }

    /**
     * @return alienatorId
     */
    public Long getAlienatorId() {
        return alienatorId;
    }

    /**
     * @param alienatorId
     *            the alienatorId to set
     */
    public void setAlienatorId(Long alienatorId) {
        this.alienatorId = alienatorId;
    }

    /**
     * @return auctionerId
     */
    public Long getAuctionerId() {
        return auctionerId;
    }

    /**
     * @param auctionerId
     *            the auctionerId to set
     */
    public void setAuctionerId(Long auctionerId) {
        this.auctionerId = auctionerId;
    }

    /**
     * @return auctionHighPrice
     */
    public Double getAuctionHighPrice() {
        return auctionHighPrice;
    }

    /**
     * @param auctionHighPrice
     *            the auctionHighPrice to set
     */
    public void setAuctionHighPrice(Double auctionHighPrice) {
        this.auctionHighPrice = auctionHighPrice;
    }

    /**
     * @return auctionEndTime
     */
    public Date getAuctionEndTime() {
        return auctionEndTime;
    }

    /**
     * @param auctionEndTime
     *            the auctionEndTime to set
     */
    public void setAuctionEndTime(Date auctionEndTime) {
        this.auctionEndTime = auctionEndTime;
    }

    /**
     * @return applyTime
     */
    public Date getApplyTime() {
        return applyTime;
    }

    /**
     * @param applyTime
     *            the applyTime to set
     */
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    /**
     * @return manageFee
     */
    public Double getManageFee() {
        return manageFee;
    }

    /**
     * @param manageFee
     *            the manageFee to set
     */
    public void setManageFee(Double manageFee) {
        this.manageFee = manageFee;
    }

    /**
     * @return investId
     */
    public Long getInvestId() {
        return investId;
    }

    /**
     * @param investId
     *            the investId to set
     */
    public void setInvestId(Long investId) {
        this.investId = investId;
    }


    /** 
     * @return auctionDebtDo 
     */
    public AuctionDebtDo getAuctionDebtDo() {
        return auctionDebtDo;
    }

    /**
     * 设置认购对象
     * @param auctionDebtDo the auctionDebtDo to set
     */
    public void setAuctionDebtDo(AuctionDebtDo auctionDebtDo) {
        this.auctionDebtDo = auctionDebtDo;
    }

}
