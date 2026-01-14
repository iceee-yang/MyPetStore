package com.csu.petstore.web.servlet;

import com.csu.petstore.domain.Account;
import com.csu.petstore.domain.Order;
import com.csu.petstore.service.CartService;
import com.csu.petstore.service.OrderService;
import com.csu.petstore.service.UserActionLogService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

public class ConfirmOrderAjaxServlet extends HttpServlet {

    private OrderService orderService;
    private CartService cartService;
    private UserActionLogService logService;

    public ConfirmOrderAjaxServlet() {
        this.orderService = new OrderService();
        this.cartService = new CartService();
        this.logService = new UserActionLogService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        resp.setContentType("application/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        HttpSession session = req.getSession();
        Order order = (Order) session.getAttribute("order");
        Account loginAccount = (Account) session.getAttribute("loginAccount");
        
        PrintWriter out = resp.getWriter();
        
        // 检查登录状态
        if (loginAccount == null) {
            out.print("{\"success\":false,\"message\":\"Please sign in first.\"}");
            logService.log(req, "CONFIRM_ORDER_AJAX", "ORDER", null, "FAIL", "not login");
            out.flush();
            return;
        }
        
        // 检查订单是否存在
        if (order == null) {
            out.print("{\"success\":false,\"message\":\"Order not found. Please create a new order.\"}");
            logService.log(req, "CONFIRM_ORDER_AJAX", "ORDER", null, "FAIL", "order is null");
            out.flush();
            return;
        }
        
        try {
            // 保存订单到数据库
            orderService.insertOrder(order);
            
            // 清空Session中的购物车
            session.removeAttribute("cart");
            
            // 清空数据库中的购物车
            cartService.clearCart(loginAccount.getUsername());
            
            // 记录成功日志
            logService.log(req, "CONFIRM_ORDER_AJAX", "ORDER", String.valueOf(order.getOrderId()), "SUCCESS", null);
            
            // 返回成功响应
            out.print("{\"success\":true,\"orderId\":" + order.getOrderId() + ",\"message\":\"Order created successfully.\"}");
            
            // 从session中移除订单（可选，如果需要保留可以不移除）
            // session.removeAttribute("order");
            
        } catch (Exception e) {
            e.printStackTrace();
            String errorMsg = escapeJson(e.getMessage());
            out.print("{\"success\":false,\"message\":\"Error while saving order: " + errorMsg + "\"}");
            logService.log(req, "CONFIRM_ORDER_AJAX", "ORDER", null, "FAIL", e.getMessage());
        }
        
        out.flush();
    }
    
    // JSON字符串转义
    private String escapeJson(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
}
