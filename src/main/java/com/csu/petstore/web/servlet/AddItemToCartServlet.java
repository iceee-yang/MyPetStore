package com.csu.petstore.web.servlet;

import com.csu.petstore.domain.Account;
import com.csu.petstore.domain.Cart;
import com.csu.petstore.domain.Item;
import com.csu.petstore.service.CartService;
import com.csu.petstore.service.CatalogService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AddItemToCartServlet extends HttpServlet {

    private static final String VIEW_CART = "/WEB-INF/jsp/cart/cart.jsp";
    private static final String ERROR_FORM = "/WEB-INF/jsp/common/error.jsp";

    private CatalogService catalogService;
    private CartService cartService;

    public AddItemToCartServlet() {
        catalogService = new CatalogService();
        cartService = new CartService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String workingItemId = req.getParameter("workingItemId");
        HttpSession session = req.getSession();
        Account loginAccount = (Account) session.getAttribute("loginAccount");

        // ✅ 修改：未登录时重定向到登录页面
        if (loginAccount == null) {
            resp.sendRedirect("signonForm");
            return;
        }

        // 从数据库获取商品信息
        Item item = catalogService.getItem(workingItemId);

        if (item == null) {
            String msg = "商品不存在！";
            req.setAttribute("errorMsg", msg);
            req.getRequestDispatcher(ERROR_FORM).forward(req, resp);
            return;
        }

        // 将商品添加到数据库中的购物车
        String username = loginAccount.getUsername();
        cartService.addItemToCart(username, item, catalogService.isItemInStock(workingItemId));

        // 从数据库重新加载购物车到Session
        Cart cart = cartService.getCartByUsername(username);
        session.setAttribute("cart", cart);

        // 转发到购物车页面
        req.getRequestDispatcher(VIEW_CART).forward(req, resp);
    }
}