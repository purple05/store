package com.cn.store.dao;

import java.util.List;

import com.cn.store.domain.Category;

public interface CategoryDao {
	List<Category> findAll();
	void save(Category category);
	Category findById(String cid);
	void update(Category category);
	void delete(String cid);
}
