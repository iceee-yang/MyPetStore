package com.csu.petstore.web.servlet;

import com.csu.petstore.domain.Account;
import com.csu.petstore.domain.Order;
import com.csu.petstore.service.CartService;
import com.csu.petstore.service.CatalogService;
import com.csu.petstore.service.OrderService;
import com.csu.petstore.service.UserActionLogService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class ConfirmOrderServlet extends HttpServlet {

    private static final String VIEW_ORDER_FORM = "/WEB-INF/jsp/order/viewOrder.jsp";

    private OrderService orderService;
    private CartService cartService;
    private UserActionLogService logService;

    public ConfirmOrderServlet() {
        this.orderService = new OrderService();
        this.cartService = new CartService();
        this.logService = new UserActionLogService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // 获取订单和登录账户
        Order order = (Order) session.getAttribute("order");
        Account loginAccount = (Account) session.getAttribute("loginAccount");

        if (order != null && loginAccount != null) {
            // 保存订单到数据库
            orderService.insertOrder(order);

            // 【修改】清空Session中的购物车
            session.removeAttribute("cart");

            // 【新增】清空数据库中的购物车
            cartService.clearCart(loginAccount.getUsername());

            // 订单保存成功后，跳转到查看订单页面
            req.getRequestDispatcher(VIEW_ORDER_FORM).forward(req, resp);
        } else {
            if (loginAccount == null) {
                logService.log(req, "CONFIRM_ORDER", "ORDER", null, "FAIL", "not login");
                resp.sendRedirect("mainForm");
                return;
            }

            if (order == null) {
                logService.log(req, "CONFIRM_ORDER", "ORDER", null, "FAIL", "order is null");
                resp.sendRedirect("mainForm");
                return;
            }

        }
    }
}