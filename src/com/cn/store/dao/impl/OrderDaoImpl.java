package com.cn.store.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.cn.store.dao.OrderDao;
import com.cn.store.domain.Order;
import com.cn.store.domain.OrderItem;
import com.cn.store.domain.Product;
import com.cn.store.domain.User;
import com.cn.store.utils.DataSourceUtils;

public class OrderDaoImpl implements OrderDao {
	QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
	@Override
	public void save(Connection conn, Order order) {
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		Object[] params = {order.getOid(),order.getOrdertime(),order.getTotal(),
				order.getState(),order.getAddress(),order.getName(),order.getTelephone(),
				order.getUser().getUid()};
		try {
			qr.update(conn, sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public void save(Connection conn, OrderItem orderItem) {
		String sql = "insert into orderItem values(?,?,?,?,?)";
		Object[] params = {orderItem.getItemid(),orderItem.getCount(),orderItem.getSubtotal(),
				orderItem.getProduct().getPid(),orderItem.getOrder().getOid()};
		try {
			qr.update(conn, sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public int findTotalRecordByUid(User user) {
		String sql = "select count(*) from orders where uid=?";
		try {
			Long count = (long)qr.query(sql, new ScalarHandler(),user.getUid());
			return count.intValue();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Order> findAllByUid(User user, int startIndex, int pageSize) {
		String sql = "select * from orders where uid = ? order by ordertime desc limit ?,?";
		try {
			List<Order> list = qr.query(sql, new BeanListHandler<Order>(Order.class),user.getUid(),startIndex,pageSize);
			
			for (Order order : list) {
				
				sql = "select * from orderitem o,product p where oid=? and o.pid=p.pid";
				//通过字段名获取相应的值
				List<Map<String, Object>> oList = qr.query(sql, new MapListHandler(),order.getOid());
			
				for (Map<String, Object> map : oList) {
					OrderItem orderItem = new OrderItem();
					BeanUtils.populate(orderItem, map);
					Product product = new Product();
					BeanUtils.populate(product, map);
					orderItem.setProduct(product);
					orderItem.setOrder(order);
					
					order.getList().add(orderItem);
				}
				
				order.setUser(user);
			}
			return list;
		} catch (SQLException | IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Order findByOid(String oid) {
		String sql = "select * from orders where oid = ?";
		try {
			Order order = qr.query(sql, new BeanHandler<Order>(Order.class),oid);
			
			sql = "select * from orderitem o,product p where o.oid=? and o.pid=p.pid";
			//通过字段名获取相应的值
			List<Map<String, Object>> oList = qr.query(sql, new MapListHandler(),oid);
		
			for (Map<String, Object> map : oList) {
				OrderItem orderItem = new OrderItem();
				BeanUtils.populate(orderItem, map);
				Product product = new Product();
				BeanUtils.populate(product, map);
				orderItem.setProduct(product);
				orderItem.setOrder(order);
				
				order.getList().add(orderItem);
			}
			return order;
			
		} catch (SQLException | IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Order order) {
		String sql = "update orders set total=?,state=?,address=?,telephone=? where oid=?";
		Object[] params = {order.getTotal(),order.getState(),order.getAddress(),
				order.getTelephone(),order.getOid()};
		try {
			qr.update(sql,params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
