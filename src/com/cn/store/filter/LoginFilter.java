package com.cn.store.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cn.store.domain.User;
import com.cn.store.service.UserService;
import com.cn.store.service.impl.UserServiceImpl;
import com.cn.store.utils.CookUtils;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		/*if("*.user".equals(req.getServletPath())) {
			chain.doFilter(request, response);
		}*/
		
//		已经登录
		Object user = req.getSession().getAttribute("user");
		if(user != null) {
			chain.doFilter(request, response);
			return;
		}
		
//		查找cookie
		Cookie[] cks = req.getCookies();
		Cookie ck = CookUtils.getCookieByName("autoLogin", cks);
		
		if(ck == null) {
			chain.doFilter(request, response);
			return;
		}
		
		String[] autologin = ck.getValue().split("@");
		String username = autologin[0];
		String password = autologin[1];
		
		UserService uservice = new UserServiceImpl();
		User u = uservice.login(username, password);
		
		if(u == null) {
			chain.doFilter(request, response);
		}
//		找到了就自动登录
		req.getSession().setAttribute("user", u);
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
