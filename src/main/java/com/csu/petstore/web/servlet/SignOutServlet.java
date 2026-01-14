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

        // 清除购物车（Session中的临时数据）
        session.removeAttribute("cart");

        // 注意：不删除数据库中的购物车，下次登录时会重新加载

        // 重定向到主页
        resp.sendRedirect("mainForm");
    }
}