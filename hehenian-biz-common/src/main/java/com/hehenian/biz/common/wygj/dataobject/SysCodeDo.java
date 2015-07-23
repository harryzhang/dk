/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.wygj.dataobject
 * @Title: SysCodeDo.java
 * @Description: TODO
 *
 * @author: chenzhpmf
 * @date 2015-5-6 下午6:40:36
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.common.wygj.dataobject;

import java.io.Serializable;
import java.util.Date;

public class SysCodeDo implements Serializable{
	
	private static final long serialVersionUID = -3402728554716058956L;
	
	private Integer typeId; //类型id
	private Long id; //标识
	private Integer parentTypeId; //父typeId
	private Long parentId;//父id
	private String remark;//描述
	private String remarkForShow ;//用于展示的描述
	private Integer invalid;//是否有效
	private Date createTime;//创建时间
	private Date updateTime; //修改时间
	
	
	
	
	public SysCodeDo() {
		super();
	}
	public SysCodeDo(Integer typeId, Long id) {
		super();
		this.typeId = typeId;
		this.id = id;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Integer getParentTypeId() {
		return parentTypeId;
	}
	public void setParentTypeId(Integer parentTypeId) {
		this.parentTypeId = parentTypeId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRemarkForShow() {
		return remarkForShow;
	}
	public void setRemarkForShow(String remarkForShow) {
		this.remarkForShow = remarkForShow;
	}
	public Integer getInvalid() {
		return invalid;
	}
	public void setInvalid(Integer invalid) {
		this.invalid = invalid;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	

}
