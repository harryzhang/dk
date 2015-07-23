/**
 * 
 */
package com.hehenian.biz.common.account.dataobject;

import java.io.Serializable;
import java.util.Date;

/**
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.account.dataobject
 * @Title: MaterialsAuth
 * @Description: 资料认证实体类
 * @author: chenzhpmf
 * @date 2015年4月28日
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
public class MaterialsAuth implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long	id;                 // 编号
	private long	materAuthTypeId;    // 认证类别id(关联认证类别表)             
	private String	imgPath;            // 认证图片地址
	private int		auditStatus;        // 认证状态(默认NULL，表示未上传，1 默认审核中或等待审核 2 审核不通过 3 审核通过)
	private long	userId;             // 用户id
	private Date	authTime;           // 认证通过时间
	private Date	passTime;           // 上传资料时间
	private String	option;             // 证件的审核观点
	private Date	pastTime;           // 过期时间(当确认状态为审核失败（2）的时候这个时间有值)
	private long	materaldetalId;     // 可见图片的id
	private int		criditing;          // 单个证件的信用积分 默认积分为0

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the materAuthTypeId
	 */
	public long getMaterAuthTypeId() {
		return materAuthTypeId;
	}

	/**
	 * @param materAuthTypeId the materAuthTypeId to set
	 */
	public void setMaterAuthTypeId(long materAuthTypeId) {
		this.materAuthTypeId = materAuthTypeId;
	}

	/**
	 * @return the imgPath
	 */
	public String getImgPath() {
		return imgPath;
	}

	/**
	 * @param imgPath the imgPath to set
	 */
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	/**
	 * @return the auditStatus
	 */
	public int getAuditStatus() {
		return auditStatus;
	}

	/**
	 * @param auditStatus the auditStatus to set
	 */
	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

	/**
	 * @return the userId
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @return the authTime
	 */
	public Date getAuthTime() {
		return authTime;
	}

	/**
	 * @param authTime the authTime to set
	 */
	public void setAuthTime(Date authTime) {
		this.authTime = authTime;
	}

	/**
	 * @return the passTime
	 */
	public Date getPassTime() {
		return passTime;
	}

	/**
	 * @param passTime the passTime to set
	 */
	public void setPassTime(Date passTime) {
		this.passTime = passTime;
	}

	/**
	 * @return the option
	 */
	public String getOption() {
		return option;
	}

	/**
	 * @param option the option to set
	 */
	public void setOption(String option) {
		this.option = option;
	}

	/**
	 * @return the pastTime
	 */
	public Date getPastTime() {
		return pastTime;
	}

	/**
	 * @param pastTime the pastTime to set
	 */
	public void setPastTime(Date pastTime) {
		this.pastTime = pastTime;
	}

	/**
	 * @return the materaldetalId
	 */
	public long getMateraldetalId() {
		return materaldetalId;
	}

	/**
	 * @param materaldetalId the materaldetalId to set
	 */
	public void setMateraldetalId(long materaldetalId) {
		this.materaldetalId = materaldetalId;
	}

	/**
	 * @return the criditing
	 */
	public int getCriditing() {
		return criditing;
	}

	/**
	 * @param criditing the criditing to set
	 */
	public void setCriditing(int criditing) {
		this.criditing = criditing;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MaterialsAuth [id=" + id + ", materAuthTypeId=" + materAuthTypeId + ", imgPath=" + imgPath + ", auditStatus=" + auditStatus
				+ ", userId=" + userId + ", authTime=" + authTime + ", passTime=" + passTime + ", option=" + option + ", pastTime="
				+ pastTime + ", materaldetalId=" + materaldetalId + ", criditing=" + criditing + "]";
	}
}
