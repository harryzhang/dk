/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.loan.dataobject
 * @Title: JobDo.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年1月19日 下午3:18:37
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
 * @date 2015年1月19日 下午3:18:37
 */
public class JobDo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long              jobId;                // 职业ID
    private Long              loanId;               // 借款申请ID
    private Long              loanPersonId;         // 借款人ID
    private String            companyName;          // 工作单位
    private String            position;             // 职位

    private Integer           jobYear;              // 工作年限/营业时间
    private Double            jobIncome;            // 月收入/月营业额
    private String            companyPhone;         // 单位电话
    private JobType           jobType;              // 工作类型
    private Date              createTime;           // 创建日期
    private Date              updateTime;           // 最后修改日期

    private String 			  companyInTime;
    private String            companyAddr;
    private String            certNo;
    
    
    public String getCompanyInTime() {
		return companyInTime;
	}

	public void setCompanyInTime(String companyInTime) {
		this.companyInTime = companyInTime;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	

	public String getCompanyAddr() {
		return companyAddr;
	}

	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}

	/** 工作类型（SALARYMAN-工薪族、SELF_EMPLOYED-自雇人士、EMPLOYER-私营业主） */
    public enum JobType {
        SALARYMAN, SELF_EMPLOYED, EMPLOYER;
    }

    /**
     * @return jobId
     */
    public Long getJobId() {
        return jobId;
    }

    /**
     * @param jobId
     *            the jobId to set
     */
    public void setJobId(Long jobId) {
        this.jobId = jobId;
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
     * @return companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName
     *            the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName =StringUtil.filterDangerString( companyName);
    }

    /**
     * @return position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position
     *            the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * @return jobYear
     */
    public Integer getJobYear() {
        return jobYear;
    }

    /**
     * @param jobYear
     *            the jobYear to set
     */
    public void setJobYear(Integer jobYear) {
        this.jobYear = jobYear;
    }

    /**
     * @return jobIncome
     */
    public Double getJobIncome() {
        return jobIncome;
    }

    /**
     * @param jobIncome
     *            the jobIncome to set
     */
    public void setJobIncome(Double jobIncome) {
        this.jobIncome = jobIncome;
    }

    /**
     * @return companyPhone
     */
    public String getCompanyPhone() {
        return companyPhone;
    }

    /**
     * @param companyPhone
     *            the companyPhone to set
     */
    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = StringUtil.filterDangerString(companyPhone);
    }

    /**
     * @return jobType
     */
    public JobType getJobType() {
        return jobType;
    }

    /**
     * @param jobType
     *            the jobType to set
     */
    public void setJobType(JobType jobType) {
        this.jobType = jobType;
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

}
