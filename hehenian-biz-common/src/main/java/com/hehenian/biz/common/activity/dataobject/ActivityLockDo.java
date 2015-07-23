/*
 * Powered By zhangyunhua
 * Web Site: http://www.hehenian.com
 * Since 2008 - 2014
 */

package com.hehenian.biz.common.activity.dataobject;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

public class ActivityLockDo implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private Long              lockId;
    private Long              ordId;
    private Long              userId;
    private Double            amount;
    private Integer           lockType;
    private Integer           status;
    private String            remark;
    private java.util.Date    createTime;
    private java.util.Date    updateTime;

    // columns END
    public Long getLockId() {
        return this.lockId;
    }

    public void setLockId(Long value) {
        this.lockId = value;
    }

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

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double value) {
        this.amount = value;
    }

    public Integer getLockType() {
        return this.lockType;
    }

    public void setLockType(Integer value) {
        this.lockType = value;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer value) {
        this.status = value;
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

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("LockId", getLockId())
                .append("OrdId", getOrdId()).append("UserId", getUserId()).append("Amount", getAmount())
                .append("LockType", getLockType()).append("Status", getStatus()).append("Remark", getRemark())
                .append("CreateTime", getCreateTime()).append("UpdateTime", getUpdateTime()).toString();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(getLockId()).toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof ActivityLockDo == false)
            return false;
        if (this == obj)
            return true;
        ActivityLockDo other = (ActivityLockDo) obj;
        return new EqualsBuilder().append(getLockId(), other.getLockId()).isEquals();
    }
}
