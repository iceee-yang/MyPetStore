package com.csu.petstore.persistence;

import com.csu.petstore.domain.Order;

import java.util.List;

public interface OrderDao {
    List<Order> getOrdersByUsername(String username);

    Order getOrder(int orderId);

    void insertOrder(Order order);

    //void insertOrderStatus(Order order);
}
