package com.cn.store.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.cn.store.dao.CategoryDao;
import com.cn.store.domain.Category;
import com.cn.store.utils.DataSourceUtils;

public class CategoryDaoImpl implements CategoryDao {
	QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

	@Override
	public List<Category> findAll() {
		String sql = "select * from category";
		try {
			return qr.query(sql, new BeanListHandler<Category>(Category.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void save(Category category) {
		String sql = "insert into category values(?,?)";
		try {
			qr.update(sql, category.getCid(),category.getCname());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public Category findById(String cid) {
		String sql = "select * from category where cid=?";
		try {
			return qr.query(sql, new BeanHandler<Category>(Category.class),cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Category category) {
		String sql = "update category set cname=? where cid=?";
		try {
			qr.update(sql,category.getCname(),category.getCid());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void delete(String cid) {
		
		String sql = "update product set cid=null where cid=?";
		try {
			qr.update(sql,cid);
			sql = "delete from category where cid=?";
			qr.update(sql,cid);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
