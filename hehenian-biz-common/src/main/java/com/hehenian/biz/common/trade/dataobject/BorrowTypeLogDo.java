package com.hehenian.biz.common.trade.dataobject;



import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author zhangyunhua
 * @version 1.0
 * @since 1.0
 */



public class BorrowTypeLogDo  implements java.io.Serializable{	
	
	//columns START
	private java.lang.Long id;
	private java.lang.String nid;
	private java.lang.Integer status;
	private java.lang.String name;
	private java.lang.String title;
	private java.lang.String description;
	private java.lang.Long accountMultiple;
	private java.lang.Integer passwordStatus;
	private java.lang.Integer amountType;
	private java.lang.Long amountFirst;
	private java.lang.Long amountEnd;
	private Double frostScaleVip;
	private Double aprFirst;
	private Double aprEnd;
	private java.lang.Integer checkFirst;
	private java.lang.Integer checkEnd;
	private java.lang.String tenderAccountMin;
	private java.lang.String tenderAccountMax;
	private java.lang.String periodMonth;
	private java.lang.String periodDay;
	private java.lang.String validate;
	private java.lang.Integer awardStatus;
	private Double awardScaleFirst;
	private Double awardScaleEnd;
	private Double awardAccountFirst;
	private Double awardAccountEnd;
	private java.lang.Integer subscribeStatus;
	private java.lang.Integer verifyAutoStatus;
	private java.lang.String verifyAutoRemark;
	private java.lang.String styles;
	private Double vipFrostScale;
	private java.lang.Integer lateDaysMonth;
	private java.lang.Integer lateDaysDay;
	private Double vipLateScale;
	private Double allLateScale;
	private Double allFrostScale;
	private java.lang.Long userId;
	private java.lang.Long updateTime;
	private java.lang.String updateIp;
	private java.lang.String identifier;
	private java.lang.String counterGuarantee;
	private java.lang.String institution;
	private Double locanFee;
	private java.lang.Integer locanMonth;
	private Double locanFeeMonth;
	private Double dayFee;
	//columns END
	public java.lang.Long getId() {
		return this.id;
	}
	
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	public java.lang.String getNid() {
		return this.nid;
	}
	
