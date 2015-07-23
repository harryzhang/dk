package com.hehenian.biz.common.trade.dataobject;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.hehenian.biz.common.account.dataobject.AccountUserDo;
import com.hehenian.biz.common.account.dataobject.PersonDo;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */

public class BorrowDo implements java.io.Serializable {

    // columns START
    private java.lang.Long    id;
    private java.lang.String  borrowTitle;
    private java.lang.Integer borrowStatus;
    private java.lang.String  imgPath;
    private java.lang.Integer borrowWay;
    private java.lang.String  borrowInfo;
    private java.lang.Integer deadline;
    private java.lang.Integer paymentMode;
    private Double            borrowAmount;
    private Double            annualRate;
    private Double            minTenderedSum;
    private Double            maxTenderedSum;
    private java.lang.Integer raiseTerm;
    private java.lang.String  detail;
    private java.lang.Integer visitors;
    private java.util.Date    remainTimeStart;
    private java.lang.Integer tradeType;
    private java.lang.String  auditOpinion;
    private java.lang.Long    publisher;
    private java.lang.Integer excitationType;
    private Double            excitationSum;
    private java.lang.Integer excitationMode;
    private Double            hasInvestAmount;
    private java.lang.Integer investNum;
    private java.lang.Integer purpose;
    private java.lang.Integer hasPwd;
    private java.lang.String  investPwd;
    private java.util.Date    publishTime;
    private java.lang.String  publishIp;
    private java.util.Date    remainTimeEnd;
    private java.util.Date    auditTime;
    private java.lang.Integer hasDeadline;
    private java.lang.Integer isAutoBid;
    private Double            manageFee;
    private java.lang.Integer isDayThe;
    private java.util.Date    autoBidEnableTime;
    private java.lang.Integer version;
    private Double            frozenMargin;
    private Double            smallestFlowUnit;
    private java.lang.Integer circulationNumber;
    private java.lang.Integer hasCirculationNumber;
    private java.lang.String  nidLog;
    private java.lang.Integer sort;
    private java.lang.String  feestate;
    private java.lang.String  feelog;
    private java.lang.String  businessDetail;
    private java.lang.String  assets;
    private java.lang.String  moneyPurposes;
    private java.lang.Integer circulationMode;
    private java.lang.Integer circulationStatus;
    private java.lang.Long    undertaker;
    private java.lang.Integer borrowShow;
    private java.lang.Integer hasRepoNumber;
    private java.lang.String  agent;
    private java.lang.String  counterAgent;
    private Double            amountScale;
    private java.lang.String  windControl;
    private java.lang.String  firstTrial;
    private java.lang.String  recheck;
    private java.lang.String  number;
    private java.lang.Integer isTimes;
    private java.lang.Integer maxInvest;
    private java.lang.Integer guaranteeId;
    private java.lang.Integer loansOk;
    private java.lang.Integer unfreeOk;
    private java.lang.String  borrowadvisory;
    private java.lang.String  advisorybranch;
    private java.lang.Integer borrowGroup;
    private Double            loanAnnualRate;      // 借款年利率

    private AccountUserDo     userDo;              // 借款人
    private PersonDo          personDo;            // 借款人信息

    // columns END
    public java.lang.Long getId() {
        return this.id;
    }

    public void setId(java.lang.Long value) {
        this.id = value;
    }

    public java.lang.String getBorrowTitle() {
        return this.borrowTitle;
    }

    public void setBorrowTitle(java.lang.String value) {
        this.borrowTitle = value;
    }

    public java.lang.Integer getBorrowStatus() {
        return this.borrowStatus;
    }

    public void setBorrowStatus(java.lang.Integer value) {
        this.borrowStatus = value;
    }

    public java.lang.String getImgPath() {
        return this.imgPath;
    }

    public void setImgPath(java.lang.String value) {
        this.imgPath = value;
    }

    public java.lang.Integer getBorrowWay() {
        return this.borrowWay;
    }

    public void setBorrowWay(java.lang.Integer value) {
        this.borrowWay = value;
    }

    public java.lang.String getBorrowInfo() {
        return this.borrowInfo;
    }

    public void setBorrowInfo(java.lang.String value) {
        this.borrowInfo = value;
    }

    public java.lang.Integer getDeadline() {
        return this.deadline;
    }

    public void setDeadline(java.lang.Integer value) {
        this.deadline = value;
    }

    public java.lang.Integer getPaymentMode() {
        return this.paymentMode;
    }

    public void setPaymentMode(java.lang.Integer value) {
        this.paymentMode = value;
    }

    public Double getBorrowAmount() {
        return this.borrowAmount;
    }

