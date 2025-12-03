package com.csu.petstore.web.servlet;

import com.csu.petstore.domain.Account;
import com.csu.petstore.service.AccountService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class EditAccountServlet extends HttpServlet {

    private static final String EDIT_ACCOUNT_FORM = "/WEB-INF/jsp/account/editAccount.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Account loginAccount = session == null ? null : (Account) session.getAttribute("loginAccount");
        if (loginAccount == null) {
            resp.sendRedirect("signonForm");
            return;
        }

        Account account = buildAccount(req, loginAccount.getUsername());
        String repeatedPassword = req.getParameter("repeatedPassword");

        String validationMsg = validate(account, repeatedPassword);
        if (validationMsg != null) {
            req.setAttribute("editMsg", validationMsg);
            req.setAttribute("account", account);
            req.getRequestDispatcher(EDIT_ACCOUNT_FORM).forward(req, resp);
            return;
        }

        AccountService accountService = new AccountService();
        accountService.updateAccount(account);

        Account refreshed = accountService.getAccount(loginAccount.getUsername());
        if (refreshed != null) {
            // 不在 session 中保存密码
            refreshed.setPassword(null);
            session.setAttribute("loginAccount", refreshed);
        }

        resp.sendRedirect("editAccountForm");
    }

    private Account buildAccount(HttpServletRequest req, String username) {
        Account account = new Account();
        account.setUsername(username);
        String password = req.getParameter("password");
        if (password != null && !password.isEmpty()) {
            account.setPassword(password);
        }
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
        // 密码为空表示不修改密码；如果填写了则需要重复校验
        if (account.getPassword() != null && !account.getPassword().isEmpty()) {
            if (repeatedPassword == null || !account.getPassword().equals(repeatedPassword)) {
                return "两次输入的密码不一致";
            }
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

