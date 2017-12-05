package com.cn.store.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cn.store.domain.Product;
import com.cn.store.service.ProductService;
import com.cn.store.service.impl.ProductServiceImpl;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("*.index")
public class IndexServlet extends HttpServlet {
	private ProductService ps = new ProductServiceImpl();
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		if("/toIndex.index".equals(req.getServletPath())) {
			toIndex(req,res);
		}else {
			System.out.println(req.getServletPath());
		}
	}

	private void toIndex(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		List<Product> hostList = ps.findByHot();
		List<Product> newList = ps.findByNew();
		
		req.setAttribute("hostList", hostList);
		req.setAttribute("newList", newList);
		
		req.getRequestDispatcher("jsp/index.jsp").forward(req, res);
		
	}
	

}
