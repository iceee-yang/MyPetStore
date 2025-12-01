package com.csu.petstore.service;

import com.csu.petstore.domain.Item;
import com.csu.petstore.domain.LineItem;
import com.csu.petstore.domain.Order;
import com.csu.petstore.persistence.ItemDao;
import com.csu.petstore.persistence.LineItemDao;
import com.csu.petstore.persistence.OrderDao;
import com.csu.petstore.persistence.SequenceDao;
import com.csu.petstore.persistence.impl.ItemDaoImpl;
import com.csu.petstore.persistence.impl.LineItemDaoImpl;
import com.csu.petstore.persistence.impl.OrderDaoImpl;

import javax.sound.midi.Sequence;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {

    private ItemDao itemDao;
    private OrderDao orderDao;
    private LineItemDao lineItemDao;
    private SequenceDao sequenceDao;

    public OrderService() {
        this.itemDao = new ItemDaoImpl();
        this.orderDao = new OrderDaoImpl();
        this.lineItemDao = new LineItemDaoImpl();
    }

    public void insertOrder(Order order) {
        order.setOrderId(this.getNextId("ordernum"));

        for(int i = 0; i < order.getLineItems().size(); ++i) {
            LineItem lineItem = (LineItem)order.getLineItems().get(i);
            String itemId = lineItem.getItemId();
            Integer increment = Integer.valueOf((lineItem.getQuantity()));
            Map<String, Object> param = new HashMap(2);
            param.put("itemId", itemId);
            param.put("increment", increment);
            this.itemDao.updateInventoryQuantity(param);
        }

        this.orderDao.insertOrder(order);
        //this.orderDao.insertOrderStatus(order);

        for(int i = 0; i < order.getLineItems().size(); ++i) {
            LineItem lineItem = (LineItem)order.getLineItems().get(i);
            lineItem.setOrderId(order.getOrderId());
            this.lineItemDao.insertLineItem(lineItem);
        }

    }

    public Order getOrder(int orderId) {
        Order order = this.orderDao.getOrder(orderId);
        order.setLineItems(this.lineItemDao.getLineItemsByOrderId(orderId));

        for(int i = 0; i < order.getLineItems().size(); ++i) {
            LineItem lineItem = (LineItem)order.getLineItems().get(i);
            Item item = this.itemDao.getItem(lineItem.getItemId());
            item.setQuantity(this.itemDao.getInventoryQuantity(lineItem.getItemId()));
            lineItem.setItem(item);
        }

        return order;
    }

    public List<Order> getOrdersByUsername(String username) {
        return this.orderDao.getOrdersByUsername(username);
    }

    public int getNextId(String name) {
        return this.sequenceDao.getNextId(name);
    }


}
