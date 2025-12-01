package com.csu.petstore.web.servlet;

import com.csu.petstore.domain.Account;
import com.csu.petstore.domain.Order;
import com.csu.petstore.service.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

public class ListOrdersServlet extends HttpServlet {

    private static final String LIST_ORDERS_FORM = "/WEB-INF/jsp/order/listOrders.jsp";

    private OrderService orderService;

    public ListOrdersServlet() {
        this.orderService = new OrderService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // 获取登录账户
        Account loginAccount = (Account) session.getAttribute("loginAccount");

        if (loginAccount != null) {
            // 查询该用户的所有订单
            List<Order> orderList = orderService.getOrdersByUsername(loginAccount.getUsername());

            // 将订单列表保存到request
            req.setAttribute("orderList", orderList);

            // 转发到订单列表页面
            req.getRequestDispatcher(LIST_ORDERS_FORM).forward(req, resp);
        } else {
            // 未登录，跳转到登录页面
            resp.sendRedirect("signonForm");
        }
    }
}