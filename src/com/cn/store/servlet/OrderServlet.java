package com.cn.store.servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cn.store.domain.Cart;
import com.cn.store.domain.CartItem;
import com.cn.store.domain.Order;
import com.cn.store.domain.OrderItem;
import com.cn.store.domain.PageBean;
import com.cn.store.domain.User;
import com.cn.store.service.OrderService;
import com.cn.store.service.impl.OrderServiceImpl;
import com.cn.store.utils.PaymentUtil;
import com.cn.store.utils.UUIDUtils;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("*.order")
public class OrderServlet extends HttpServlet {

	private OrderService os = new OrderServiceImpl();

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		if ("/saveOrder.order".equals(req.getServletPath())) {
			saveOrder(req, res);
		} else if ("/findByUid.order".equals(req.getServletPath())) {
			findByUid(req, res);
		} else if ("/findByOid.order".equals(req.getServletPath())) {
			findByOid(req, res);
		} else if ("/payOrder.order".equals(req.getServletPath())) {
			payOrder(req, res);
		} else if ("/callback.order".equals(req.getServletPath())) {
			callback(req, res);
		} else {
			System.out.println(req.getServletPath());
		}

	}

	private void callback(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try {
			String p1_MerId = req.getParameter("p1_MerId");
			String r0_Cmd = req.getParameter("r0_Cmd");
			String r1_Code = req.getParameter("r1_Code");
			String r2_TrxId = req.getParameter("r2_TrxId");
			String r3_Amt = req.getParameter("r3_Amt");
			String r4_Cur = req.getParameter("r4_Cur");
			String r5_Pid = req.getParameter("r5_Pid");
			String r6_Order = req.getParameter("r6_Order");
			String r7_Uid = req.getParameter("r7_Uid");
			String r8_MP = req.getParameter("r8_MP");
			String r9_BType = req.getParameter("r9_BType");
			String rb_BankId = req.getParameter("rb_BankId");
			String ro_BankOrderId = req.getParameter("ro_BankOrderId");
			String rp_PayDate = req.getParameter("rp_PayDate");
			String rq_CardNo = req.getParameter("rq_CardNo");
			String ru_Trxtime = req.getParameter("ru_Trxtime");
			// 身份校验 --- 判断是不是支付公司通知你
			String hmac = req.getParameter("hmac");
			String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");

			// 自己对上面数据进行加密 --- 比较支付公司发过来hamc
			boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur,
					r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType, keyValue);
			if (isValid) {
				// 响应数据有效
				if (r9_BType.equals("1")) {
					// 浏览器重定向
					System.out.println("111");
					req.setAttribute("msg", "您的订单号为:" + r6_Order + ",金额为:" + r3_Amt + "已经支付成功,等待发货~~");

				} else if (r9_BType.equals("2")) {
					// 服务器点对点 --- 支付公司通知你
					System.out.println("付款成功！222");
					// 修改订单状态 为已付款
					// 回复支付公司
					res.getWriter().print("success");
				}

				// 修改订单状态
//				OrderService s = (OrderService) BeanFactory.getBean("OrderService");
				Order order = os.findByOid(r6_Order);
				order.setState(2);

				os.update(order);

			} else {
				// 数据无效
				System.out.println("数据被篡改！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("msg", "支付失败");
		}
		
		req.getRequestDispatcher("jsp/msg.jsp").forward(req, res);
		// return "/jsp/msg.jsp";

	}

	private void payOrder(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String oid = req.getParameter("oid");
		String name = req.getParameter("name");
		String address = req.getParameter("address");
		String telephone = req.getParameter("telephone");
		// String pd_FrpId = req.getParameter("pd_FrpId");

		Order order = os.findByOid(oid);
		order.setAddress(address);
		order.setName(name);
		order.setTelephone(telephone);
		os.update(order);

		// 组织发送支付公司需要哪些数据
		String pd_FrpId = req.getParameter("pd_FrpId");
		String p0_Cmd = "Buy";
		String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
		String p2_Order = oid;
		String p3_Amt = "0.01";
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		// 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
		// 第三方支付可以访问网址
		String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("responseURL");
		String p9_SAF = "";
		String pa_MP = "";
		String pr_NeedResponse = "1";
		// 加密hmac 需要密钥
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc,
				p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);

		// 发送给第三方
		StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
		sb.append("p0_Cmd=").append(p0_Cmd).append("&");
		sb.append("p1_MerId=").append(p1_MerId).append("&");
		sb.append("p2_Order=").append(p2_Order).append("&");
		sb.append("p3_Amt=").append(p3_Amt).append("&");
		sb.append("p4_Cur=").append(p4_Cur).append("&");
		sb.append("p5_Pid=").append(p5_Pid).append("&");
		sb.append("p6_Pcat=").append(p6_Pcat).append("&");
		sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
		sb.append("p8_Url=").append(p8_Url).append("&");
		sb.append("p9_SAF=").append(p9_SAF).append("&");
		sb.append("pa_MP=").append(pa_MP).append("&");
		sb.append("pd_FrpId=").append(pd_FrpId).append("&");
		sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
		sb.append("hmac=").append(hmac);

		res.sendRedirect(sb.toString());

	}

	private void findByOid(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String oid = req.getParameter("oid");
		Order order = os.findByOid(oid);
		req.setAttribute("order", order);
		req.getRequestDispatcher("jsp/order_info.jsp").forward(req, res);

	}

	private void findByUid(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		int pageNum = 1;
		String pageNumber = req.getParameter("pageNumber");
		if (pageNumber != null) {
			pageNum = Integer.parseInt(pageNumber);
		}

		int pageSize = 3;
		User user = (User) req.getSession().getAttribute("user");

		PageBean<Order> pageBean = os.findByUid(user, pageNum, pageSize);
		req.setAttribute("pageBean", pageBean);
		req.getRequestDispatcher("jsp/order_list.jsp").forward(req, res);
	}

	private void saveOrder(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Cart cart = (Cart) req.getSession().getAttribute("cart");
		User user = (User) req.getSession().getAttribute("user");
		/*if (user == null) {
			req.setAttribute("msg", "请先登录");
			req.getRequestDispatcher("jsp/msg.jsp").forward(req, res);
		}*/

		Order order = new Order();
		order.setOid(UUIDUtils.getId());
		order.setState(1);
		order.setOrdertime(new Date(new java.util.Date().getTime()));
		order.setTotal(cart.getTotal());
		order.setUser(user);

		for (CartItem ci : cart.getCartItems()) {
			OrderItem oi = new OrderItem();
			oi.setItemid(UUIDUtils.getId());
			oi.setCount(ci.getCount());
			oi.setSubtotal(ci.getSubtotal());
			oi.setProduct(ci.getProduct());
			oi.setOrder(order);

			order.getList().add(oi);
		}

		os.save(order);
		cart.clearCart();
		req.setAttribute("order", order);

		req.getRequestDispatcher("jsp/order_info.jsp").forward(req, res);

	}

}
