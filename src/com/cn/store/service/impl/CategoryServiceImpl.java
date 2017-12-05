package com.cn.store.service.impl;

import java.util.List;

import com.cn.store.dao.CategoryDao;
import com.cn.store.dao.impl.CategoryDaoImpl;
import com.cn.store.domain.Category;
import com.cn.store.service.CategoryService;

public class CategoryServiceImpl implements CategoryService {
	CategoryDao cdao = new CategoryDaoImpl();

	@Override
	public List<Category> findAll() {
		return cdao.findAll();
	}

	@Override
	public void save(Category category) {
		cdao.save(category);
		
	}

	@Override
	public Category findById(String cid) {
		return cdao.findById(cid);
	}

	@Override
	public void update(Category category) {
		cdao.update(category);
		
	}

	@Override
	public void delete(String cid) {
		cdao.delete(cid);
		
	}

}
