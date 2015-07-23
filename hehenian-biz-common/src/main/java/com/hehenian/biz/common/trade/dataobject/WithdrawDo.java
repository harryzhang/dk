package com.hehenian.biz.common.trade.dataobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 提现申请对象
 * 
 * @author liuzgmf
 *
 */
public class WithdrawDo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;// 编号
	private String name;// 姓名
	private String cellPhone;// 手机号码
	private String acount;// 提现帐号
	private Double sum;// 提现金额

	private Double poundage;// 手续费
	private Date applyTime;// 申请时间
	private Integer status;// 状态(1 默认初审核中 2 已提现 3 取消 4转账中 5 失败 )
	private Long bankId;// 银行卡ID
	private Long userId;// 用户ID

	private String remark;// 备注
	private Long checkId;// 审核人id
	private Date checkTime;// 审核时间
	private String ipAddress;// IP地址
	private Integer versoin;// 版本控制

	private String trxId;// 汇付交易标识ID
	private String usrCustId;// 客户号

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getAcount() {
		return acount;
	}

	public void setAcount(String acount) {
		this.acount = acount;
	}

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}

	public Double getPoundage() {
		return poundage;
	}

	public void setPoundage(Double poundage) {
		this.poundage = poundage;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCheckId() {
		return checkId;
	}

	public void setCheckId(Long checkId) {
		this.checkId = checkId;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Integer getVersoin() {
		return versoin;
	}

	public void setVersoin(Integer versoin) {
		this.versoin = versoin;
	}

	public String getTrxId() {
		return trxId;
	}

	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}

	public String getUsrCustId() {
		return usrCustId;
	}

	public void setUsrCustId(String usrCustId) {
		this.usrCustId = usrCustId;
	}

}
