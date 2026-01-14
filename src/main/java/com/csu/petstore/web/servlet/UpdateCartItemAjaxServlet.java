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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;

public class UpdateCartItemAjaxServlet extends HttpServlet {

    private final CartService cartService;

    public UpdateCartItemAjaxServlet() {
        this.cartService = new CartService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");

        HttpSession session = req.getSession();
        Account loginAccount = (Account) session.getAttribute("loginAccount");

        if (loginAccount == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("{\"ok\":false,\"message\":\"NOT_LOGIN\"}");
            return;
        }

        String itemId = req.getParameter("itemId");
        String quantityStr = req.getParameter("quantity");

        if (itemId == null || itemId.trim().isEmpty() || quantityStr == null || quantityStr.trim().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"ok\":false,\"message\":\"MISSING_PARAM\"}");
            return;
        }

        itemId = itemId.trim();

        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr.trim());
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"ok\":false,\"message\":\"INVALID_QUANTITY\"}");
            return;
        }

        String username = loginAccount.getUsername();

        cartService.updateCartItemQuantity(username, itemId, quantity);

        Cart cart = cartService.getCartByUsername(username);
        session.setAttribute("cart", cart);

        CartItem updatedItem = null;
        Iterator<CartItem> it = cart.getAllCartItems();
        while (it.hasNext()) {
            CartItem ci = it.next();
            if (ci.getItem() != null && itemId.equals(ci.getItem().getItemId())) {
                updatedItem = ci;
                break;
            }
        }

        String itemTotal = "0.00";
        int itemQuantity = 0;
        if (updatedItem != null) {
            itemQuantity = updatedItem.getQuantity();
            BigDecimal t = updatedItem.getTotal();
            if (t != null) {
                itemTotal = t.setScale(2, RoundingMode.HALF_UP).toPlainString();
            }
        }

        String subTotal = "0.00";
        BigDecimal st = cart.getSubTotal();
        if (st != null) {
            subTotal = st.setScale(2, RoundingMode.HALF_UP).toPlainString();
        }

        String json = "{"
                + "\"ok\":true,"
                + "\"itemId\":\"" + escapeJson(itemId) + "\","
                + "\"quantity\":" + itemQuantity + ","
                + "\"itemTotal\":\"" + itemTotal + "\","
                + "\"subTotal\":\"" + subTotal + "\","
                + "\"numberOfItems\":" + cart.getNumberOfItems() +
                "}";

        resp.getWriter().write(json);
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
