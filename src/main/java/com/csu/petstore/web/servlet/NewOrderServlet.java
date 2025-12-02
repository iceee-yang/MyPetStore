package com.csu.petstore.web.servlet;

import com.csu.petstore.domain.Account;
import com.csu.petstore.domain.Cart;
import com.csu.petstore.domain.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class NewOrderServlet extends HttpServlet {

    private static final String CONFIRM_ORDER_FORM = "/WEB-INF/jsp/order/confirmOrder.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        // 获取登录账户和购物车
        Account loginAccount = (Account) session.getAttribute("loginAccount");
        Cart cart = (Cart) session.getAttribute("cart");

        // 创建订单对象
        Order order = new Order();
        order.initOrder(loginAccount, cart);

        // 从表单获取支付信息
        String cardType = req.getParameter("order.cardType");
        String creditCard = req.getParameter("order.creditCard");
        String expiryDate = req.getParameter("order.expiryDate");

        // 从表单获取账单地址
        String billToFirstName = req.getParameter("order.billToFirstName");
        String billToLastName = req.getParameter("order.billToLastName");
        String billAddress1 = req.getParameter("order.billAddress1");
        String billAddress2 = req.getParameter("order.billAddress2");
        String billCity = req.getParameter("order.billCity");
        String billState = req.getParameter("order.billState");
        String billZip = req.getParameter("order.billZip");
        String billCountry = req.getParameter("order.billCountry");

        // 设置支付信息
        order.setCardType(cardType);
        order.setCreditCard(creditCard);
        order.setExpiryDate(expiryDate);

        // 设置账单地址
        order.setBillToFirstName(billToFirstName);
        order.setBillToLastName(billToLastName);
        order.setBillAddress1(billAddress1);
        order.setBillAddress2(billAddress2);
        order.setBillCity(billCity);
        order.setBillState(billState);
        order.setBillZip(billZip);
        order.setBillCountry(billCountry);

        // 检查是否需要不同的配送地址
        String shippingAddressRequired = req.getParameter("shippingAddressRequired");
        if (shippingAddressRequired != null) {
            // 如果勾选了，可以在这里处理配送地址
            // 目前使用账户默认地址，您可以后续扩展
        }

        // 将订单保存到session中
        session.setAttribute("order", order);

        // 转发到确认订单页面
        req.getRequestDispatcher(CONFIRM_ORDER_FORM).forward(req, resp);
    }
}