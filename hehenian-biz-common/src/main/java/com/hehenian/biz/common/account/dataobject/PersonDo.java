package com.hehenian.biz.common.account.dataobject;

import java.io.Serializable;
import java.util.Date;

public class PersonDo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;// 编号
	private String realName;// 真实姓名
	private String cellPhone;// 电话号码
	private String sex;// 编号
	private Date birthday;// 编号

	private Integer age;// 年龄
	private String highestEdu;// 最高学历
	private Date eduStartDay;// 入学年份
	private String school;// 毕业院校
	private String maritalStatus;// 婚姻状况(已婚 未婚)

	private String hasChild;// 有无子女(有 无)
	private String hasHourse;// 是否有房(有 无)
	private String hasHousrseLoan;// 有无房贷(有 无)
	private String hasCar;// 是否有车 (有 无)
	private String hasCarLoan;// 有无车贷 (有 无)

	private Integer nativePlacePro;// 籍贯省份(默认为-1)
	private Integer nativePlaceCity;// 籍贯城市 (默认为-1)
	private Integer registedPlacePro;// 户口所在地省份(默认为-1)
	private Integer registedPlaceCity;// 户口所在地城市(默认为-1)
	private String address;// 居住地址

	private String telephone;// 居住电话
	private Long userId;// 用户id
	private String personalHead;// 个人头像
	private String bankCar;// 银行卡号
	private Integer bankCarStatus;// 银行卡审核状态 默认:0,审核成功:1

	private String idNo;// 身份证号码
	private Integer idNoStatus;// 身份审核状态 默认:0,审核成功:1
	private Integer auditStatus;// 认证状态(1 默认审核中或等待审核 2 审核不通过 3 审核通过)
	private String audExplain;//
	private Integer flag;//

	private String qq;//
	private String usrCustId;// 用户客户号
	private Integer creditNum;// 信用卡数量
	private Double creditSum;// 信用卡总额度
	private String email;// 邮箱
	
	private String invite2dcodePath;//邀请二维码图片地址

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getHighestEdu() {
		return highestEdu;
	}

	public void setHighestEdu(String highestEdu) {
		this.highestEdu = highestEdu;
	}

	public Date getEduStartDay() {
		return eduStartDay;
	}

	public void setEduStartDay(Date eduStartDay) {
		this.eduStartDay = eduStartDay;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getHasChild() {
		return hasChild;
	}

	public void setHasChild(String hasChild) {
		this.hasChild = hasChild;
	}

	public String getHasHourse() {
		return hasHourse;
	}

	public void setHasHourse(String hasHourse) {
		this.hasHourse = hasHourse;
	}

	public String getHasHousrseLoan() {
		return hasHousrseLoan;
	}

	public void setHasHousrseLoan(String hasHousrseLoan) {
		this.hasHousrseLoan = hasHousrseLoan;
	}

	public String getHasCar() {
		return hasCar;
	}

	public void setHasCar(String hasCar) {
		this.hasCar = hasCar;
	}

	public String getHasCarLoan() {
		return hasCarLoan;
	}

	public void setHasCarLoan(String hasCarLoan) {
		this.hasCarLoan = hasCarLoan;
	}

	public Integer getNativePlacePro() {
		return nativePlacePro;
	}

	public void setNativePlacePro(Integer nativePlacePro) {
		this.nativePlacePro = nativePlacePro;
	}

	public Integer getNativePlaceCity() {
		return nativePlaceCity;
	}

	public void setNativePlaceCity(Integer nativePlaceCity) {
		this.nativePlaceCity = nativePlaceCity;
	}

	public Integer getRegistedPlacePro() {
		return registedPlacePro;
	}

	public void setRegistedPlacePro(Integer registedPlacePro) {
		this.registedPlacePro = registedPlacePro;
	}

	public Integer getRegistedPlaceCity() {
		return registedPlaceCity;
	}

	public void setRegistedPlaceCity(Integer registedPlaceCity) {
		this.registedPlaceCity = registedPlaceCity;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPersonalHead() {
		return personalHead;
	}

	public void setPersonalHead(String personalHead) {
		this.personalHead = personalHead;
	}

	public String getBankCar() {
		return bankCar;
	}

	public void setBankCar(String bankCar) {
		this.bankCar = bankCar;
	}

	public Integer getBankCarStatus() {
		return bankCarStatus;
	}

	public void setBankCarStatus(Integer bankCarStatus) {
		this.bankCarStatus = bankCarStatus;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public Integer getIdNoStatus() {
		return idNoStatus;
	}

	public void setIdNoStatus(Integer idNoStatus) {
		this.idNoStatus = idNoStatus;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getAudExplain() {
		return audExplain;
	}

	public void setAudExplain(String audExplain) {
		this.audExplain = audExplain;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getUsrCustId() {
		return usrCustId;
	}

	public void setUsrCustId(String usrCustId) {
		this.usrCustId = usrCustId;
	}

	public Integer getCreditNum() {
		return creditNum;
	}

	public void setCreditNum(Integer creditNum) {
		this.creditNum = creditNum;
	}

	public Double getCreditSum() {
		return creditSum;
	}

	public void setCreditSum(Double creditSum) {
		this.creditSum = creditSum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the invite2dcodePath
	 */
	public String getInvite2dcodePath() {
		return invite2dcodePath;
	}

	/**
	 * @param invite2dcodePath the invite2dcodePath to set
	 */
	public void setInvite2dcodePath(String invite2dcodePath) {
		this.invite2dcodePath = invite2dcodePath;
	}

}
