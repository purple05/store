package com.cn.store.domain;

/**
 *购物项实体：用于封装某一类商品的购买情况
 */
public class CartItem {
	private Product product;
	private int count;
	private double subtotal;
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getSubtotal() {
		this.subtotal = this.count*product.getShop_price();
		return this.subtotal;
	}
	
	/*public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}*/
	
	
}
