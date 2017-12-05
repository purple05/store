package com.cn.store.domain;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Cart {
	//购物项集合属性
	private Map<String,CartItem> map = new LinkedHashMap<String,CartItem>();
	private double total;//总价
	
	public Map<String,CartItem> getMap(){
		return map;
	}
	
	//获取所有的购物项
	public Collection<CartItem> getCartItems(){
		return map.values();
	}
	
	//计算总价
	public double getTotal() {
		total = 0;
		Set<Entry<String,CartItem>> entrySet = map.entrySet();
		for (Entry<String, CartItem> entry : entrySet) {
			CartItem cartItem = entry.getValue();
			total += cartItem.getSubtotal();
		}
		return total;
	}
	
	//添加商品到购物车
	public void addCart(Product product,int count) {
		if(product == null) {
			return;
		}
		
		CartItem cartItem = map.get(product.getPid());
		
		if(cartItem == null) {
			cartItem = new CartItem();
			cartItem.setProduct(product);
			cartItem.setCount(count);
			map.put(product.getPid(), cartItem);
		}else {
			cartItem.setCount(cartItem.getCount()+count);
		}
		
		
	}
	
	//移除购物项
	public void removeCart(String id) {
		map.remove(id);
	}
	
	//清空购物车
	public void clearCart() {
		map.clear();
	}
}
