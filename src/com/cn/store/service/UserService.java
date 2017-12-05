package com.cn.store.service;

import com.cn.store.domain.User;

public interface UserService {
	void register(User user);
	User active(String code);
	User login(String username, String password);
	User findByName(String username);
}
