/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.colorlife.dataobject
 * @Title: BusinessDo.java
 * @Description: TODO
 *
 * @author: chenzhpmf
 * @date 2015-5-8 下午9:30:07
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.common.colorlife.dataobject;

import java.io.Serializable;
import java.util.Date;

public class BusinessDo implements Serializable{

	private static final long serialVersionUID = 2069441934881549593L;
	
	private Integer businessType;
	private Long businessId;
	private String externalId;
	private Integer status;
	private Date createTime;
	private Date updateTime;
	
	public Integer getBusinessType() {
		return businessType;
	}
	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}
	public Long getBusinessId() {
		return businessId;
	}
	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
