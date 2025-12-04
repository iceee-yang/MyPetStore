package com.csu.petstore.web.servlet;

import com.csu.petstore.domain.Account;
import com.csu.petstore.domain.Cart;
import com.csu.petstore.service.CartService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class ViewCartServlet extends HttpServlet {

    private static final String VIEW_CART = "/WEB-INF/jsp/cart/cart.jsp";

    private CartService cartService;

    public ViewCartServlet() {
        cartService = new CartService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Account loginAccount = (Account) session.getAttribute("loginAccount");

        // ✅ 修改：未登录时重定向到登录页面
        if (loginAccount == null) {
            resp.sendRedirect("signonForm");
            return;
        }

        // 从数据库加载购物车
        String username = loginAccount.getUsername();
        Cart cart = cartService.getCartByUsername(username);
        session.setAttribute("cart", cart);

        // 转发到购物车页面
        req.getRequestDispatcher(VIEW_CART).forward(req, resp);
    }
}