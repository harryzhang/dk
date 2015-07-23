/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.loan.dataobject
 * @Title: CreditDo.java
 * @Description: TODO
 * @author: liuzgmf
 * @date 2015年4月20日 上午11:29:53
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0  
 */
package com.hehenian.biz.common.loan.dataobject;

import java.io.Serializable;
import java.util.Date;

import com.hehenian.biz.common.system.dataobject.SettSchemeDo.SettleWay;

/**
 * 小贷系统的借款标的对象
 * 
 * @author: liuzgmf
 * @date 2015年4月20日 上午11:29:53
 */
public class LoanInfoDo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long              loanInfoId;           // 借款ID
    private String            realName;             // 客户名称
    private String            sex;                  // 性别
    private Integer           age;                  // 年龄
    private String            marriaged;            // 婚姻状况

    private String            residenceType;        // 居住状况
    private String            education;            // 学历
    private Integer           creditNum;            // 信用卡总张数
    private Double            creditAmt;            // 信用卡总额度
    private String            idNo;                 // 身份证号码

    private Integer           carQty;               // 车辆总数量
    private String            companyName;          // 公司名称
    private String            companyAddress;       // 公司地址
    private String            position;             // 职位级别
    private String            companyType;          // 企业性质

    private String            workYear;             // 现公司工作年限
    private String            industry;             // 公司行业
    private Double            income;               // 月收入
    private Double            expense;              // 支出合计
    private Double            loanAmt;              // 借款金额

    private Integer           loanPeriod;           // 申请期限
    private String            productType;          // 产品类型
    private Integer           repayType;            // 还款方式
    private Double            loanAnnualRate;       // 借款年利率
    private Double            annualRate;           // 投资年利率
    private Integer           tenderDay;            // 筹标期限（天）

    private String            loanUsage;            // 贷款资金用途
    private String            consultant;           // 借款咨询方
    private String            consultantBranch;     // 咨询方分行
    private String            borrowGroup;          // 所属专区
    private String            businessNo;           // 业务编号

    private LoanStatus        loanStatus;           // 借款状态
    private String            remark;               // 备注
    private Date              createTime;           // 创建时间
    private Date              updateTime;           // 修改时间

    private SettleWay         repayWay;             // 还款方式

    /** 借款状态(CANCEL-作废,UNRELEASE-未发布,TOCHINAPNR-已发布资金托管平台,TODEPOSIT-已发布定存平台) */
    public enum LoanStatus {
        CANCEL, UNRELEASE, TOCHINAPNR, TODEPOSIT;
    }

    /**
     * @return loanInfoId
     */
    public Long getLoanInfoId() {
        return loanInfoId;
    }

    /**
     * @param loanInfoId
     *            the loanInfoId to set
     */
    public void setLoanInfoId(Long loanInfoId) {
        this.loanInfoId = loanInfoId;
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
     * @return sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex
     *            the sex to set
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age
     *            the age to set
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * @return marriaged
     */
    public String getMarriaged() {
        return marriaged;
    }

    /**
     * @param marriaged
     *            the marriaged to set
     */
    public void setMarriaged(String marriaged) {
        this.marriaged = marriaged;
    }

    /**
     * @return residenceType
     */
    public String getResidenceType() {
        return residenceType;
    }

    /**
     * @param residenceType
     *            the residenceType to set
     */
    public void setResidenceType(String residenceType) {
        this.residenceType = residenceType;
    }

    /**
     * @return education
     */
    public String getEducation() {
        return education;
    }

    /**
     * @param education
     *            the education to set
     */
    public void setEducation(String education) {
        this.education = education;
    }

    /**
     * @return creditNum
     */
    public Integer getCreditNum() {
        return creditNum;
    }

    /**
     * @param creditNum
     *            the creditNum to set
     */
    public void setCreditNum(Integer creditNum) {
        this.creditNum = creditNum;
    }

    /**
     * @return creditAmt
     */
    public Double getCreditAmt() {
        return creditAmt;
    }

    /**
     * @param creditAmt
     *            the creditAmt to set
     */
    public void setCreditAmt(Double creditAmt) {
        this.creditAmt = creditAmt;
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
     * @return carQty
     */
    public Integer getCarQty() {
        return carQty;
    }

    /**
     * @param carQty
     *            the carQty to set
     */
    public void setCarQty(Integer carQty) {
        this.carQty = carQty;
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
        this.companyName = companyName;
    }

    /**
     * @return companyAddress
     */
    public String getCompanyAddress() {
        return companyAddress;
    }

    /**
     * @param companyAddress
     *            the companyAddress to set
     */
    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
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
     * @return companyType
     */
    public String getCompanyType() {
        return companyType;
    }

    /**
     * @param companyType
     *            the companyType to set
     */
    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    /**
     * @return workYear
     */
    public String getWorkYear() {
        return workYear;
    }

    /**
     * @param workYear
     *            the workYear to set
     */
    public void setWorkYear(String workYear) {
        this.workYear = workYear;
    }

    /**
     * @return industry
     */
    public String getIndustry() {
        return industry;
    }

    /**
     * @param industry
     *            the industry to set
     */
    public void setIndustry(String industry) {
        this.industry = industry;
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
     * @return expense
     */
    public Double getExpense() {
        return expense;
    }

    /**
     * @param expense
     *            the expense to set
     */
    public void setExpense(Double expense) {
        this.expense = expense;
    }

    /**
     * @return loanAmt
     */
    public Double getLoanAmt() {
        return loanAmt;
    }

    /**
     * @param loanAmt
     *            the loanAmt to set
     */
    public void setLoanAmt(Double loanAmt) {
        this.loanAmt = loanAmt;
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
     * @return productType
     */
    public String getProductType() {
        return productType;
    }

    /**
     * @param productType
     *            the productType to set
     */
    public void setProductType(String productType) {
        this.productType = productType;
    }

    /**
     * @return repayType
     */
    public Integer getRepayType() {
        return repayType;
    }

    /**
     * @param repayType
     *            the repayType to set
     */
    public void setRepayType(Integer repayType) {
        this.repayType = repayType;
    }

    /**
     * @return annualRate
     */
    public Double getAnnualRate() {
        return annualRate;
    }

    /**
     * @param annualRate
     *            the annualRate to set
     */
    public void setAnnualRate(Double annualRate) {
        this.annualRate = annualRate;
    }

    /**
     * @return tenderDay
     */
    public Integer getTenderDay() {
        return tenderDay;
    }

    /**
     * @param tenderDay
     *            the tenderDay to set
     */
    public void setTenderDay(Integer tenderDay) {
        this.tenderDay = tenderDay;
    }

    /**
     * @return loanUsage
     */
    public String getLoanUsage() {
        return loanUsage;
    }

    /**
     * @param loanUsage
     *            the loanUsage to set
     */
    public void setLoanUsage(String loanUsage) {
        this.loanUsage = loanUsage;
    }

    /**
     * @return consultant
     */
    public String getConsultant() {
        return consultant;
    }

    /**
     * @param consultant
     *            the consultant to set
     */
    public void setConsultant(String consultant) {
        this.consultant = consultant;
    }

    /**
     * @return consultantBranch
     */
    public String getConsultantBranch() {
        return consultantBranch;
    }

    /**
     * @param consultantBranch
     *            the consultantBranch to set
     */
    public void setConsultantBranch(String consultantBranch) {
        this.consultantBranch = consultantBranch;
    }

    /**
     * @return borrowGroup
     */
    public String getBorrowGroup() {
        return borrowGroup;
    }

    /**
     * @param borrowGroup
     *            the borrowGroup to set
     */
    public void setBorrowGroup(String borrowGroup) {
        this.borrowGroup = borrowGroup;
    }

    /**
     * @return businessNo
     */
    public String getBusinessNo() {
        return businessNo;
    }

    /**
     * @param businessNo
     *            the businessNo to set
     */
    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
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

    public Double getLoanAnnualRate() {
        return loanAnnualRate;
    }

    public void setLoanAnnualRate(Double loanAnnualRate) {
        this.loanAnnualRate = loanAnnualRate;
    }

    /**
     * @return repayWay
     */
    public SettleWay getRepayWay() {
        return repayWay;
    }

    /**
     * @param repayWay
     *            the repayWay to set
     */
    public void setRepayWay(SettleWay repayWay) {
        this.repayWay = repayWay;
    }

}
