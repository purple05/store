package com.cn.store.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.cn.store.domain.Category;
import com.cn.store.service.CategoryService;
import com.cn.store.service.impl.CategoryServiceImpl;
import com.cn.store.utils.UUIDUtils;

/**
 * Servlet implementation class AdminCategoryServlet
 */
@WebServlet("*.adminCate")
public class AdminCategoryServlet extends HttpServlet {
	CategoryService gs = new CategoryServiceImpl();
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		if("/findAll.adminCate".equals(req.getServletPath())) {
			findAll(req,res);
		}else if("/toAdd.adminCate".equals(req.getServletPath())) {
			toAdd(req,res);
		}else if("/add.adminCate".equals(req.getServletPath())) {
			add(req,res);
		}else if("/toEdit.adminCate".equals(req.getServletPath())) {
			toEdit(req,res);
		}else if("/edit.adminCate".equals(req.getServletPath())) {
			edit(req,res);
		}else if("/del.adminCate".equals(req.getServletPath())) {
			del(req,res);
		}
	}
	private void del(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		String cid = req.getParameter("cid");
		gs.delete(cid);
		res.sendRedirect("findAll.adminCate");
		
	}
	private void edit(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
		String cid = req.getParameter("cid");
		String cname = req.getParameter("cname");
		Category category = new Category(cid,cname);
		gs.update(category);
		
		res.sendRedirect("findAll.adminCate");
//		System.out.println(cname);
		/*try {
			BeanUtils.populate(category, req.getParameterMap());
			System.out.println(category);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
	}
	private void toEdit(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		String cid = req.getParameter("cid");
		Category category = gs.findById(cid);
		req.setAttribute("category", category);
		req.getRequestDispatcher("admin/category/edit.jsp").forward(req, res);
		
	}
	private void add(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		String cname = req.getParameter("cname");
		Category category = new Category();
		category.setCid(UUIDUtils.getId());
		category.setCname(cname);
		gs.save(category);
		res.sendRedirect("findAll.adminCate");
		
	}
	private void toAdd(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException {
		res.sendRedirect("admin/category/add.jsp");
		
	}
	private void findAll(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		List<Category> list = gs.findAll();
		req.setAttribute("list", list);
		req.getRequestDispatcher("admin/category/list.jsp").forward(req, res);
		
	}
	

}
