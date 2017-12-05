package com.cn.store.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.cn.store.dao.ProductDao;
import com.cn.store.domain.Product;
import com.cn.store.utils.DataSourceUtils;

public class ProductDaoImpl implements ProductDao {
	private QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
	@Override
	public List<Product> findByHot() {
		String sql = "select * from product where is_hot=? and pflag=? order by pdate desc limit ?";
		Object[] params = {1,0,9};
		try {
			return qr.query(sql, new BeanListHandler<Product>(Product.class), params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Product> findByNew() {
		String sql = "select * from product where pflag=? order by pdate desc limit ?";
		Object[] params = {0,9};
		try {
			return qr.query(sql, new BeanListHandler<Product>(Product.class), params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Product findById(String pid) {
		String sql = "select * from product where pid = ?";
		try {
			return qr.query(sql, new BeanHandler<Product>(Product.class), pid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int findTotalRecordByCid(String cid) {
		String sql = "select count(*) from product where cid=?";
		try {
			return ((Number)qr.query(sql, new ScalarHandler(),cid)).intValue();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Product> findAllByCid(String cid, int startIndex, int pageSize) {
		String sql = "select * from product where cid=? and pflag=? order by pdate desc limit ?,?";
		Object[] params = {cid,0,startIndex,pageSize};
		try {
			return qr.query(sql, new BeanListHandler<Product>(Product.class), params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public int findTotalRecord() {
		String sql = "select count(*) from product where pflag=?";
		try {
			Long count = (long)qr.query(sql, new ScalarHandler<>(),0);
			return count.intValue();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Product> findAll(int startIndex,int pageSize) {
		String sql = "select * from product where pflag = ? order by pdate desc limit ?,?";
		Object[] params = {0,(startIndex-1)*pageSize,pageSize};
		try {
			return qr.query(sql, new BeanListHandler<Product>(Product.class), params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public void save(Product product) {
		String sql = "insert into product values (?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {product.getPid(),product.getPname(),
				product.getMarket_price(),product.getShop_price(),product.getPimage(),
				product.getPdate(),product.getIs_hot(),product.getPdesc(),
				product.getPflag(),product.getCid()};
		try {
			qr.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
