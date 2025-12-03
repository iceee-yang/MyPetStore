package com.csu.petstore.web.servlet;

import com.csu.petstore.domain.Cart;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class ViewCartServlet extends HttpServlet {

    private static final String CART_FORM = "/WEB-INF/jsp/cart/cart.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        // 如果购物车不存在,创建一个空购物车
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        // 转发到购物车页面
        req.getRequestDispatcher(CART_FORM).forward(req, resp);
    }
}