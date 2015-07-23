/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.wygj.dataobject
 * @Title: LotteryInfo.java
 * @Description: TODO
 *
 * @author: chenzhpmf
 * @date 2015-5-13 下午2:46:30
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.common.wygj.dataobject;

import java.io.Serializable;
import java.util.Date;

public class LotteryInfo implements Serializable{

	private static final long serialVersionUID = 7943321017517160267L;
	
	private Integer id;
	private Integer userId;
	private String userName;
	private String prizeName;
	private Date createTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPrizeName() {
		return prizeName;
	}
	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	

}
