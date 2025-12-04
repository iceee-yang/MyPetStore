package com.csu.petstore.web.servlet;

import com.csu.petstore.domain.Account;
import com.csu.petstore.domain.Cart;
import com.csu.petstore.domain.CartItem;
import com.csu.petstore.service.CartService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Iterator;

public class UpdateCartServlet extends HttpServlet {

    private static final String VIEW_CART = "/WEB-INF/jsp/cart/cart.jsp";

    private CartService cartService;

    public UpdateCartServlet() {
        cartService = new CartService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Account loginAccount = (Account) session.getAttribute("loginAccount");

        // ✅ 修改：未登录时重定向到登录页面
        if (loginAccount == null) {
            resp.sendRedirect("signonForm");
            return;
        }

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }

        String username = loginAccount.getUsername();

        // 更新购物车中每个商品的数量到数据库
        Iterator<CartItem> cartItemIterator = cart.getAllCartItems();
        while (cartItemIterator.hasNext()) {
            CartItem cartItem = cartItemIterator.next();
            String itemId = cartItem.getItem().getItemId();

            try {
                int quantity = Integer.parseInt(req.getParameter(itemId));

                // 更新数据库中的商品数量
                cartService.updateCartItemQuantity(username, itemId, quantity);

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // 从数据库重新加载购物车
        cart = cartService.getCartByUsername(username);
        session.setAttribute("cart", cart);

        // 转发到购物车页面
        req.getRequestDispatcher(VIEW_CART).forward(req, resp);
    }
}