    public void setBorrowAmount(Double value) {
        this.borrowAmount = value;
    }

    public Double getAnnualRate() {
        return this.annualRate;
    }

    public void setAnnualRate(Double value) {
        this.annualRate = value;
    }

    public Double getMinTenderedSum() {
        return this.minTenderedSum;
    }

    public void setMinTenderedSum(Double value) {
        this.minTenderedSum = value;
    }

    public Double getMaxTenderedSum() {
        return this.maxTenderedSum;
    }

    public void setMaxTenderedSum(Double value) {
        this.maxTenderedSum = value;
    }

    public java.lang.Integer getRaiseTerm() {
        return this.raiseTerm;
    }

    public void setRaiseTerm(java.lang.Integer value) {
        this.raiseTerm = value;
    }

    public java.lang.String getDetail() {
        return this.detail;
    }

    public void setDetail(java.lang.String value) {
        this.detail = value;
    }

    public java.lang.Integer getVisitors() {
        return this.visitors;
    }

    public void setVisitors(java.lang.Integer value) {
        this.visitors = value;
    }

    public java.util.Date getRemainTimeStart() {
        return this.remainTimeStart;
    }

    public void setRemainTimeStart(java.util.Date value) {
        this.remainTimeStart = value;
    }

    public java.lang.Integer getTradeType() {
        return this.tradeType;
    }

    public void setTradeType(java.lang.Integer value) {
        this.tradeType = value;
    }

    public java.lang.String getAuditOpinion() {
        return this.auditOpinion;
    }

    public void setAuditOpinion(java.lang.String value) {
        this.auditOpinion = value;
    }

    public java.lang.Long getPublisher() {
        return this.publisher;
    }

    public void setPublisher(java.lang.Long value) {
        this.publisher = value;
    }

    public java.lang.Integer getExcitationType() {
        return this.excitationType;
    }

    public void setExcitationType(java.lang.Integer value) {
        this.excitationType = value;
    }

    public Double getExcitationSum() {
        return this.excitationSum;
    }

    public void setExcitationSum(Double value) {
        this.excitationSum = value;
    }

    public java.lang.Integer getExcitationMode() {
        return this.excitationMode;
    }

    public void setExcitationMode(java.lang.Integer value) {
        this.excitationMode = value;
    }

    public Double getHasInvestAmount() {
        return this.hasInvestAmount;
    }

    public void setHasInvestAmount(Double value) {
        this.hasInvestAmount = value;
    }

    public java.lang.Integer getInvestNum() {
        return this.investNum;
    }

    public void setInvestNum(java.lang.Integer value) {
        this.investNum = value;
    }

    public java.lang.Integer getPurpose() {
        return this.purpose;
    }

    public void setPurpose(java.lang.Integer value) {
        this.purpose = value;
    }

    public java.lang.Integer getHasPwd() {
        return this.hasPwd;
    }

    public void setHasPwd(java.lang.Integer value) {
        this.hasPwd = value;
    }

    public java.lang.String getInvestPwd() {
        return this.investPwd;
    }

    public void setInvestPwd(java.lang.String value) {
        this.investPwd = value;
    }

    public java.util.Date getPublishTime() {
        return this.publishTime;
    }

    public void setPublishTime(java.util.Date value) {
        this.publishTime = value;
    }

    public java.lang.String getPublishIp() {
        return this.publishIp;
    }

    public void setPublishIp(java.lang.String value) {
        this.publishIp = value;
    }

    public java.util.Date getRemainTimeEnd() {
        return this.remainTimeEnd;
    }

    public void setRemainTimeEnd(java.util.Date value) {
        this.remainTimeEnd = value;
    }

    public java.util.Date getAuditTime() {
        return this.auditTime;
    }

    public void setAuditTime(java.util.Date value) {
        this.auditTime = value;
    }

    public java.lang.Integer getHasDeadline() {
        return this.hasDeadline;
    }

    public void setHasDeadline(java.lang.Integer value) {
        this.hasDeadline = value;
    }

    public java.lang.Integer getIsAutoBid() {
        return this.isAutoBid;
    }

    public void setIsAutoBid(java.lang.Integer value) {
        this.isAutoBid = value;
    }

    public Double getManageFee() {
        return this.manageFee;
    }

    public void setManageFee(Double value) {
        this.manageFee = value;
    }

    public java.lang.Integer getIsDayThe() {
        return this.isDayThe;
    }

    public void setIsDayThe(java.lang.Integer value) {
        this.isDayThe = value;
    }

    public java.util.Date getAutoBidEnableTime() {
        return this.autoBidEnableTime;
    }

    public void setAutoBidEnableTime(java.util.Date value) {
        this.autoBidEnableTime = value;
    }

