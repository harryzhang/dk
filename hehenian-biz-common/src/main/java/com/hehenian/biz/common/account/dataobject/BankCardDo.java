package com.hehenian.biz.common.account.dataobject;

import java.io.Serializable;
import java.util.Date;

public class BankCardDo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long Id;// 编号
	private String cardUserName;// 用户名
	private String bankName;// 开户行
	private String branchBankName;// 支行
	private String cardNo;// 卡号

	private Integer cardMode;// 卡类，根据系统提供的卡类项进行多个设置
	private String modifiedCardNo;// 变更后的银行卡号
	private String modifiedBankName;// 变更后的银行卡开户行
	private String modifiedBranchBankName;// 变更后的银行卡开户支行
	private Date commitTime;// 申请时间

	private Date modifiedTime;// 更换时间
	private Integer modifiedCardStatus;// 变更后的银行卡状态（1 成功 2正在审核 3 失败）
	private Integer cardStatus;// 卡状态( 默认 2申请中) 1 已绑定 2 申请中 3 失败
	private Date checkTime;// 审核时间
	private Long userId;// 用户id

	private Long checkUser;// 审核人
	private String remark;// 审核意见
	private String province;// 省份
	private String city;// 地市
	private String openBankId;// 银行代号

	private String modifiedOpenBankId;// 变更后银行代号
	private String provinceId;// 省份代号
	private String cityId;// 地区代号
	private Integer isDefault;// 是否为默认的取现银行卡,(1是,0不是)
	private Integer isExpress;//快捷充值卡标示(1是,0不是)

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getCardUserName() {
		return cardUserName;
	}

	public void setCardUserName(String cardUserName) {
		this.cardUserName = cardUserName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchBankName() {
		return branchBankName;
	}

	public void setBranchBankName(String branchBankName) {
		this.branchBankName = branchBankName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Integer getCardMode() {
		return cardMode;
	}

	public void setCardMode(Integer cardMode) {
		this.cardMode = cardMode;
	}

	public String getModifiedCardNo() {
		return modifiedCardNo;
	}

	public void setModifiedCardNo(String modifiedCardNo) {
		this.modifiedCardNo = modifiedCardNo;
	}

	public String getModifiedBankName() {
		return modifiedBankName;
	}

	public void setModifiedBankName(String modifiedBankName) {
		this.modifiedBankName = modifiedBankName;
	}

	public String getModifiedBranchBankName() {
		return modifiedBranchBankName;
	}

	public void setModifiedBranchBankName(String modifiedBranchBankName) {
		this.modifiedBranchBankName = modifiedBranchBankName;
	}

	public Date getCommitTime() {
		return commitTime;
	}

	public void setCommitTime(Date commitTime) {
		this.commitTime = commitTime;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public Integer getModifiedCardStatus() {
		return modifiedCardStatus;
	}

	public void setModifiedCardStatus(Integer modifiedCardStatus) {
		this.modifiedCardStatus = modifiedCardStatus;
	}

	public Integer getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(Integer cardStatus) {
		this.cardStatus = cardStatus;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(Long checkUser) {
		this.checkUser = checkUser;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getOpenBankId() {
		return openBankId;
	}

	public void setOpenBankId(String openBankId) {
		this.openBankId = openBankId;
	}

	public String getModifiedOpenBankId() {
		return modifiedOpenBankId;
	}

	public void setModifiedOpenBankId(String modifiedOpenBankId) {
		this.modifiedOpenBankId = modifiedOpenBankId;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

    /** 
     * @return isExpress 
     */
    public Integer getIsExpress() {
        return isExpress;
    }

    /**
     * @param isExpress the isExpress to set
     */
    public void setIsExpress(Integer isExpress) {
        this.isExpress = isExpress;
    }
	
	

}
