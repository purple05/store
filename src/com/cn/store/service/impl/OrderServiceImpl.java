package com.cn.store.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.cn.store.dao.OrderDao;
import com.cn.store.dao.impl.OrderDaoImpl;
import com.cn.store.domain.Order;
import com.cn.store.domain.OrderItem;
import com.cn.store.domain.PageBean;
import com.cn.store.domain.User;
import com.cn.store.service.OrderService;
import com.cn.store.utils.DataSourceUtils;

public class OrderServiceImpl implements OrderService {
	OrderDao odao = new OrderDaoImpl();
	@Override
	public void save(Order order) {
		Connection conn = null;
		try {
			conn = DataSourceUtils.getConnection();
			odao.save(conn, order);
			for (OrderItem orderItem : order.getList()) {
				odao.save(conn, orderItem);
			}
			DataSourceUtils.commitAndClose();
		} catch (SQLException e) {
			DataSourceUtils.rollbackAndClose();
			e.printStackTrace();
		}

	}
	@Override
	public PageBean<Order> findByUid(User loginUser, int pageNumber, int pageSize) {
		int totalRecord = odao.findTotalRecordByUid(loginUser);
		PageBean<Order> pageBean = new PageBean<>(pageNumber,pageSize,totalRecord);
		List<Order> data = odao.findAllByUid(loginUser, pageBean.getStartIndex(), pageBean.getPageSize());
		pageBean.setData(data);
		return pageBean;
	}
	@Override
	public Order findByOid(String oid) {
		return odao.findByOid(oid);
	}
	@Override
	public void update(Order order) {
		odao.update(order);
		
	}

}
