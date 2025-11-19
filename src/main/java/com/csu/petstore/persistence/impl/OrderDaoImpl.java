package com.csu.petstore.persistence.impl;

import com.csu.petstore.domain.Order;
import com.csu.petstore.persistence.OrderDao;

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

    public List<Order> getOrdersByUsername(String username){
        return null;
    }

    public Order getOrder(int orderId){
        return null;
    }

    public void insertOrder(Order order){

    }

    public void insertOrderStatus(Order order){

    }
}
