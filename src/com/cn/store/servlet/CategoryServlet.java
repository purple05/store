package com.cn.store.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cn.store.domain.Category;
import com.cn.store.service.CategoryService;
import com.cn.store.service.impl.CategoryServiceImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet("*.cate")
public class CategoryServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		if("/jsp/getcate.cate".equals(req.getServletPath())) {
			getCategory(req,res);
		}
	}

	private void getCategory(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		CategoryService gs = new CategoryServiceImpl();
		List<Category> list = gs.findAll();
		
		JSONArray json = new JSONArray().fromObject(list);
		
		res.getWriter().println(json);
		
	}
	
}