	public void setNid(java.lang.String value) {
		this.nid = value;
	}
	public java.lang.Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	public java.lang.String getName() {
		return this.name;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	public java.lang.String getTitle() {
		return this.title;
	}
	
	public void setTitle(java.lang.String value) {
		this.title = value;
	}
	public java.lang.String getDescription() {
		return this.description;
	}
	
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
	public java.lang.Long getAccountMultiple() {
		return this.accountMultiple;
	}
	
	public void setAccountMultiple(java.lang.Long value) {
		this.accountMultiple = value;
	}
	public java.lang.Integer getPasswordStatus() {
		return this.passwordStatus;
	}
	
	public void setPasswordStatus(java.lang.Integer value) {
		this.passwordStatus = value;
	}
	public java.lang.Integer getAmountType() {
		return this.amountType;
	}
	
	public void setAmountType(java.lang.Integer value) {
		this.amountType = value;
	}
	public java.lang.Long getAmountFirst() {
		return this.amountFirst;
	}
	
	public void setAmountFirst(java.lang.Long value) {
		this.amountFirst = value;
	}
	public java.lang.Long getAmountEnd() {
		return this.amountEnd;
	}
	
	public void setAmountEnd(java.lang.Long value) {
		this.amountEnd = value;
	}
	public Double getFrostScaleVip() {
		return this.frostScaleVip;
	}
	
	public void setFrostScaleVip(Double value) {
		this.frostScaleVip = value;
	}
	public Double getAprFirst() {
		return this.aprFirst;
	}
	
	public void setAprFirst(Double value) {
		this.aprFirst = value;
	}
	public Double getAprEnd() {
		return this.aprEnd;
	}
	
	public void setAprEnd(Double value) {
		this.aprEnd = value;
	}
	public java.lang.Integer getCheckFirst() {
		return this.checkFirst;
	}
	
	public void setCheckFirst(java.lang.Integer value) {
		this.checkFirst = value;
	}
	public java.lang.Integer getCheckEnd() {
		return this.checkEnd;
	}
	
	public void setCheckEnd(java.lang.Integer value) {
		this.checkEnd = value;
	}
	public java.lang.String getTenderAccountMin() {
		return this.tenderAccountMin;
	}
	
	public void setTenderAccountMin(java.lang.String value) {
		this.tenderAccountMin = value;
	}
	public java.lang.String getTenderAccountMax() {
		return this.tenderAccountMax;
	}
	
	public void setTenderAccountMax(java.lang.String value) {
		this.tenderAccountMax = value;
	}
	public java.lang.String getPeriodMonth() {
		return this.periodMonth;
	}
	
	public void setPeriodMonth(java.lang.String value) {
		this.periodMonth = value;
	}
	public java.lang.String getPeriodDay() {
		return this.periodDay;
	}
	
	public void setPeriodDay(java.lang.String value) {
		this.periodDay = value;
	}
	public java.lang.String getValidate() {
		return this.validate;
	}
	
	public void setValidate(java.lang.String value) {
		this.validate = value;
	}
	public java.lang.Integer getAwardStatus() {
		return this.awardStatus;
	}
	
	public void setAwardStatus(java.lang.Integer value) {
		this.awardStatus = value;
	}
	public Double getAwardScaleFirst() {
		return this.awardScaleFirst;
	}
	
	public void setAwardScaleFirst(Double value) {
		this.awardScaleFirst = value;
	}
	public Double getAwardScaleEnd() {
		return this.awardScaleEnd;
	}
	
	public void setAwardScaleEnd(Double value) {
		this.awardScaleEnd = value;
	}
	public Double getAwardAccountFirst() {
		return this.awardAccountFirst;
	}
	
	public void setAwardAccountFirst(Double value) {
		this.awardAccountFirst = value;
	}
	public Double getAwardAccountEnd() {
		return this.awardAccountEnd;
	}
	
	public void setAwardAccountEnd(Double value) {
		this.awardAccountEnd = value;
	}
	public java.lang.Integer getSubscribeStatus() {
		return this.subscribeStatus;
	}
	
	public void setSubscribeStatus(java.lang.Integer value) {
		this.subscribeStatus = value;
	}
	public java.lang.Integer getVerifyAutoStatus() {
		return this.verifyAutoStatus;
	}
	
	public void setVerifyAutoStatus(java.lang.Integer value) {
		this.verifyAutoStatus = value;
	}
	public java.lang.String getVerifyAutoRemark() {
		return this.verifyAutoRemark;
	}
	
	public void setVerifyAutoRemark(java.lang.String value) {
		this.verifyAutoRemark = value;
	}
	public java.lang.String getStyles() {
		return this.styles;
	}
	
	public void setStyles(java.lang.String value) {
		this.styles = value;
	}
	public Double getVipFrostScale() {
		return this.vipFrostScale;
	}
	
	public void setVipFrostScale(Double value) {
		this.vipFrostScale = value;
	}
	public java.lang.Integer getLateDaysMonth() {
		return this.lateDaysMonth;
	}
	
	public void setLateDaysMonth(java.lang.Integer value) {
		this.lateDaysMonth = value;
	}
	public java.lang.Integer getLateDaysDay() {
		return this.lateDaysDay;
	}
	
	public void setLateDaysDay(java.lang.Integer value) {
		this.lateDaysDay = value;
	}
	public Double getVipLateScale() {
		return this.vipLateScale;
	}
	
	public void setVipLateScale(Double value) {
		this.vipLateScale = value;
	}
	public Double getAllLateScale() {
		return this.allLateScale;
	}
	
	public void setAllLateScale(Double value) {
		this.allLateScale = value;
	}
	public Double getAllFrostScale() {
		return this.allFrostScale;
	}
	
	public void setAllFrostScale(Double value) {
		this.allFrostScale = value;
	}
	public java.lang.Long getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.Long value) {
		this.userId = value;
	}
	public java.lang.Long getUpdateTime() {
		return this.updateTime;
	}
	
