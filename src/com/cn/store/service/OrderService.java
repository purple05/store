package com.cn.store.service;

import com.cn.store.domain.Order;
import com.cn.store.domain.PageBean;
import com.cn.store.domain.User;

public interface OrderService {
	void save(Order order);
	PageBean<Order> findByUid(User loginUser,int pageNumber,int pageSize);
	Order findByOid(String oid);
	void update(Order order);
}
