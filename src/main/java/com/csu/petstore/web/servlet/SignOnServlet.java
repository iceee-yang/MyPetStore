package com.csu.petstore.web.servlet;

import com.csu.petstore.domain.Account;
import com.csu.petstore.domain.Cart;
import com.csu.petstore.service.AccountService;
import com.csu.petstore.service.CartService;
import com.csu.petstore.service.CatalogService;
import com.csu.petstore.service.UserActionLogService;
import com.csu.petstore.domain.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

public class SignOnServlet extends HttpServlet {

    private static final String SIGN_ON_FORM = "/WEB-INF/jsp/account/signon.jsp";

    private AccountService accountService;
    private CatalogService catalogService;
    private CartService cartService;
    private UserActionLogService logService;

    public SignOnServlet() {
        accountService = new AccountService();
        catalogService = new CatalogService();
        cartService = new CartService();
        logService = new UserActionLogService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String code = req.getParameter("code");

        HttpSession session = req.getSession();
        String vCode = (String) session.getAttribute("vCode");

//        // 验证码校验
//        if (code == null || !code.equalsIgnoreCase(vCode)) {
//            String msg = "验证码错误！";
//            req.setAttribute("signOnMsg", msg);
//            req.getRequestDispatcher(SIGN_ON_FORM).forward(req, resp);
//            return;
//        }

        // 账号密码校验
        Account loginAccount = accountService.getAccount(username, password);

        if (loginAccount == null) {
            String msg = "用户名或密码错误！";
            req.setAttribute("signOnMsg", msg);
            logService.log(req, "LOGIN", "ACCOUNT", username, "FAIL", "wrong username or password");
            req.getRequestDispatcher(SIGN_ON_FORM).forward(req, resp);
        } else {
            loginAccount.setPassword(null);
            session.setAttribute("loginAccount", loginAccount);

            logService.log(req, "LOGIN", "ACCOUNT", username, "SUCCESS", null);

            // 【新增】登录成功后，从数据库加载用户的购物车
            Cart cart = cartService.getCartByUsername(username);
            session.setAttribute("cart", cart);

            // 如果用户选择了"我喜欢的分类"，保存到session
            if (loginAccount.isBannerOption()) {
                List<Product> myList = catalogService.getProductListByCategory(loginAccount.getBannerName());
                session.setAttribute("myList", myList);
            }

            resp.sendRedirect("mainForm");
        }
    }
}