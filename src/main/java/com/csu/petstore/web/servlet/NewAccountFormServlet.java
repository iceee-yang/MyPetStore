package com.csu.petstore.web.servlet;

import com.csu.petstore.domain.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class NewAccountFormServlet extends HttpServlet {

    private static final String NEW_ACCOUNT_FORM = "/WEB-INF/jsp/account/newAccount.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getAttribute("account") == null) {
            req.setAttribute("account", new Account());
        }
        req.getRequestDispatcher(NEW_ACCOUNT_FORM).forward(req, resp);
    }
}

