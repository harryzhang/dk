package com.hehenian.biz.common.account.dataobject;

import java.io.Serializable;
import java.util.Date;

public class AccountUserDo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id; // 用户ID
	private Long usrCustId; // 用户客户号(汇付平台)
	private String username; // 帐号
	private String email; // 用户email
	private String password; // 用户密码

	private String dealpwd; // 交易密码(初始密码为用户密码)
	private String mobilePhone; // 用户移动电话
	private String refferee; // 推荐人
	private Integer rating; // 推荐人
	private Integer creditrating; // 信用积分

	private String lastIP; // 最后登录IP
	private Date lastDate; // 最后登录时间
	private Integer vipStatus; // VIP会员状态(1 非VIP 2 VIP(默认)
								// 3 待续费VIP)
	private Date vipCreateTime; // VIP创建时间
	private Long creditLimit; // 信用额度

	private Integer authStep; // 认证步骤(默认是1 个人详细信息 2 工作认证
								// 3上传 资料)
	private String headImg; // 头像
	private Integer enable; // 是否禁用 1、启用 2、禁用 3.黑名单 默认1
	private String cause; // 锁定用户原因
	private Date createTime; // 帐号创建时间

	private String content; // 用户vip申请会员时候填写的备注
	private Double usableSum; // 可用金额
	private Double freezeSum; // 冻结金额
	private Double dueinSum; // 待收金额(和合年当前值=待还本金+待还利息)
	private Double dueinCapitalSum; // 待收本金

	private Double dueinAccrualSum; // 待收利息
	private Double dueoutSum; // 待还金额
	private Long kefuId; // 客服Id
	private Long adminId; // 后台审核员id
	private Long groupId; // 组ID

	private Long usableCreditLimit; // 可用信用额度
	private Long creditlimtor; // 额度审核审核员id
	private Double vipFee; // vip会费
	private Integer feeStatus; // 费用扣除状态( 1 待扣 2 已扣)
	private Long loginCount; // 登录次数

	private Date lockTime; // 锁定时间
	private Integer cashStatus; // 提现状态(默认1 启动 2 禁止)
	private Double xmax; // 最大待收本金
	private Double x; // 系数
	private Double xmin; // 最小值

	private Integer isFirstVip; // 是否首次成为VIP(默认 1 是 2 否)
	private Integer source; // 用户来源：0小贷公司导入，1网站注册，2彩生活用户，3净值用户,4彩之云app用户
	private String registerIp; // 注册IP
	private Long colorid; // 彩之云userid
	private String via; // 用户来源平台

	private String colorcheck; // 彩之云userid验证码
	private String colorTjr; //
	private Integer userGroup; // 用户群组 1=花样年
	private Double lockAmount; // 锁定金额

	private String reffer; // 推荐人原始字段 可能为用户id 或 手机号码

	private PersonDo person; // 根据userid 查询得到的person对象

	private Boolean phoneHasVerify;// 手机号码是否已经认证

	private Integer accountType; // 代理人账户类型：1
	private String payPassword;

	public String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	/**
	 * @return reffer
	 */
	public String getReffer() {
		return reffer;
	}

	/**
	 * @param reffer
	 *            the reffer to set
	 */
	public void setReffer(String reffer) {
		this.reffer = reffer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUsrCustId() {
		return usrCustId == null ? -1 : usrCustId;
	}

	public void setUsrCustId(Long usrCustId) {
		this.usrCustId = usrCustId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDealpwd() {
		return dealpwd;
	}

	public void setDealpwd(String dealpwd) {
		this.dealpwd = dealpwd;
	}

	public String getMobilePhone() {
		if (mobilePhone != null && mobilePhone.indexOf("-") == 0) {
			return mobilePhone.substring(1, mobilePhone.length());
		} else {
			return mobilePhone;
		}
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getRefferee() {
		return refferee;
	}

	public void setRefferee(String refferee) {
		this.refferee = refferee;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Integer getCreditrating() {
		return creditrating;
	}

	public void setCreditrating(Integer creditrating) {
		this.creditrating = creditrating;
	}

	public String getLastIP() {
		return lastIP;
	}

	public void setLastIP(String lastIP) {
		this.lastIP = lastIP;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public Integer getVipStatus() {
		return vipStatus;
	}

	public void setVipStatus(Integer vipStatus) {
		this.vipStatus = vipStatus;
	}

	public Date getVipCreateTime() {
		return vipCreateTime;
	}

	public void setVipCreateTime(Date vipCreateTime) {
		this.vipCreateTime = vipCreateTime;
	}

	public Long getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(Long creditLimit) {
		this.creditLimit = creditLimit;
	}

	public Integer getAuthStep() {
		return authStep;
	}

	public void setAuthStep(Integer authStep) {
		this.authStep = authStep;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Double getUsableSum() {
		return usableSum;
	}

	public void setUsableSum(Double usableSum) {
		this.usableSum = usableSum;
	}

	public Double getFreezeSum() {
		return freezeSum;
	}

	public void setFreezeSum(Double freezeSum) {
		this.freezeSum = freezeSum;
	}

	public Double getDueinSum() {
		return dueinSum;
	}

	public void setDueinSum(Double dueinSum) {
		this.dueinSum = dueinSum;
	}

	public Double getDueinCapitalSum() {
		return dueinCapitalSum;
	}

	public void setDueinCapitalSum(Double dueinCapitalSum) {
		this.dueinCapitalSum = dueinCapitalSum;
	}

	public Double getDueinAccrualSum() {
		return dueinAccrualSum;
	}

	public void setDueinAccrualSum(Double dueinAccrualSum) {
		this.dueinAccrualSum = dueinAccrualSum;
	}

	public Double getDueoutSum() {
		return dueoutSum;
	}

	public void setDueoutSum(Double dueoutSum) {
		this.dueoutSum = dueoutSum;
	}

	public Long getKefuId() {
		return kefuId;
	}

	public void setKefuId(Long kefuId) {
		this.kefuId = kefuId;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getUsableCreditLimit() {
		return usableCreditLimit;
	}

	public void setUsableCreditLimit(Long usableCreditLimit) {
		this.usableCreditLimit = usableCreditLimit;
	}

	public Long getCreditlimtor() {
		return creditlimtor;
	}

	public void setCreditlimtor(Long creditlimtor) {
		this.creditlimtor = creditlimtor;
	}

	public Double getVipFee() {
		return vipFee;
	}

	public void setVipFee(Double vipFee) {
		this.vipFee = vipFee;
	}

	public Integer getFeeStatus() {
		return feeStatus;
	}

	public void setFeeStatus(Integer feeStatus) {
		this.feeStatus = feeStatus;
	}

	public Long getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Long loginCount) {
		this.loginCount = loginCount;
	}

	public Date getLockTime() {
		return lockTime;
	}

	public void setLockTime(Date lockTime) {
		this.lockTime = lockTime;
	}

	public Integer getCashStatus() {
		return cashStatus;
	}

	public void setCashStatus(Integer cashStatus) {
		this.cashStatus = cashStatus;
	}

	public Double getXmax() {
		return xmax;
	}

	public void setXmax(Double xmax) {
		this.xmax = xmax;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getXmin() {
		return xmin;
	}

	public void setXmin(Double xmin) {
		this.xmin = xmin;
	}

	public Integer getIsFirstVip() {
		return isFirstVip;
	}

	public void setIsFirstVip(Integer isFirstVip) {
		this.isFirstVip = isFirstVip;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getRegisterIp() {
		return registerIp;
	}

	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}

	public Long getColorid() {
		return colorid;
	}

	public void setColorid(Long colorid) {
		this.colorid = colorid;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getColorcheck() {
		return colorcheck;
	}

	public void setColorcheck(String colorcheck) {
		this.colorcheck = colorcheck;
	}

	public String getColorTjr() {
		return colorTjr;
	}

	public void setColorTjr(String colorTjr) {
		this.colorTjr = colorTjr;
	}

	public Integer getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(Integer userGroup) {
		this.userGroup = userGroup;
	}

	/**
	 * @return lockAmount
	 */
	public Double getLockAmount() {
		return lockAmount;
	}

	/**
	 * @param lockAmount
	 *            the lockAmount to set
	 */
	public void setLockAmount(Double lockAmount) {
		this.lockAmount = lockAmount;
	}

	/**
	 * @return person
	 */
	public PersonDo getPerson() {
		return person;
	}

	/**
	 * @param person
	 *            the person to set
	 */
	public void setPerson(PersonDo person) {
		this.person = person;
	}

	@Override
	public String toString() {
		return "AccountUserDo{" + "id=" + id + ", usrCustId=" + usrCustId
				+ ", username='" + username + '\'' + ", email='" + email + '\''
				+ ", password='" + password + '\'' + ", dealpwd='" + dealpwd
				+ '\'' + ", mobilePhone='" + mobilePhone + '\''
				+ ", refferee='" + refferee + '\'' + ", rating=" + rating
				+ ", creditrating=" + creditrating + ", lastIP='" + lastIP
				+ '\'' + ", lastDate=" + lastDate + ", vipStatus=" + vipStatus
				+ ", vipCreateTime=" + vipCreateTime + ", creditLimit="
				+ creditLimit + ", authStep=" + authStep + ", headImg='"
				+ headImg + '\'' + ", enable=" + enable + ", cause='" + cause
				+ '\'' + ", createTime=" + createTime + ", content='" + content
				+ '\'' + ", usableSum=" + usableSum + ", freezeSum="
				+ freezeSum + ", dueinSum=" + dueinSum + ", dueinCapitalSum="
				+ dueinCapitalSum + ", dueinAccrualSum=" + dueinAccrualSum
				+ ", dueoutSum=" + dueoutSum + ", kefuId=" + kefuId
				+ ", adminId=" + adminId + ", groupId=" + groupId
				+ ", usableCreditLimit=" + usableCreditLimit
				+ ", creditlimtor=" + creditlimtor + ", vipFee=" + vipFee
				+ ", feeStatus=" + feeStatus + ", loginCount=" + loginCount
				+ ", lockTime=" + lockTime + ", cashStatus=" + cashStatus
				+ ", xmax=" + xmax + ", x=" + x + ", xmin=" + xmin
				+ ", isFirstVip=" + isFirstVip + ", accountType=" + accountType
				+ ", source=" + source + ", registerIp='" + registerIp + '\''
				+ ", colorid=" + colorid + ", via='" + via + '\''
				+ ", colorcheck='" + colorcheck + '\'' + ", colorTjr='"
				+ colorTjr + '\'' + ", userGroup=" + userGroup
				+ ", lockAmount=" + lockAmount + ", reffer='" + reffer + '\''
				+ '}';
	}

	public Boolean getPhoneHasVerify() {
		return phoneHasVerify;
	}

	public void setPhoneHasVerify(Boolean phoneHasVerify) {
		this.phoneHasVerify = phoneHasVerify;
	}

}
