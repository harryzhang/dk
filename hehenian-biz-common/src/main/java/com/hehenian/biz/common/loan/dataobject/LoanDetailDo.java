/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.loan.dataobject
 * @Title: LoanDetailDo.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2014年12月10日 下午6:47:28
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.loan.dataobject;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.account.dataobject.BankCardDo;
import com.hehenian.biz.common.account.dataobject.PersonDo;
import com.hehenian.biz.common.trade.dataobject.BorrowDo;
import com.hehenian.biz.common.util.StringUtil;

/**
 * 
 * @author: liuzgmf
 * @date 2014年12月10日 下午6:47:28
 */
public class LoanDetailDo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long              loanId;               // 借款ID
    private Long              userId;               // 用户ID
    private String            realName;             // 真实姓名
    private String            idNo;                 // 身份证号
    private String            mobile;               // 手机号码

    private Double            income;               // 月收入
    private LoanUsage         loanUsage;            // 贷款用途
    private String            loanUsageDesc;        // 用途描叙
    private Integer           loanPeriod;           // 借款期限
    private Double            loanAmount;           // 借款金额

    private String            checkDesc;            // 校验描叙
    private String            remark;               // 备注
    private LoanStatus        loanStatus;           // 借款状态
    private Date              createTime;           // 创建日期
    private Date              updateTime;           // 最后修改日期

    private AccountUserDo     userDo;               // 借款人信息
    private PersonDo          personDo;             // 借款详细信息
    private BorrowDo          borrowDo;             // 借款标的信息
    private BankCardDo        bankCardDo;           // 银行卡信息

    /** 贷款用途（CONSUME-消费，OTHER-其他） */
    public enum LoanUsage {
        CONSUME, OTHER;
    }

    /** 借款状态（PROCESSING-申请中，CHECKED-校验通过，UNCHECKED-校验不通过，LOANS-放款） PENDING-待处理，AUDITED-已审核，TREATY-已签约，SUBJECTED 已上标 REPAYING还款中  REPAYED已还清 
     * ，NOPASS-拒绝*/
    public enum LoanStatus {
        PROCESSING, CHECKED, UNCHECKED, LOANS,PENDING, AUDITED, TREATY, SUBJECTED, NOPASS ,REPAYING ,REPAYED;
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
     * @return realName
     */
    public String getRealName() {
        return realName;
    }

    /**
     * @param realName
     *            the realName to set
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * @return idNo
     */
    public String getIdNo() {
        return idNo;
    }

    /**
     * @param idNo
     *            the idNo to set
     */
    public void setIdNo(String idNo) {
        this.idNo = idNo;
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
        this.mobile = mobile;
    }

    /**
     * @return income
     */
    public Double getIncome() {
        return income;
    }

    /**
     * @param income
     *            the income to set
     */
    public void setIncome(Double income) {
        this.income = income;
    }

    /**
     * @return loanUsage
     */
    public LoanUsage getLoanUsage() {
        return loanUsage;
    }

    /**
     * @param loanUsage
     *            the loanUsage to set
     */
    public void setLoanUsage(LoanUsage loanUsage) {
        this.loanUsage = loanUsage;
    }

    /**
     * @return loanUsageDesc
     */
    public String getLoanUsageDesc() {
        return loanUsageDesc;
    }

    /**
     * @param loanUsageDesc
     *            the loanUsageDesc to set
     */
    public void setLoanUsageDesc(String loanUsageDesc) {
        this.loanUsageDesc = StringUtil.filterDangerString(loanUsageDesc);
    }

    /**
     * @return loanPeriod
     */
    public Integer getLoanPeriod() {
        return loanPeriod;
    }

    /**
     * @param loanPeriod
     *            the loanPeriod to set
     */
    public void setLoanPeriod(Integer loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    /**
     * @return loanAmount
     */
    public Double getLoanAmount() {
        return loanAmount;
    }

    /**
     * @param loanAmount
     *            the loanAmount to set
     */
    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    /**
     * @return checkDesc
     */
    public String getCheckDesc() {
        return checkDesc;
    }

    /**
     * @param checkDesc
     *            the checkDesc to set
     */
    public void setCheckDesc(String checkDesc) {
        this.checkDesc = checkDesc;
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     *            the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return loanStatus
     */
    public LoanStatus getLoanStatus() {
        return loanStatus;
    }

    /**
     * @param loanStatus
     *            the loanStatus to set
     */
    public void setLoanStatus(LoanStatus loanStatus) {
        this.loanStatus = loanStatus;
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

    /**
     * @return userDo
     */
    public AccountUserDo getUserDo() {
        return userDo;
    }

    /**
     * @param userDo
     *            the userDo to set
     */
    public void setUserDo(AccountUserDo userDo) {
        this.userDo = userDo;
    }

    /**
     * @return personDo
     */
    public PersonDo getPersonDo() {
        return personDo;
    }

    /**
     * @param personDo
     *            the personDo to set
     */
    public void setPersonDo(PersonDo personDo) {
        this.personDo = personDo;
    }

    /**
     * @return borrowDo
     */
    public BorrowDo getBorrowDo() {
        return borrowDo;
    }

    /**
     * @param borrowDo
     *            the borrowDo to set
     */
    public void setBorrowDo(BorrowDo borrowDo) {
        this.borrowDo = borrowDo;
    }

    /**
     * @return bankCardDo
     */
    public BankCardDo getBankCardDo() {
        return bankCardDo;
    }

    /**
     * @param bankCardDo
     *            the bankCardDo to set
     */
    public void setBankCardDo(BankCardDo bankCardDo) {
        this.bankCardDo = bankCardDo;
    }

    /**
     * @return sex
     */
    public String getSex() {
        if (StringUtils.isBlank(idNo) || (idNo.length() != 15 && idNo.length() != 18)) {
            return "";
        }
        if (this.idNo.length() == 15) {
            return (this.idNo.charAt(13) % 2 == 0 ? "女" : "男");
        }
        if (this.idNo.length() == 18) {
            return (this.idNo.charAt(16) % 2 == 0 ? "女" : "男");
        }
        return "";
    }

    /**
     * @return age
     */
    public Integer getAge() {
        if (this.idNo.length() == 15) {
            int date = 1900 + Integer.parseInt(idNo.substring(6, 8));
            return Integer.parseInt(DateFormatUtils.format(new Date(), "yyyy")) - date;
        }
        if (this.idNo.length() == 18) {
            int date = Integer.parseInt(idNo.substring(6, 10));
            return Integer.parseInt(DateFormatUtils.format(new Date(), "yyyy")) - date;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "LoanDetailDo [loanId=" + loanId + ", userId=" + userId + ", realName=" + realName + ", idNo=" + idNo
                + ", mobile=" + mobile + ", income=" + income + ", loanUsage=" + loanUsage + ", loanUsageDesc="
                + loanUsageDesc + ", loanPeriod=" + loanPeriod + ", loanAmount=" + loanAmount + ", checkDesc="
                + checkDesc + ", remark=" + remark + ", loanStatus=" + loanStatus + ", createTime=" + createTime
                + ", updateTime=" + updateTime + "]";
    }

}
