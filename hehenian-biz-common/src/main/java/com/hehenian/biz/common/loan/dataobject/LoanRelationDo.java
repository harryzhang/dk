/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.loan.dataobject
 * @Title: LoanRelationDo.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月19日 下午3:34:26
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.loan.dataobject;

import java.io.Serializable;
import java.util.Date;

import com.hehenian.biz.common.util.StringUtil;

/**
 * 
 * @author: liuzgmf
 * @date 2015年1月19日 下午3:34:26
 */
public class LoanRelationDo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long              ralationId;           // 联系人ID'
    private Long              loanId;               // 借款申请ID
    private Long              loanPersonId;         // 借款人ID
    private String            ralationName;         // 联系人姓名
    private String            relationship;         // 关系

    private String            mobile;               // 手机号码
    private Date              createTime;           // 创建日期
    private Date              updateTime;           // 最后修改日期
    private Integer     	  relationType;         //联系人类型
    private Long              statusId;         // 状态ID
    private int               statusInt;           // 状态INT
    private String            certificateTypeHead;  // 证件类型jsp头
    
    
    public Integer getRelationType() {
		return relationType;
	}

	public void setRelationType(Integer relationType) {
		this.relationType = relationType;
	}

	/**
     * @return ralationId
     */
    public Long getRalationId() {
        return ralationId;
    }

    /**
     * @param ralationId
     *            the ralationId to set
     */
    public void setRalationId(Long ralationId) {
        this.ralationId = ralationId;
    }

    /**
     * @return loanId
     */
    public Long getLoanId() {
        return loanId;
    }

    /**
     * @param loanId
     *            the loanId to set
     */
    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    /**
     * @return loanPersonId
     */
    public Long getLoanPersonId() {
        return loanPersonId;
    }

    /**
     * @param loanPersonId
     *            the loanPersonId to set
     */
    public void setLoanPersonId(Long loanPersonId) {
        this.loanPersonId = loanPersonId;
    }

    /**
     * @return ralationName
     */
    public String getRalationName() {
        return ralationName;
    }

    /**
     * @param ralationName
     *            the ralationName to set
     */
    public void setRalationName(String ralationName) {
    	
        this.ralationName = StringUtil.filterDangerString(ralationName);
    }

    /**
     * @return relationship
     */
    public String getRelationship() {
        return relationship;
    }

    /**
     * @param relationship
     *            the relationship to set
     */
    public void setRelationship(String relationship) {
        this.relationship = StringUtil.filterDangerString(relationship);
    }

    /**
     * @return mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile
     *            the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = StringUtil.filterDangerString(mobile);
    }

    /**
     * @return createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     *            the createTime to set
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     *            the updateTime to set
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public int getStatusInt() {
		return statusInt;
	}

	public void setStatusInt(int statusInt) {
		this.statusInt = statusInt;
	}

	public String getCertificateTypeHead() {
		return certificateTypeHead;
	}

	public void setCertificateTypeHead(String certificateTypeHead) {
		this.certificateTypeHead = certificateTypeHead;
	}

}
