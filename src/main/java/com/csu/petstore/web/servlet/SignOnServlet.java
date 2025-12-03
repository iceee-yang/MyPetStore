package com.csu.petstore.web.servlet;

import com.csu.petstore.domain.Account;
import com.csu.petstore.domain.Product;
import com.csu.petstore.service.AccountService;
import com.csu.petstore.service.CatalogService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

public class SignOnServlet extends HttpServlet {

    private static final String SIGN_ON_FORM = "/WEB-INF/jsp/account/signon.jsp";

    private String username;
    private String password;

    private String msg;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.username = req.getParameter("username");
        this.password = req.getParameter("password");
        String captcha = req.getParameter("captcha");

        // 校验验证码（不区分大小写）
        HttpSession session = req.getSession();
        Object sessionCodeObj = session.getAttribute(CaptchaServlet.SESSION_KEY);
        String sessionCode = sessionCodeObj == null ? null : sessionCodeObj.toString();
        if (sessionCode == null || captcha == null ||
                !sessionCode.equalsIgnoreCase(captcha.trim())) {
            this.msg = "验证码错误";
            req.setAttribute("signOnMsg", this.msg);
            req.getRequestDispatcher(SIGN_ON_FORM).forward(req, resp);
            return;
        }

        //校验用户输入的正确性
        if(!validate()){
            req.setAttribute("signOnMsg", this.msg);
            req.getRequestDispatcher(SIGN_ON_FORM).forward(req, resp);
        }
        else {
            AccountService accountService = new AccountService();
            Account loginAccount = accountService.getAccount(username, password);
            if(loginAccount == null){
                this.msg = "用户名或密码错误";
                req.setAttribute("signOnMsg", this.msg);
                req.getRequestDispatcher(SIGN_ON_FORM).forward(req, resp);
            }else{
                loginAccount.setPassword(null);
                // 这里直接复用上面创建的 session
                session.setAttribute("loginAccount", loginAccount);

                if(loginAccount.isListOption()){
                    CatalogService catalogService = new CatalogService();
                    List<Product> myList = catalogService.getProductListByCategory(loginAccount.getFavouriteCategoryId());
                    session.setAttribute("myList", myList);
                }
                resp.sendRedirect("mainForm");
            }
        }
    }

    private boolean validate() {
        if (this.username == null || this.username.equals("")) {
            this.msg = "用户名不能为空";
            return false;
        }
        if (this.password == null || this.password.equals("")) {
            this.msg = "密码不能为空";
            return false;
        }
        return true;
    }
}