	public void setUpdateTime(java.lang.Long value) {
		this.updateTime = value;
	}
	public java.lang.String getUpdateIp() {
		return this.updateIp;
	}
	
	public void setUpdateIp(java.lang.String value) {
		this.updateIp = value;
	}
	public java.lang.String getIdentifier() {
		return this.identifier;
	}
	
	public void setIdentifier(java.lang.String value) {
		this.identifier = value;
	}
	public java.lang.String getCounterGuarantee() {
		return this.counterGuarantee;
	}
	
	public void setCounterGuarantee(java.lang.String value) {
		this.counterGuarantee = value;
	}
	public java.lang.String getInstitution() {
		return this.institution;
	}
	
	public void setInstitution(java.lang.String value) {
		this.institution = value;
	}
	public Double getLocanFee() {
		return this.locanFee;
	}
	
	public void setLocanFee(Double value) {
		this.locanFee = value;
	}
	public java.lang.Integer getLocanMonth() {
		return this.locanMonth;
	}
	
	public void setLocanMonth(java.lang.Integer value) {
		this.locanMonth = value;
	}
	public Double getLocanFeeMonth() {
		return this.locanFeeMonth;
	}
	
	public void setLocanFeeMonth(Double value) {
		this.locanFeeMonth = value;
	}
	public Double getDayFee() {
		return this.dayFee;
	}
	
	public void setDayFee(Double value) {
		this.dayFee = value;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Nid",getNid())
			.append("Status",getStatus())
			.append("Name",getName())
			.append("Title",getTitle())
			.append("Description",getDescription())
			.append("AccountMultiple",getAccountMultiple())
			.append("PasswordStatus",getPasswordStatus())
			.append("AmountType",getAmountType())
			.append("AmountFirst",getAmountFirst())
			.append("AmountEnd",getAmountEnd())
			.append("FrostScaleVip",getFrostScaleVip())
			.append("AprFirst",getAprFirst())
			.append("AprEnd",getAprEnd())
			.append("CheckFirst",getCheckFirst())
			.append("CheckEnd",getCheckEnd())
			.append("TenderAccountMin",getTenderAccountMin())
			.append("TenderAccountMax",getTenderAccountMax())
			.append("PeriodMonth",getPeriodMonth())
			.append("PeriodDay",getPeriodDay())
			.append("Validate",getValidate())
			.append("AwardStatus",getAwardStatus())
			.append("AwardScaleFirst",getAwardScaleFirst())
			.append("AwardScaleEnd",getAwardScaleEnd())
			.append("AwardAccountFirst",getAwardAccountFirst())
			.append("AwardAccountEnd",getAwardAccountEnd())
			.append("SubscribeStatus",getSubscribeStatus())
			.append("VerifyAutoStatus",getVerifyAutoStatus())
			.append("VerifyAutoRemark",getVerifyAutoRemark())
			.append("Styles",getStyles())
			.append("VipFrostScale",getVipFrostScale())
			.append("LateDaysMonth",getLateDaysMonth())
			.append("LateDaysDay",getLateDaysDay())
			.append("VipLateScale",getVipLateScale())
			.append("AllLateScale",getAllLateScale())
			.append("AllFrostScale",getAllFrostScale())
			.append("UserId",getUserId())
			.append("UpdateTime",getUpdateTime())
			.append("UpdateIp",getUpdateIp())
			.append("Identifier",getIdentifier())
			.append("CounterGuarantee",getCounterGuarantee())
			.append("Institution",getInstitution())
			.append("LocanFee",getLocanFee())
			.append("LocanMonth",getLocanMonth())
			.append("LocanFeeMonth",getLocanFeeMonth())
			.append("DayFee",getDayFee())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof BorrowTypeLogDo == false) return false;
		if(this == obj) return true;
		BorrowTypeLogDo other = (BorrowTypeLogDo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

