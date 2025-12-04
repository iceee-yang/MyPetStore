package com.csu.petstore.persistence.impl;

import com.csu.petstore.domain.Cart;
import com.csu.petstore.domain.CartItem;
import com.csu.petstore.persistence.CartDao;
import com.csu.petstore.persistence.CartItemDao;
import com.csu.petstore.persistence.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CartDaoImpl implements CartDao {

    private static final String GET_CART_ID = "SELECT cart_id FROM cart WHERE username = ?";
    private static final String CREATE_CART = "INSERT INTO cart (username) VALUES (?)";
    private static final String CLEAR_CART = "DELETE FROM cart_item WHERE cart_id = (SELECT cart_id FROM cart WHERE username = ?)";
    private static final String DELETE_CART = "DELETE FROM cart WHERE username = ?";

    @Override
    public int getOrCreateCartId(String username) {
        int cartId = -1;
        try {
            Connection connection = DBUtil.getConnection();

            // 先尝试获取现有的购物车ID
            PreparedStatement pStatement = connection.prepareStatement(GET_CART_ID);
            pStatement.setString(1, username);
            ResultSet resultSet = pStatement.executeQuery();

            if (resultSet.next()) {
                cartId = resultSet.getInt("cart_id");
            } else {
                // 如果不存在，创建新的购物车
                DBUtil.closeResultSet(resultSet);
                DBUtil.closePreparedStatement(pStatement);

                pStatement = connection.prepareStatement(CREATE_CART, PreparedStatement.RETURN_GENERATED_KEYS);
                pStatement.setString(1, username);
                pStatement.executeUpdate();

                resultSet = pStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    cartId = resultSet.getInt(1);
                }
            }

            DBUtil.closeResultSet(resultSet);
            DBUtil.closePreparedStatement(pStatement);
            DBUtil.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cartId;
    }

    @Override
    public Cart getCartByUsername(String username) {
        Cart cart = new Cart();
        try {
            int cartId = getOrCreateCartId(username);
            if (cartId != -1) {
                CartItemDao cartItemDao = new CartItemDaoImpl();
                // 获取购物车中的所有商品项
                for (CartItem item : cartItemDao.getCartItemsByCartId(cartId)) {
                    cart.addItem(item.getItem(), item.isInStock());
                    // 设置正确的数量（因为addItem默认数量为1）
                    if (item.getQuantity() > 1) {
                        cart.setQuantityByItemId(item.getItem().getItemId(), item.getQuantity());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cart;
    }

    @Override
    public void clearCart(String username) {
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement pStatement = connection.prepareStatement(CLEAR_CART);
            pStatement.setString(1, username);
            pStatement.executeUpdate();
            DBUtil.closePreparedStatement(pStatement);
            DBUtil.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCart(String username) {
        try {
            Connection connection = DBUtil.getConnection();

            // 先清空购物车项（级联删除也可以，但这里显式处理更清晰）
            clearCart(username);

            // 再删除购物车
            PreparedStatement pStatement = connection.prepareStatement(DELETE_CART);
            pStatement.setString(1, username);
            pStatement.executeUpdate();
            DBUtil.closePreparedStatement(pStatement);
            DBUtil.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}