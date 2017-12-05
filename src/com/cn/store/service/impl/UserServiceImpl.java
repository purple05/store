package com.cn.store.service.impl;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.Session;

import com.cn.store.dao.UserDao;
import com.cn.store.dao.impl.UserDaoImpl;
import com.cn.store.domain.User;
import com.cn.store.mail.Mail;
import com.cn.store.mail.MailUtils;
import com.cn.store.service.UserService;

public class UserServiceImpl implements UserService {
	private UserDao udao = new UserDaoImpl();
	@Override
	public void register(User user) {
		udao.save(user);
		sendEmail(user);
	}
	private void sendEmail(User user) {
		Session session = MailUtils.createSession("smtp.163.com", "gxls_hzw@163.com", "hzw123456");
		String msg = user.getUsername()+"请点击这里激活账号，<a href='http://localhost/store/jsp/active.user?code="+user.getCode()+"'>激活</a>";
		Mail mail = new Mail("gxls_hzw@163.com", user.getEmail(), "激活", msg);
		try {
			MailUtils.send(session, mail);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public User active(String code) {
		User user = udao.getByCode(code);
		if(user == null ) {
			return null;
		}
		udao.update(user);
		return user;
	}
	@Override
	public User login(String username, String password) {
		User user = udao.getByUsernameAndPwd(username, password);
		return user;
	}
	@Override
	public User findByName(String username) {
		return udao.getByUsername(username);
	}

}
