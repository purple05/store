package com.cn.store.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.cn.store.domain.User;
import com.cn.store.service.UserService;
import com.cn.store.service.impl.UserServiceImpl;
import com.cn.store.utils.UUIDUtils;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private UserService uservice = new UserServiceImpl();
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		if("/jsp/register.user".equals(req.getServletPath())) {
			register(req,res);
		}else if("/jsp/active.user".equals(req.getServletPath())) {
			//localhost:8080/store/jsp/active.do ../img/1.jpg
			active(req,res);
		}else if("/jsp/login.user".equals(req.getServletPath())) {
			login(req,res);
		}else if("/jsp/logout.user".equals(req.getServletPath())) {
			logout(req,res);
		}else if("/jsp/checkreg.user".equals(req.getServletPath())) {
			checkreg(req,res);
		}else {
			System.out.println(req.getServletPath());
		}
	}

	private void checkreg(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		String username = req.getParameter("username");
		User user = uservice.findByName(username);
		if(user == null) {
			res.getWriter().print("1");
		}else {
			res.getWriter().print("2");
		}
	}

	private void logout(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException {
		req.getSession().invalidate();
		Cookie c = new Cookie("autoLogin","");
		c.setPath(req.getContextPath()+"/");
		c.setMaxAge(0);
		res.addCookie(c);
		res.sendRedirect("index.jsp");
	}

	private void login(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		User user = uservice.login(username, password);
		if(user == null) {
			req.setAttribute("msg", "用户名和密码不匹配");
			req.getRequestDispatcher("login.jsp").forward(req, res);
			return;
		}
		if(user.getState() != 1) {
			req.setAttribute("msg", "请先去邮箱激活,再登录!");
			req.getRequestDispatcher("msg.jsp").forward(req, res);
			return;
		}
		
		req.getSession().setAttribute("user", user);
		
		if("auto".equals(req.getParameter("autoLogin"))) {
			Cookie c = new Cookie("autoLogin", user.getUsername()+"@"+user.getPassword());
			
			c.setMaxAge(Integer.MAX_VALUE);
			c.setPath(req.getContextPath()+"/");
			
			res.addCookie(c);
		}else {
			Cookie c = new Cookie("autoLogin","");
			c.setPath("/");
			c.setMaxAge(0);
			res.addCookie(c);
			
		}
		if("ok".equals(req.getParameter("savename"))) {
			Cookie savename = new Cookie("savename",user.getUsername());
			savename.setPath("/");
			savename.setMaxAge(60*60*24*7);
			res.addCookie(savename);
		}
		res.sendRedirect("index.jsp");
	}

	private void active(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException {
		String code = req.getParameter("code");
		User user = uservice.active(code);
		if(user == null) {
			req.setAttribute("msg", "激活失败,请重新激活或者重新注册~");
			req.getRequestDispatcher("jsp/msg.jsp").forward(req, res);
		}
		req.setAttribute("msg", "恭喜你,激活成功了,可以登录了~");
		req.getRequestDispatcher("msg.jsp").forward(req, res);
	}

	private void register(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		User user = new User();
		try {
			BeanUtils.populate(user, req.getParameterMap());
			user.setUid(UUIDUtils.getId());
			user.setCode(UUIDUtils.getCode());
			user.setState(0);
			uservice.register(user);
			
			req.setAttribute("msg", "恭喜你,注册成功,请登录邮箱完成激活!");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			req.setAttribute("msg", "恭喜你,注册成功,请登录邮箱完成激活!");
			req.getRequestDispatcher("msg.jsp").forward(req, res);
		} 
		req.getRequestDispatcher("msg.jsp").forward(req, res);
		
	}
	

}
