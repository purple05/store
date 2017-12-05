package com.cn.store.dao;

import com.cn.store.domain.User;

public interface UserDao {
	void save(User user);
	User getByCode(String code);
	void update(User user);
	User getByUsernameAndPwd(String username, String password);
	User getByUsername(String username);
}
