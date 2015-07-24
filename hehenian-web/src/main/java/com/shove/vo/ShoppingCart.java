package com.shove.vo;
 
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/** 
 * 购物车
 * @author yangcheng
 *
 */
public class ShoppingCart {
	//商品集合
	private List<Product> products;
	//商品总数
	private int total = 0;
	//商品总定价
	private BigDecimal totalPricing =  new BigDecimal(0);
	//商品总实价
	private BigDecimal totalPrice  =  new BigDecimal(0);
	
	
	
	/**
	 * 添加购物车商品
	 * 如购物车中已有商品只添加数量
	 * 没有则加入购物车
	 * @param product
	 */
	public void addProduct(Product product){
		if(products == null){
			products =  new ArrayList<Product>();
		}
		int index = products.indexOf(product);
		if(index != -1){//如果之前有值
			Product _product = products.get(index);
			_product.setQuantity(_product.getQuantity()+product.getQuantity());
			if(_product.getQuantity() <1 ){
				_product = null;
			}
		}else{
			products.add(product);
		}
		count();
	}
	
	
	
	/**
	 * 计算总数，总价
	 */
	public void count(){
		total = 0;
		totalPricing =  new BigDecimal(0);
		totalPrice  =  new BigDecimal(0);
		if( getProducts() != null)
		for (Product p : getProducts()) {
			this.total += p.getQuantity();
			this.totalPricing = this.totalPricing.add(p.getPricing().multiply(new BigDecimal(p.getQuantity())));
			this.totalPrice = this.totalPrice.add(p.getPrice().multiply(new BigDecimal(p.getQuantity())));
		}
	}
	
	
	/**
	 * 清空购物车
	 */
	public void clear(){
		total = 0;
		totalPricing =  new BigDecimal(0);
		totalPrice  =  new BigDecimal(0);
		this.products = null;
	}
	
	/**
	 * 根据ID删除商品
	 * @param id
	 */
	public void delete(long id){
		Product p =  new Product();
		p.setId(id);
		int index = products.indexOf(p);
		if(index != -1){
			products.remove(index);
			count();
		}
	}
	
	/**
	 * 修改数量，如果之前没有书籍则没有物品修改，给予提示
	 * @param product
	 * @return
	 */
	public boolean update(Product product,int quantity){
		if(products == null){
			return false;
		}
		int index = products.indexOf(product);
		if(index != -1){//如果之前有值
			Product _product = products.get(index);
			_product.setQuantity(quantity);
		}else{
			return false;
		}
		count();
		return true;
	}
	
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}

	public BigDecimal getTotalPricing() {
		return totalPricing;
	}

	public void setTotalPricing(BigDecimal totalPricing) {
		this.totalPricing = totalPricing;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
}
