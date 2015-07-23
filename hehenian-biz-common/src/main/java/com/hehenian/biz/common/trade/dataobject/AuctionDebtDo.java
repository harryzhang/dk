/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.trade.dataobject
 * @Title: AuctionDebtDo.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年10月8日 下午3:35:10
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.trade.dataobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 债权转让认购信息
 * 
 * @author: liuzgmf
 * @date 2014年10月8日 下午3:35:10
 */
public class AuctionDebtDo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long              id;                   //
    private Long              userId;               // 竞拍者Id
    private Long              debtId;               // 债权转让Id
    private Date              auctionTime;          // 竞拍时间
    private Double            auctionPrice;         // 竞拍价格
    private Integer           autiontimes;          // 竞标次数（最多为2）
    private AuctionStatus     auctionStatus;        // 认购状态

    /** 认购状态 （PROCESSING-认购中, SUCCESS-认购成功, FAILURE-认购失败）*/
    public enum AuctionStatus {
        PROCESSING, SUCCESS, FAILURE;
    }

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
     * @return userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return debtId
     */
    public Long getDebtId() {
        return debtId;
    }

    /**
     * @param debtId
     *            the debtId to set
     */
    public void setDebtId(Long debtId) {
        this.debtId = debtId;
    }

    /**
     * @return auctionTime
     */
    public Date getAuctionTime() {
        return auctionTime;
    }

    /**
     * @param auctionTime
     *            the auctionTime to set
     */
    public void setAuctionTime(Date auctionTime) {
        this.auctionTime = auctionTime;
    }

    /**
     * @return auctionPrice
     */
    public Double getAuctionPrice() {
        return auctionPrice;
    }

    /**
     * @param auctionPrice
     *            the auctionPrice to set
     */
    public void setAuctionPrice(Double auctionPrice) {
        this.auctionPrice = auctionPrice;
    }

    /**
     * @return autiontimes
     */
    public Integer getAutiontimes() {
        return autiontimes;
    }

    /**
     * @param autiontimes
     *            the autiontimes to set
     */
    public void setAutiontimes(Integer autiontimes) {
        this.autiontimes = autiontimes;
    }

    /**
     * @return auctionStatus
     */
    public AuctionStatus getAuctionStatus() {
        return auctionStatus;
    }

    /**
     * @param auctionStatus
     *            the auctionStatus to set
     */
    public void setAuctionStatus(AuctionStatus auctionStatus) {
        this.auctionStatus = auctionStatus;
    }

}
