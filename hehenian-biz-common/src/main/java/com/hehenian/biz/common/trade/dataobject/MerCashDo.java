package com.hehenian.biz.common.trade.dataobject;

import java.io.Serializable;
import java.util.Date;

public class MerCashDo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long Id;// 商户提现ID
	private Long userId;// 用户ID
	private String cardNo;// 取现银行卡号
	private Double sum;// 提现金额
	private Double poundage;// 提现手续费
	private Date applyTime;// 提现时间
	private Integer status;// 状态(1成功,0失败)
	private Long adminId;// 管理用户ID

	private String usrCustId;// 客户号

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
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

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public String getUsrCustId() {
		return usrCustId;
	}

	public void setUsrCustId(String usrCustId) {
		this.usrCustId = usrCustId;
	}

}
