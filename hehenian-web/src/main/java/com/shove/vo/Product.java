package com.shove.vo;
 
import java.math.BigDecimal;

/**
 * 购物车中栋商品
 * @author yangcheng
 *
 */
public class Product {
	//商品ID
	private long id;
	//定价||E币
	private BigDecimal pricing;	
	//实际价格||E币
	private BigDecimal price;	
	//数量
	private int quantity;
	//是否删除
	private boolean isDelete;
	
	
	
	public Product() {
		super();
	}
	public Product(long id, BigDecimal pricing, BigDecimal price, int quantity,
			boolean isDelete) {
		super();
		this.id = id;
		this.pricing = pricing;
		this.price = price;
		this.quantity = quantity;
		this.isDelete = isDelete;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public BigDecimal getPricing() {
		return pricing;
	}
	public void setPricing(BigDecimal pricing) {
		this.pricing = pricing;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (id != other.id)
			return false;
		return true;
	}

	

}