    public java.lang.Integer getVersion() {
        return this.version;
    }

    public void setVersion(java.lang.Integer value) {
        this.version = value;
    }

    public Double getFrozenMargin() {
        return this.frozenMargin;
    }

    public void setFrozenMargin(Double value) {
        this.frozenMargin = value;
    }

    public Double getSmallestFlowUnit() {
        return this.smallestFlowUnit;
    }

    public void setSmallestFlowUnit(Double value) {
        this.smallestFlowUnit = value;
    }

    public java.lang.Integer getCirculationNumber() {
        return this.circulationNumber;
    }

    public void setCirculationNumber(java.lang.Integer value) {
        this.circulationNumber = value;
    }

    public java.lang.Integer getHasCirculationNumber() {
        return this.hasCirculationNumber;
    }

    public void setHasCirculationNumber(java.lang.Integer value) {
        this.hasCirculationNumber = value;
    }

    public java.lang.String getNidLog() {
        return this.nidLog;
    }

    public void setNidLog(java.lang.String value) {
        this.nidLog = value;
    }

    public java.lang.Integer getSort() {
        return this.sort;
    }

    public void setSort(java.lang.Integer value) {
        this.sort = value;
    }

    public java.lang.String getFeestate() {
        return this.feestate;
    }

    public void setFeestate(java.lang.String value) {
        this.feestate = value;
    }

    public java.lang.String getFeelog() {
        return this.feelog;
    }

    public void setFeelog(java.lang.String value) {
        this.feelog = value;
    }

    public java.lang.String getBusinessDetail() {
        return this.businessDetail;
    }

    public void setBusinessDetail(java.lang.String value) {
        this.businessDetail = value;
    }

    public java.lang.String getAssets() {
        return this.assets;
    }

    public void setAssets(java.lang.String value) {
        this.assets = value;
    }

    public java.lang.String getMoneyPurposes() {
        return this.moneyPurposes;
    }

    public void setMoneyPurposes(java.lang.String value) {
        this.moneyPurposes = value;
    }

    public java.lang.Integer getCirculationMode() {
        return this.circulationMode;
    }

    public void setCirculationMode(java.lang.Integer value) {
        this.circulationMode = value;
    }

    public java.lang.Integer getCirculationStatus() {
        return this.circulationStatus;
    }

    public void setCirculationStatus(java.lang.Integer value) {
        this.circulationStatus = value;
    }

    public java.lang.Long getUndertaker() {
        return this.undertaker;
    }

    public void setUndertaker(java.lang.Long value) {
        this.undertaker = value;
    }

    public java.lang.Integer getBorrowShow() {
        return this.borrowShow;
    }

    public void setBorrowShow(java.lang.Integer value) {
        this.borrowShow = value;
    }

    public java.lang.Integer getHasRepoNumber() {
        return this.hasRepoNumber;
    }

    public void setHasRepoNumber(java.lang.Integer value) {
        this.hasRepoNumber = value;
    }

    public java.lang.String getAgent() {
        return this.agent;
    }

    public void setAgent(java.lang.String value) {
        this.agent = value;
    }

    public java.lang.String getCounterAgent() {
        return this.counterAgent;
    }

    public void setCounterAgent(java.lang.String value) {
        this.counterAgent = value;
    }

    public Double getAmountScale() {
        return this.amountScale;
    }

    public void setAmountScale(Double value) {
        this.amountScale = value;
    }

    public java.lang.String getWindControl() {
        return this.windControl;
    }

    public void setWindControl(java.lang.String value) {
        this.windControl = value;
    }

    public java.lang.String getFirstTrial() {
        return this.firstTrial;
    }

    public void setFirstTrial(java.lang.String value) {
        this.firstTrial = value;
    }

    public java.lang.String getRecheck() {
        return this.recheck;
    }

    public void setRecheck(java.lang.String value) {
        this.recheck = value;
    }

    public java.lang.String getNumber() {
        return this.number;
    }

    public void setNumber(java.lang.String value) {
        this.number = value;
    }

    public java.lang.Integer getIsTimes() {
        return this.isTimes;
    }

    public void setIsTimes(java.lang.Integer value) {
        this.isTimes = value;
    }

    public java.lang.Integer getMaxInvest() {
        return this.maxInvest;
    }

    public void setMaxInvest(java.lang.Integer value) {
        this.maxInvest = value;
    }

    public java.lang.Integer getGuaranteeId() {
        return this.guaranteeId;
    }

    public void setGuaranteeId(java.lang.Integer value) {
        this.guaranteeId = value;
    }

    public java.lang.Integer getLoansOk() {
        return this.loansOk;
    }

