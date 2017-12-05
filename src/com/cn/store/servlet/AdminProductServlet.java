package com.cn.store.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.cn.store.domain.Category;
import com.cn.store.domain.PageBean;
import com.cn.store.domain.Product;
import com.cn.store.service.CategoryService;
import com.cn.store.service.ProductService;
import com.cn.store.service.impl.CategoryServiceImpl;
import com.cn.store.service.impl.ProductServiceImpl;
import com.cn.store.utils.UUIDUtils;

/**
 * Servlet implementation class AdminProductServlet
 */
@WebServlet("*.adminPro")
public class AdminProductServlet extends HttpServlet {
	private ProductService ps = new ProductServiceImpl();
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		if("/findByPage.adminPro".equals(req.getServletPath())) {
			findByPage(req,res);
		}if("/toAdd.adminPro".equals(req.getServletPath())) {
			toAdd(req,res);
		}if("/add.adminPro".equals(req.getServletPath())) {
//			System.out.println("come in");
			add(req,res);
		}
	}

	private void add(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		DiskFileItemFactory dfif = new DiskFileItemFactory();
		ServletFileUpload fileUpload = new ServletFileUpload(dfif);
		try {
			Map<String, String> map = new HashMap<String,String>();
			List<FileItem> list = fileUpload.parseRequest(req);
			String fileName = null;
			for (FileItem fi : list) {
				if(fi.isFormField()) {
					String name = fi.getFieldName();
					String value = fi.getString("utf-8");
					map.put(name, value);
				}else {
					fileName = fi.getName();
					InputStream is = fi.getInputStream();
					String path = getServletContext().getRealPath("/products/1");
					FileOutputStream fos = new FileOutputStream(path+"/"+fileName);
					IOUtils.copy(is, fos);
				}
			}
			
			Product product = new Product();
			BeanUtils.populate(product, map);
			product.setPid(UUIDUtils.getId());
			product.setPdate(new Date().toLocaleString());
			product.setPflag(0);
			product.setPimage("products/1/"+fileName);
			/*
			Category category = new Category();
			category.setCid(map.get("cid"));
			
			product.setCid(cid);*/
//			System.out.println(product);
			ps.save(product);
			
			res.sendRedirect("findByPage.adminPro");
			
		} catch (FileUploadException | IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void toAdd(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		CategoryService cs = new CategoryServiceImpl();
		List<Category> list = cs.findAll();
		req.setAttribute("list", list);
		req.getRequestDispatcher("admin/product/add.jsp").forward(req, res);
		
	}

	private void findByPage(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException  {
		int pageNumber = 1;
		try {
			pageNumber = Integer.parseInt(req.getParameter("pageNumber"));
		} catch (NumberFormatException e) {
			
		}
		int pageSize = 10;
		
		PageBean<Product> pageBean = ps.findByPage(pageNumber, pageSize);
		req.setAttribute("pageBean", pageBean);
		req.getRequestDispatcher("admin/product/list.jsp").forward(req, res);
	}
	
}
