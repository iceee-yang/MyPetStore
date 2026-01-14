package com.csu.petstore.web.servlet;

import com.csu.petstore.service.AccountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CheckUsernameServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");

        String username = req.getParameter("username");
        username = username == null ? null : username.trim();

        boolean exists = false;
        if (username != null && !username.isEmpty()) {
            AccountService accountService = new AccountService();
            exists = accountService.getAccount(username) != null;
        }

        String json = "{\"ok\":true,\"exists\":" + exists + "}";
        resp.getWriter().write(json);
    }
}
