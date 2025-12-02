package com.csu.petstore.persistence.impl;

import com.csu.petstore.domain.Order;
import com.csu.petstore.persistence.DBUtil;
import com.csu.petstore.persistence.OrderDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

    private static final String GET_ORDER_BY_USERNAME = """
            SELECT\
                  BILLADDR1 AS billAddress1,
                  BILLADDR2 AS billAddress2,
                  BILLCITY,
                  BILLCOUNTRY,
                  BILLSTATE,
                  BILLTOFIRSTNAME,
                  BILLTOLASTNAME,
                  BILLZIP,
                  SHIPADDR1 AS shipAddress1,
                  SHIPADDR2 AS shipAddress2,
                  SHIPCITY,
                  SHIPCOUNTRY,
                  SHIPSTATE,
                  SHIPTOFIRSTNAME,
                  SHIPTOLASTNAME,
                  SHIPZIP,
                  CARDTYPE,
                  COURIER,
                  CREDITCARD,
                  EXPRDATE AS expiryDate,
                  LOCALE,
                  ORDERDATE,
                  ORDERS.ORDERID,
                  TOTALPRICE,
                  USERID AS username,
                  STATUS
                FROM ORDERS, ORDERSTATUS
                WHERE ORDERS.USERID = ?\s
                  AND ORDERS.ORDERID = ORDERSTATUS.ORDERID
                ORDER BY ORDERDATE""";

    private static final String GET_ORDER_BY_ID = """
            SELECT
                  BILLADDR1 AS billAddress1,
                  BILLADDR2 AS billAddress2,
                  BILLCITY,
                  BILLCOUNTRY,
                  BILLSTATE,
                  BILLTOFIRSTNAME,
                  BILLTOLASTNAME,
                  BILLZIP,
                  SHIPADDR1 AS shipAddress1,
                  SHIPADDR2 AS shipAddress2,
                  SHIPCITY,
                  SHIPCOUNTRY,
                  SHIPSTATE,
                  SHIPTOFIRSTNAME,
                  SHIPTOLASTNAME,
                  SHIPZIP,
                  CARDTYPE,
                  COURIER,
                  CREDITCARD,
                  EXPRDATE AS expiryDate,
                  LOCALE,
                  ORDERDATE,
                  ORDERID,
                  TOTALPRICE,
                  USERID AS username
                FROM ORDERS
                WHERE ORDERID = ?""";

    private static final String INSERT_ORDER = """
            INSERT INTO ORDERS (
                ORDERID, USERID, ORDERDATE,
                SHIPADDR1, SHIPADDR2, SHIPCITY, SHIPSTATE, SHIPZIP, SHIPCOUNTRY,
                BILLADDR1, BILLADDR2, BILLCITY, BILLSTATE, BILLZIP, BILLCOUNTRY,
                COURIER, TOTALPRICE,
                BILLTOFIRSTNAME, BILLTOLASTNAME,
                SHIPTOFIRSTNAME, SHIPTOLASTNAME,
                CREDITCARD, EXPRDATE, CARDTYPE, LOCALE
            ) VALUES (
                ?, ?, ?,
                ?, ?, ?, ?, ?, ?,
                ?, ?, ?, ?, ?, ?,
                ?, ?,
                ?, ?,
                ?, ?,
                ?, ?, ?, ?
            )""";

    public List<Order> getOrdersByUsername(String username){
        List<Order> orders = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(GET_ORDER_BY_USERNAME);
            ps.setString(1, username);
            rs = ps.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("ORDERID"));
                order.setUsername(rs.getString("username"));
                order.setOrderDate(rs.getDate("ORDERDATE"));
                order.setBillAddress1(rs.getString("billAddress1"));
                order.setBillAddress2(rs.getString("billAddress2"));
                order.setBillCity(rs.getString("BILLCITY"));
                order.setBillState(rs.getString("BILLSTATE"));
                order.setBillZip(rs.getString("BILLZIP"));
                order.setBillCountry(rs.getString("BILLCOUNTRY"));
                order.setBillToFirstName(rs.getString("BILLTOFIRSTNAME"));
                order.setBillToLastName(rs.getString("BILLTOLASTNAME"));
                order.setShipAddress1(rs.getString("shipAddress1"));
                order.setShipAddress2(rs.getString("shipAddress2"));
                order.setShipCity(rs.getString("SHIPCITY"));
                order.setShipState(rs.getString("SHIPSTATE"));
                order.setShipZip(rs.getString("SHIPZIP"));
                order.setShipCountry(rs.getString("SHIPCOUNTRY"));
                order.setShipToFirstName(rs.getString("SHIPTOFIRSTNAME"));
                order.setShipToLastName(rs.getString("SHIPTOLASTNAME"));
                order.setCourier(rs.getString("COURIER"));
                order.setTotalPrice(rs.getBigDecimal("TOTALPRICE"));
                order.setCreditCard(rs.getString("CREDITCARD"));
                order.setExpiryDate(rs.getString("expiryDate"));
                order.setCardType(rs.getString("CARDTYPE"));
                order.setLocale(rs.getString("LOCALE"));

                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(conn);
        }

        return orders;
    }

    public Order getOrder(int orderId){
        Order order = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(GET_ORDER_BY_ID);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();

            if (rs.next()) {
                order = new Order();
                order.setOrderId(rs.getInt("ORDERID"));
                order.setUsername(rs.getString("username"));
                order.setOrderDate(rs.getDate("ORDERDATE"));
                order.setBillAddress1(rs.getString("billAddress1"));
                order.setBillAddress2(rs.getString("billAddress2"));
                order.setBillCity(rs.getString("BILLCITY"));
                order.setBillState(rs.getString("BILLSTATE"));
                order.setBillZip(rs.getString("BILLZIP"));
                order.setBillCountry(rs.getString("BILLCOUNTRY"));
                order.setBillToFirstName(rs.getString("BILLTOFIRSTNAME"));
                order.setBillToLastName(rs.getString("BILLTOLASTNAME"));
                order.setShipAddress1(rs.getString("shipAddress1"));
                order.setShipAddress2(rs.getString("shipAddress2"));
                order.setShipCity(rs.getString("SHIPCITY"));
                order.setShipState(rs.getString("SHIPSTATE"));
                order.setShipZip(rs.getString("SHIPZIP"));
                order.setShipCountry(rs.getString("SHIPCOUNTRY"));
                order.setShipToFirstName(rs.getString("SHIPTOFIRSTNAME"));
                order.setShipToLastName(rs.getString("SHIPTOLASTNAME"));
                order.setCourier(rs.getString("COURIER"));
                order.setTotalPrice(rs.getBigDecimal("TOTALPRICE"));
                order.setCreditCard(rs.getString("CREDITCARD"));
                order.setExpiryDate(rs.getString("expiryDate"));
                order.setCardType(rs.getString("CARDTYPE"));
                order.setLocale(rs.getString("LOCALE"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(conn);
        }

        return order;
    }

    public void insertOrder(Order order){
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(INSERT_ORDER);
            ps.setInt(1, order.getOrderId());
            ps.setString(2, order.getUsername());
            ps.setDate(3, new java.sql.Date(order.getOrderDate().getTime()));
            ps.setString(4, order.getShipAddress1());
            ps.setString(5, order.getShipAddress2());
            ps.setString(6, order.getShipCity());
            ps.setString(7, order.getShipState());
            ps.setString(8, order.getShipZip());
            ps.setString(9, order.getShipCountry());
            ps.setString(10, order.getBillAddress1());
            ps.setString(11, order.getBillAddress2());
            ps.setString(12, order.getBillCity());
            ps.setString(13, order.getBillState());
            ps.setString(14, order.getBillZip());
            ps.setString(15, order.getBillCountry());
            ps.setString(16, order.getCourier());
            ps.setBigDecimal(17, order.getTotalPrice());
            ps.setString(18, order.getBillToFirstName());
            ps.setString(19, order.getBillToLastName());
            ps.setString(20, order.getShipToFirstName());
            ps.setString(21, order.getShipToLastName());
            ps.setString(22, order.getCreditCard());
            ps.setString(23, order.getExpiryDate());
            ps.setString(24, order.getCardType());
            ps.setString(25, order.getLocale());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.closePreparedStatement(ps);
            DBUtil.closeConnection(conn);
        }
    }

//    public void insertOrderStatus(Order order){
//
//    }
}
