package com.cn.store.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cn.store.domain.Cart;
import com.cn.store.domain.Product;
import com.cn.store.service.ProductService;
import com.cn.store.service.impl.ProductServiceImpl;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("*.cart")
public class CartServlet extends HttpServlet {
	private ProductService ps = new ProductServiceImpl();
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		if("/addToCart.cart".equals(req.getServletPath())) {
			addToCart(req,res);
		}else if("/clearCart.cart".equals(req.getServletPath())) {
			clearCart(req,res);
		}else if("/removeCart.cart".equals(req.getServletPath())) {
			removeCart(req,res);
		}
	}

	private void removeCart(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		String id = req.getParameter("pid");
		Cart cart = getCart(req.getSession());
		cart.removeCart(id);
		res.sendRedirect("jsp/cart.jsp");
	}

	private void clearCart(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException {
		Cart cart = getCart(req.getSession());
		cart.clearCart();
		res.sendRedirect("jsp/cart.jsp");
		
	}

	private void addToCart(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		String pid = req.getParameter("pid");
		int count = Integer.parseInt(req.getParameter("quantity"));
		Product product = ps.findById(pid);
		Cart cart = getCart(req.getSession());
		cart.addCart(product, count);
		res.sendRedirect("jsp/cart.jsp");
	}
	
	private Cart getCart(HttpSession session) {
		Cart cart =(Cart) session.getAttribute("cart");
		if(cart == null) {
			cart  = new Cart();
			session.setAttribute("cart", cart);
		}
		return cart;
		
	}
	
}
