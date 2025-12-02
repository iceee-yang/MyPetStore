package com.csu.petstore.web.servlet;

import com.csu.petstore.domain.Order;
import com.csu.petstore.service.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class ViewOrderServlet extends HttpServlet {

    private static final String VIEW_ORDER_FORM = "/WEB-INF/jsp/order/viewOrder.jsp";

    private OrderService orderService;

    public ViewOrderServlet() {
        this.orderService = new OrderService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // 获取订单ID
        String orderIdStr = req.getParameter("orderId");

        if (orderIdStr != null && !orderIdStr.isEmpty()) {
            int orderId = Integer.parseInt(orderIdStr);

            // 从数据库查询订单
            Order order = orderService.getOrder(orderId);

            // 将订单保存到session
            session.setAttribute("order", order);
        }

        // 转发到查看订单页面
        req.getRequestDispatcher(VIEW_ORDER_FORM).forward(req, resp);
    }
}