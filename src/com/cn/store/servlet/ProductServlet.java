package com.cn.store.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cn.store.domain.PageBean;
import com.cn.store.domain.Product;
import com.cn.store.service.ProductService;
import com.cn.store.service.impl.ProductServiceImpl;
import com.cn.store.utils.CookUtils;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("*.product")
public class ProductServlet extends HttpServlet {
	private ProductService ps = new ProductServiceImpl();
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		if("/findById.product".equals(req.getServletPath())) {
			findById(req,res);
		}else if("/findByCid.product".equals(req.getServletPath())) {
			findByCid(req,res);
		}else if("/history.product".equals(req.getServletPath())) {
			history(req,res);
		}else {
			System.out.println(req.getServletPath());
		}
	}

	private void history(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		Cookie cookies = CookUtils.getCookieByName("history", req.getCookies());
		if(cookies == null) {
			return;
		}
		String ck = cookies.getValue();
		String[] split = ck.split("-");
		List<Product> list = new ArrayList<Product>();
		for (String pid : split) {
			list.add(ps.findById(pid));
		}
//		res.setContentType("application/json;charset=utf-8");
//		System.out.println(list.size());
		res.setContentType("text/html;charset=utf-8");
		JSONArray json = JSONArray.fromObject(list);
		res.getWriter().println(json);
	}

	private void findByCid(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		String cid = req.getParameter("cid");
		int pageNumber = 1;
		String pn = req.getParameter("pageNumber");
		if(pn != null) {
			pageNumber = Integer.parseInt(pn);
		}
		int pageSize = 12;
		PageBean<Product> pageBean = ps.findByCid(cid, pageNumber, pageSize);
		req.setAttribute("pb", pageBean);
		req.getRequestDispatcher("jsp/product_list.jsp").forward(req, res);
		
	}

	private void findById(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		String pid = req.getParameter("pid");
		Product product = ps.findById(pid);
		req.setAttribute("bean", product);
		
		//浏览记录
		Cookie cookie = CookUtils.getCookieByName("history", req.getCookies());
		if(cookie == null) {
			Cookie c = new Cookie("history",pid);
			c.setPath("/");
			c.setMaxAge(7*24*60*60);
			res.addCookie(c);
		}else {
			String value = cookie.getValue();
			if(!value.contains(pid)) {
				StringBuilder sb = new StringBuilder(value);
				value = sb.append("-"+pid).toString();
				String[] split = value.split("-");
				if(split.length > 6 ) {
					int index = value.indexOf("-");
					value = value.substring(index+1);
				}
			}
			Cookie ck = new Cookie("history",value);
			ck.setPath("/");
			ck.setMaxAge(7*24*60*60);
			res.addCookie(ck);
		}
		
		
		
		req.getRequestDispatcher("jsp/product_info.jsp").forward(req, res);
	}
	
}
