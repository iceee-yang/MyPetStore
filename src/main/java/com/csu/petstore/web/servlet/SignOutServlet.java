package com.csu.petstore.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class SignOutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // 清除登录账户信息
        session.removeAttribute("loginAccount");

        // 可选:清除购物车(根据业务需求决定)
        // session.removeAttribute("cart");

        // 或者直接销毁整个session
        // session.invalidate();

        // 重定向到主页
        resp.sendRedirect("mainForm");
    }
}
