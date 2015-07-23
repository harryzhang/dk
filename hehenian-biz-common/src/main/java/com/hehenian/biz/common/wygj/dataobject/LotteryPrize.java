/**  
 * @Project: hehenian-biz-common
 * @Package com.hehenian.biz.common.wygj.dataobject
 * @Title: LotteryPrize.java
 * @Description: TODO
 *
 * @author: chenzhpmf
 * @date 2015-5-13 下午5:23:33
 * @Copyright: HEHENIAN Co.,Ltd. All rights reserved.
 * @version V1.0   
 */
package com.hehenian.biz.common.wygj.dataobject;

import java.io.Serializable;

public class LotteryPrize implements Serializable{
	
	private static final long serialVersionUID = -2376866329921983605L;
	
	private Integer id;
	private String prizeName;
	private Integer totalQuantitty;
	private String prob;
	private Integer dayQuantity;
	private Integer yetQuantity;
	private Integer status;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPrizeName() {
		return prizeName;
	}
	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	public Integer getTotalQuantitty() {
		return totalQuantitty;
	}
	public void setTotalQuantitty(Integer totalQuantitty) {
		this.totalQuantitty = totalQuantitty;
	}
	public String getProb() {
		return prob;
	}
	public void setProb(String prob) {
		this.prob = prob;
	}
	public Integer getDayQuantity() {
		return dayQuantity;
	}
	public void setDayQuantity(Integer dayQuantity) {
		this.dayQuantity = dayQuantity;
	}
	public Integer getYetQuantity() {
		return yetQuantity;
	}
	public void setYetQuantity(Integer yetQuantity) {
		this.yetQuantity = yetQuantity;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

}
