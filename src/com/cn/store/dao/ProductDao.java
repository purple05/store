package com.cn.store.dao;

import java.util.List;

import com.cn.store.domain.Product;

public interface ProductDao {
	List<Product> findByHot();
	List<Product> findByNew();
	Product findById(String pid);
	int findTotalRecordByCid(String cid);//分页总记录
	List<Product> findAllByCid(String cid,int startIndex,int pageSize);
	
	int findTotalRecord();
	List<Product> findAll(int startIndex,int pageSize);
	
	void save(Product product);
}
