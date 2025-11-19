package com.csu.petstore.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Order implements Serializable {
    private static final long serialVersionUID = 6321792448424424931L;
    private int orderId;
    private String username;
    private Date orderDate;
    private String shipAddress1;
    private String shipAddress2;
    private String shipCity;
    private String shipState;
    private String shipZip;
    private String shipCountry;
    private String billAddress1;
    private String billAddress2;
    private String billCity;
    private String billState;
    private String billZip;
    private String billCountry;
    private String courier;
    private BigDecimal totalPrice;
    private String billToFirstName;
    private String billToLastName;
    private String shipToFirstName;
    private String shipToLastName;
    private String creditCard;
    private String expiryDate;
    private String cardType;
    private String locale;
    private String status;
    private List<LineItem> lineItems = new ArrayList();

    public Order() {
    }

    public int getOrderId() {
        return this.orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getOrderDate() {
        return this.orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getShipAddress1() {
        return this.shipAddress1;
    }

    public void setShipAddress1(String shipAddress1) {
        this.shipAddress1 = shipAddress1;
    }

    public String getShipAddress2() {
        return this.shipAddress2;
    }

    public void setShipAddress2(String shipAddress2) {
        this.shipAddress2 = shipAddress2;
    }

    public String getShipCity() {
        return this.shipCity;
    }

    public void setShipCity(String shipCity) {
        this.shipCity = shipCity;
    }

    public String getShipState() {
        return this.shipState;
    }

    public void setShipState(String shipState) {
        this.shipState = shipState;
    }

    public String getShipZip() {
        return this.shipZip;
    }

    public void setShipZip(String shipZip) {
        this.shipZip = shipZip;
    }

    public String getShipCountry() {
        return this.shipCountry;
    }

    public void setShipCountry(String shipCountry) {
        this.shipCountry = shipCountry;
    }

    public String getBillAddress1() {
        return this.billAddress1;
    }

    public void setBillAddress1(String billAddress1) {
        this.billAddress1 = billAddress1;
    }

    public String getBillAddress2() {
        return this.billAddress2;
    }

    public void setBillAddress2(String billAddress2) {
        this.billAddress2 = billAddress2;
    }

    public String getBillCity() {
        return this.billCity;
    }

    public void setBillCity(String billCity) {
        this.billCity = billCity;
    }

    public String getBillState() {
        return this.billState;
    }

    public void setBillState(String billState) {
        this.billState = billState;
    }

    public String getBillZip() {
        return this.billZip;
    }

    public void setBillZip(String billZip) {
        this.billZip = billZip;
    }

    public String getBillCountry() {
        return this.billCountry;
    }

    public void setBillCountry(String billCountry) {
        this.billCountry = billCountry;
    }

    public String getCourier() {
        return this.courier;
    }

    public void setCourier(String courier) {
        this.courier = courier;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBillToFirstName() {
        return this.billToFirstName;
    }

    public void setBillToFirstName(String billToFirstName) {
        this.billToFirstName = billToFirstName;
    }

    public String getBillToLastName() {
        return this.billToLastName;
    }

    public void setBillToLastName(String billToLastName) {
        this.billToLastName = billToLastName;
    }

    public String getShipToFirstName() {
        return this.shipToFirstName;
    }

    public void setShipToFirstName(String shipFoFirstName) {
        this.shipToFirstName = shipFoFirstName;
    }

    public String getShipToLastName() {
        return this.shipToLastName;
    }

    public void setShipToLastName(String shipToLastName) {
        this.shipToLastName = shipToLastName;
    }

    public String getCreditCard() {
        return this.creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public String getExpiryDate() {
        return this.expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCardType() {
        return this.cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getLocale() {
        return this.locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public List<LineItem> getLineItems() {
        return this.lineItems;
    }

    public void initOrder(Account account, Cart cart) {
        this.username = account.getUsername();
        this.orderDate = new Date();
        this.shipToFirstName = account.getFirstName();
        this.shipToLastName = account.getLastName();
        this.shipAddress1 = account.getAddress1();
        this.shipAddress2 = account.getAddress2();
        this.shipCity = account.getCity();
        this.shipState = account.getState();
        this.shipZip = account.getZip();
        this.shipCountry = account.getCountry();
        this.billToFirstName = account.getFirstName();
        this.billToLastName = account.getLastName();
        this.billAddress1 = account.getAddress1();
        this.billAddress2 = account.getAddress2();
        this.billCity = account.getCity();
        this.billState = account.getState();
        this.billZip = account.getZip();
        this.billCountry = account.getCountry();
        this.totalPrice = cart.getSubTotal();
        this.creditCard = "999 9999 9999 9999";
        this.expiryDate = "12/03";
        this.cardType = "Visa";
        this.courier = "UPS";
        this.locale = "CA";
        this.status = "P";
        Iterator<CartItem> i = cart.getAllCartItems();

        while(i.hasNext()) {
            CartItem cartItem = (CartItem)i.next();
            this.addLineItem(cartItem);
        }

    }

    public void addLineItem(CartItem cartItem) {
        LineItem lineItem = new LineItem(this.lineItems.size() + 1, cartItem);
        this.addLineItem(lineItem);
    }

    public void addLineItem(LineItem lineItem) {
        this.lineItems.add(lineItem);
    }
}
