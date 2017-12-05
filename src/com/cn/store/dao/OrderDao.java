package com.cn.store.dao;

import java.sql.Connection;
import java.util.List;

import com.cn.store.domain.Order;
import com.cn.store.domain.OrderItem;
import com.cn.store.domain.User;

public interface OrderDao {
	void save(Connection conn,Order order);
	void save(Connection conn,OrderItem orderItem);
	int findTotalRecordByUid(User user);//通过用户查询订单总记录数
	List<Order> findAllByUid(User user,int startIndex,int pageSize);
	Order findByOid(String oid);
	void update(Order order);
}
