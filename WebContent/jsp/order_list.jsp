<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>会员登录</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css" />

		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
			}
			
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
		</style>
	</head>

	<body>

			<div class="container-fluid">
			
			<%@include file="/jsp/head.jsp" %>

		<div class="container">
			<div class="row">

				<div style="margin:0 auto; margin-top:10px;width:950px;">
					<strong>我的订单</strong>
					<table class="table table-bordered">
						<c:forEach items="${pageBean.data }" var="order"> 
						
							<tbody>
								<tr class="success">
									<th colspan="5">${order.oid } </th>
								</tr>
								<tr class="warning">
									<th>图片</th>
									<th>商品</th>
									<th>价格</th>
									<th>数量</th>
									<th>小计</th>
								</tr>
								
								<c:forEach items="${order.list }" var="orderItem">
								
									<tr class="active">
										<td width="60" width="40%">
											<input type="hidden" name="id" value="22">
											<img src="${pageContext.request.contextPath}/${orderItem.product.pimage}" width="70" height="60">
										</td>
										<td width="30%">
											<a target="_blank"> ${orderItem.product.pname}</a>
										</td>
										<td width="20%">
											￥${orderItem.product.shop_price}
										</td>
										<td width="10%">
											${orderItem.count }
										</td>
										<td width="15%">
											<span class="subtotal">￥${orderItem.subtotal}</span>
										</td>
									</tr>
								</c:forEach>
								<tr>
									<td colspan="2">
										订单编号:${order.oid }
									</td>
									<td colspan="2">
										<c:if test="${order.state == 1 }">
											状态:<a href="${pageContext.request.contextPath }/findByOid.order?oid=${order.oid}">去付款</a>
										</c:if>
										<c:if test="${order.state == 2 }">
											状态:等待发货
										</c:if>
										<c:if test="${order.state == 3 }">
											状态:确认收货
										</c:if>
										<c:if test="${order.state == 4 }">
											状态:订单结束
										</c:if>
									</td>
									<td>
										<font color="#b00">总金额:￥${order.total }</font>
									</td>
								</tr>
							</tbody>
						
						</c:forEach>
						
					</table>
				</div>
			</div>
			<div style="text-align: center;">
				<ul class="pagination">
					<!-- 判断是否是第一页 -->
				<c:if test="${pageBean.pageNumber == 1 }">
					<li class="disabled">
						<a href="javascript:void(0)" aria-label="Previous">
							<span aria-hidden="true">&laquo;</span>
						</a>
					</li>
				</c:if>
				
				<!-- 不是第一页 -->
				<c:if test="${pageBean.pageNumber != 1 }">
					<li>
						<a href="${pageContext.request.contextPath }/findByUid.order?pageNumber=${pageBean.pageNumber-1}" aria-label="Previous">
							<span aria-hidden="true">&laquo;</span>
						</a>
					</li>
				</c:if>
				
				<!-- 展示所有页码 -->
				<c:forEach begin="1" end="${pageBean.totalPage }" var = "n">
					<!-- 判断是否是当前页 -->
					<c:if test="${pageBean.pageNumber == n }">
						<li class="active"><a href="javascript:void(0)">${n }</a></li>
					</c:if>
					<c:if test="${pageBean.pageNumber != n }">
						<li><a href="${pageContext.request.contextPath }/findByUid.order?pageNumber=${n}">${n }</a></li>
					</c:if>
				</c:forEach>
				
				<!-- 判断是否是最后一页 -->
				<c:if test="${pageBean.pageNumber == pageBean.totalPage }">
					<li  class="disabled">
						<a href="javascript:void(0)" aria-label="Next">
							<span aria-hidden="true">&raquo;</span>
						</a>
					</li>
				</c:if>
				<c:if test="${pageBean.pageNumber != pageBean.totalPage }">
					<li>
						<a href="${pageContext.request.contextPath }/findByUid.order?pageNumber=${pageBean.pageNumber+1}" aria-label="Next">
							<span aria-hidden="true">&raquo;</span>
						</a>
					</li>
				</c:if>
				</ul>
			</div>
		</div>

		<div style="margin-top:50px;">
			<img src="${pageContext.request.contextPath}/image/footer.jpg" width="100%" height="78" alt="我们的优势" title="我们的优势" />
		</div>

		<div style="text-align: center;margin-top: 5px;">
			<ul class="list-inline">
				<li><a>关于我们</a></li>
				<li><a>联系我们</a></li>
				<li><a>招贤纳士</a></li>
				<li><a>法律声明</a></li>
				<li><a>友情链接</a></li>
				<li><a target="_blank">支付方式</a></li>
				<li><a target="_blank">配送方式</a></li>
				<li><a>服务声明</a></li>
				<li><a>广告声明</a></li>
			</ul>
		</div>
		<div style="text-align: center;margin-top: 5px;margin-bottom:20px;">
			Copyright &copy; 2005-2016 狗蛋商城 版权所有
		</div>
		</div>
	</body>

</html>