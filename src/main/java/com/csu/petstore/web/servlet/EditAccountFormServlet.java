package com.csu.petstore.web.servlet;

import com.csu.petstore.domain.Account;
import com.csu.petstore.service.AccountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class EditAccountFormServlet extends HttpServlet {

    private static final String EDIT_ACCOUNT_FORM = "/WEB-INF/jsp/account/editAccount.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Account loginAccount = session == null ? null : (Account) session.getAttribute("loginAccount");
        if (loginAccount == null) {
            resp.sendRedirect("signonForm");
            return;
        }
        // 读取完整账户信息以回显
        AccountService accountService = new AccountService();
        Account account = accountService.getAccount(loginAccount.getUsername());
        if (account == null) {
            // 若查不到，至少把用户名传过去
            account = new Account();
            account.setUsername(loginAccount.getUsername());
        }
        req.setAttribute("account", account);
        req.getRequestDispatcher(EDIT_ACCOUNT_FORM).forward(req, resp);
    }
}

