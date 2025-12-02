package com.csu.petstore.web.servlet;

import com.csu.petstore.domain.Order;
import com.csu.petstore.service.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class ConfirmOrderServlet extends HttpServlet {

    private static final String VIEW_ORDER_FORM = "/WEB-INF/jsp/order/viewOrder.jsp";

    private OrderService orderService;

    public ConfirmOrderServlet() {
        this.orderService = new OrderService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // 获取订单
        Order order = (Order) session.getAttribute("order");

        if (order != null) {
            // 保存订单到数据库
            orderService.insertOrder(order);

            // 清空购物车
            session.removeAttribute("cart");

            // 订单保存成功后，跳转到查看订单页面
            req.getRequestDispatcher(VIEW_ORDER_FORM).forward(req, resp);
        } else {
            // 如果订单为空，重定向到主页
            resp.sendRedirect("mainForm");
        }
    }
}