    public void setLoansOk(java.lang.Integer value) {
        this.loansOk = value;
    }

    public java.lang.Integer getUnfreeOk() {
        return this.unfreeOk;
    }

    public void setUnfreeOk(java.lang.Integer value) {
        this.unfreeOk = value;
    }

    public java.lang.String getBorrowadvisory() {
        return this.borrowadvisory;
    }

    public void setBorrowadvisory(java.lang.String value) {
        this.borrowadvisory = value;
    }

    public java.lang.String getAdvisorybranch() {
        return this.advisorybranch;
    }

    public void setAdvisorybranch(java.lang.String value) {
        this.advisorybranch = value;
    }

    public java.lang.Integer getBorrowGroup() {
        return this.borrowGroup;
    }

    public void setBorrowGroup(java.lang.Integer value) {
        this.borrowGroup = value;
    }

    /**
     * @return loanAnnualRate
     */
    public Double getLoanAnnualRate() {
        return loanAnnualRate;
    }

    /**
     * @param loanAnnualRate
     *            the loanAnnualRate to set
     */
    public void setLoanAnnualRate(Double loanAnnualRate) {
        this.loanAnnualRate = loanAnnualRate;
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

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("Id", getId())
                .append("BorrowTitle", getBorrowTitle()).append("BorrowStatus", getBorrowStatus())
                .append("ImgPath", getImgPath()).append("BorrowWay", getBorrowWay())
                .append("BorrowInfo", getBorrowInfo()).append("Deadline", getDeadline())
                .append("PaymentMode", getPaymentMode()).append("BorrowAmount", getBorrowAmount())
                .append("AnnualRate", getAnnualRate()).append("MinTenderedSum", getMinTenderedSum())
                .append("MaxTenderedSum", getMaxTenderedSum()).append("RaiseTerm", getRaiseTerm())
                .append("Detail", getDetail()).append("Visitors", getVisitors())
                .append("RemainTimeStart", getRemainTimeStart()).append("TradeType", getTradeType())
                .append("AuditOpinion", getAuditOpinion()).append("Publisher", getPublisher())
                .append("ExcitationType", getExcitationType()).append("ExcitationSum", getExcitationSum())
                .append("ExcitationMode", getExcitationMode()).append("HasInvestAmount", getHasInvestAmount())
                .append("InvestNum", getInvestNum()).append("Purpose", getPurpose()).append("HasPwd", getHasPwd())
                .append("InvestPwd", getInvestPwd()).append("PublishTime", getPublishTime())
                .append("PublishIp", getPublishIp()).append("RemainTimeEnd", getRemainTimeEnd())
                .append("AuditTime", getAuditTime()).append("HasDeadline", getHasDeadline())
                .append("IsAutoBid", getIsAutoBid()).append("ManageFee", getManageFee())
                .append("IsDayThe", getIsDayThe()).append("AutoBidEnableTime", getAutoBidEnableTime())
                .append("Version", getVersion()).append("FrozenMargin", getFrozenMargin())
                .append("SmallestFlowUnit", getSmallestFlowUnit()).append("CirculationNumber", getCirculationNumber())
                .append("HasCirculationNumber", getHasCirculationNumber()).append("NidLog", getNidLog())
                .append("Sort", getSort()).append("Feestate", getFeestate()).append("Feelog", getFeelog())
                .append("BusinessDetail", getBusinessDetail()).append("Assets", getAssets())
                .append("MoneyPurposes", getMoneyPurposes()).append("CirculationMode", getCirculationMode())
                .append("CirculationStatus", getCirculationStatus()).append("Undertaker", getUndertaker())
                .append("BorrowShow", getBorrowShow()).append("HasRepoNumber", getHasRepoNumber())
                .append("Agent", getAgent()).append("CounterAgent", getCounterAgent())
                .append("AmountScale", getAmountScale()).append("WindControl", getWindControl())
                .append("FirstTrial", getFirstTrial()).append("Recheck", getRecheck()).append("Number", getNumber())
                .append("IsTimes", getIsTimes()).append("MaxInvest", getMaxInvest())
                .append("GuaranteeId", getGuaranteeId()).append("LoansOk", getLoansOk())
                .append("UnfreeOk", getUnfreeOk()).append("Borrowadvisory", getBorrowadvisory())
                .append("Advisorybranch", getAdvisorybranch()).append("BorrowGroup", getBorrowGroup()).toString();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof BorrowDo == false)
            return false;
        if (this == obj)
            return true;
        BorrowDo other = (BorrowDo) obj;
        return new EqualsBuilder().append(getId(), other.getId()).isEquals();
    }
}
