package com.cn.store.service;

import java.util.List;

import com.cn.store.domain.PageBean;
import com.cn.store.domain.Product;

public interface ProductService {
	List<Product> findByHot();
	List<Product> findByNew();
	Product findById(String pid);
	//分类及分页查询
	PageBean<Product> findByCid(String cid,int pageNumber,int pageSize);
	
	//查询所有商品（含分页）
	PageBean<Product> findByPage(int pageNumber,int pageSize);
	
	void save(Product product);
}
