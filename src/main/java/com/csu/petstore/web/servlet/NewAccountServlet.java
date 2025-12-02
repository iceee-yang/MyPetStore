package com.csu.petstore.web.servlet;

import com.csu.petstore.domain.Account;
import com.csu.petstore.service.AccountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class NewAccountServlet extends HttpServlet {

    private static final String NEW_ACCOUNT_FORM = "/WEB-INF/jsp/account/newAccount.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account account = buildAccount(req);
        String repeatedPassword = req.getParameter("repeatedPassword");

        String validationMsg = validate(account, repeatedPassword);
        AccountService accountService = new AccountService();
        if (validationMsg == null && accountService.getAccount(account.getUsername()) != null) {
            validationMsg = "用户名已存在";
        }

        if (validationMsg != null) {
            req.setAttribute("registerMsg", validationMsg);
            req.setAttribute("account", account);
            req.getRequestDispatcher(NEW_ACCOUNT_FORM).forward(req, resp);
            return;
        }

        accountService.insertAccount(account);
        Account loginAccount = accountService.getAccount(account.getUsername(), account.getPassword());
        if (loginAccount != null) {
            loginAccount.setPassword(null);
            HttpSession session = req.getSession();
            session.setAttribute("loginAccount", loginAccount);
        }
        resp.sendRedirect("mainForm");
    }

    private Account buildAccount(HttpServletRequest req) {
        Account account = new Account();
        account.setUsername(req.getParameter("username"));
        account.setPassword(req.getParameter("password"));
        account.setFirstName(req.getParameter("account.firstName"));
        account.setLastName(req.getParameter("account.lastName"));
        account.setEmail(req.getParameter("account.email"));
        account.setPhone(req.getParameter("account.phone"));
        account.setAddress1(req.getParameter("account.address1"));
        account.setAddress2(req.getParameter("account.address2"));
        account.setCity(req.getParameter("account.city"));
        account.setState(req.getParameter("account.state"));
        account.setZip(req.getParameter("account.zip"));
        account.setCountry(req.getParameter("account.country"));
        String favouriteCategory = req.getParameter("account.favouriteCategoryId");
        account.setFavouriteCategoryId(favouriteCategory == null ? null : favouriteCategory.toUpperCase());
        account.setLanguagePreference(req.getParameter("account.languagePreference"));
        account.setListOption(req.getParameter("account.listOption") != null);
        account.setBannerOption(req.getParameter("account.bannerOption") != null);
        account.setStatus("OK");
        return account;
    }

    private String validate(Account account, String repeatedPassword) {
        if (isBlank(account.getUsername())) {
            return "用户名不能为空";
        }
        if (isBlank(account.getPassword())) {
            return "密码不能为空";
        }
        if (!account.getPassword().equals(repeatedPassword)) {
            return "两次输入的密码不一致";
        }
        if (isBlank(account.getFirstName()) || isBlank(account.getLastName())) {
            return "姓名不能为空";
        }
        if (isBlank(account.getEmail())) {
            return "邮箱不能为空";
        }
        return null;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}

