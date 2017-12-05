package com.cn.store.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.cn.store.dao.UserDao;
import com.cn.store.domain.User;
import com.cn.store.utils.DataSourceUtils;

public class UserDaoImpl implements UserDao {
	private QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
	
	@Override
	public void save(User user)  {
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		Object[] param = {user.getUid(),user.getUsername(),user.getPassword(),
				user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),
				user.getSex(),user.getState(),user.getCode()}; 
		try {
			qr.update(sql, param);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public User getByCode(String code) {
		String sql = "select * from user where code = ?";
		try {
			return qr.query(sql, new BeanHandler<User>(User.class),code);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(User user) {
		String sql = "update user set state = ?,code=null where uid = ?";
		Object[] param = {1,user.getUid()};
		try {
			qr.update(sql, param);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public User getByUsernameAndPwd(String username, String password) {
		String sql = "select * from user where username=? and password=?";
		Object[] param = {username,password};
		
		try {
			return qr.query(sql, new BeanHandler<User>(User.class),param);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public User getByUsername(String username) {
		String sql = "select * from user where username = ?";
		try {
			return qr.query(sql, new BeanHandler<User>(User.class),username);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
