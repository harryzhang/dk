package com.hehenian.biz.common.account.dataobject;

import java.io.Serializable;
import java.util.Date;

/**
 * User: liuwtmf
 * Date: 2014/11/28
 * Time: 9:11
 */
public class UserBindDo implements Serializable {
    private Long    id;
    private Long    userId;
    private String  partnerUserId;
    private Integer partnerId;
    private Integer status;
    private Date    createTime;

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

    public String getPartnerUserId() {
        return partnerUserId;
    }

    public void setPartnerUserId(String partnerUserId) {
        this.partnerUserId = partnerUserId;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
