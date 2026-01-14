package com.csu.petstore.persistence.impl;

import com.csu.petstore.domain.CartItem;
import com.csu.petstore.domain.Item;
import com.csu.petstore.domain.Product;
import com.csu.petstore.persistence.CartItemDao;
import com.csu.petstore.persistence.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CartItemDaoImpl implements CartItemDao {

    private static final String ADD_CART_ITEM = "INSERT INTO cart_item (cart_id, item_id, quantity, in_stock) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE quantity = quantity + ?";
    private static final String UPDATE_CART_ITEM_QUANTITY = "UPDATE cart_item SET quantity = ? WHERE cart_id = ? AND item_id = ?";
    private static final String REMOVE_CART_ITEM = "DELETE FROM cart_item WHERE cart_id = ? AND item_id = ?";
    private static final String GET_CART_ITEMS = "SELECT ci.cart_item_id, ci.item_id, ci.quantity, ci.in_stock, " +
            "i.LISTPRICE, i.UNITCOST, i.SUPPLIER AS supplierId, i.PRODUCTID AS productId, " +
            "p.NAME AS productName, p.DESCN AS productDescription, p.CATEGORY AS categoryId, " +
            "i.STATUS, i.ATTR1 AS attribute1, i.ATTR2 AS attribute2, i.ATTR3 AS attribute3, " +
            "i.ATTR4 AS attribute4, i.ATTR5 AS attribute5 " +
            "FROM cart_item ci " +
            "JOIN item i ON ci.item_id = i.ITEMID " +
            "JOIN product p ON i.PRODUCTID = p.PRODUCTID " +
            "WHERE ci.cart_id = ?";
    private static final String CONTAINS_ITEM = "SELECT COUNT(*) FROM cart_item WHERE cart_id = ? AND item_id = ?";
    private static final String GET_ITEM_QUANTITY = "SELECT quantity FROM cart_item WHERE cart_id = ? AND item_id = ?";
    private static final String CLEAR_CART_ITEMS = "DELETE FROM cart_item WHERE cart_id = ?";

    @Override
    public void addCartItem(int cartId, String itemId, int quantity, boolean inStock) {
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement pStatement = connection.prepareStatement(ADD_CART_ITEM);
            pStatement.setInt(1, cartId);
            pStatement.setString(2, itemId);
            pStatement.setInt(3, quantity);
            pStatement.setInt(4, inStock ? 1 : 0);
            pStatement.setInt(5, quantity);
            pStatement.executeUpdate();
            DBUtil.closePreparedStatement(pStatement);
            DBUtil.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCartItemQuantity(int cartId, String itemId, int quantity) {
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement pStatement = connection.prepareStatement(UPDATE_CART_ITEM_QUANTITY);
            pStatement.setInt(1, quantity);
            pStatement.setInt(2, cartId);
            pStatement.setString(3, itemId);
            pStatement.executeUpdate();
            DBUtil.closePreparedStatement(pStatement);
            DBUtil.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeCartItem(int cartId, String itemId) {
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement pStatement = connection.prepareStatement(REMOVE_CART_ITEM);
            pStatement.setInt(1, cartId);
            pStatement.setString(2, itemId);
            pStatement.executeUpdate();
            DBUtil.closePreparedStatement(pStatement);
            DBUtil.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<CartItem> getCartItemsByCartId(int cartId) {
        List<CartItem> cartItems = new ArrayList<>();
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement pStatement = connection.prepareStatement(GET_CART_ITEMS);
            pStatement.setInt(1, cartId);
            ResultSet resultSet = pStatement.executeQuery();

            while (resultSet.next()) {
                CartItem cartItem = new CartItem();

                // 创建Item对象
                Item item = new Item();
                item.setItemId(resultSet.getString("item_id"));
                item.setListPrice(resultSet.getBigDecimal("LISTPRICE"));
                item.setUnitCost(resultSet.getBigDecimal("UNITCOST"));
                item.setSupplierId(resultSet.getInt("supplierId"));
                item.setStatus(resultSet.getString("STATUS"));
                item.setAttribute1(resultSet.getString("attribute1"));
                item.setAttribute2(resultSet.getString("attribute2"));
                item.setAttribute3(resultSet.getString("attribute3"));
                item.setAttribute4(resultSet.getString("attribute4"));
                item.setAttribute5(resultSet.getString("attribute5"));

                // 创建Product对象
                Product product = new Product();
                product.setProductId(resultSet.getString("productId"));
                product.setName(resultSet.getString("productName"));
                product.setDescription(resultSet.getString("productDescription"));
                product.setCategoryId(resultSet.getString("categoryId"));
                item.setProduct(product);

                // 设置CartItem属性
                cartItem.setItem(item);
                cartItem.setQuantity(resultSet.getInt("quantity"));
                cartItem.setInStock(resultSet.getInt("in_stock") == 1);

                cartItems.add(cartItem);
            }

            DBUtil.closeResultSet(resultSet);
            DBUtil.closePreparedStatement(pStatement);
            DBUtil.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    @Override
    public boolean containsItem(int cartId, String itemId) {
        boolean contains = false;
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement pStatement = connection.prepareStatement(CONTAINS_ITEM);
            pStatement.setInt(1, cartId);
            pStatement.setString(2, itemId);
            ResultSet resultSet = pStatement.executeQuery();

            if (resultSet.next()) {
                contains = resultSet.getInt(1) > 0;
            }

            DBUtil.closeResultSet(resultSet);
            DBUtil.closePreparedStatement(pStatement);
            DBUtil.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contains;
    }

    @Override
    public int getCartItemQuantity(int cartId, String itemId) {
        int quantity = 0;
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement pStatement = connection.prepareStatement(GET_ITEM_QUANTITY);
            pStatement.setInt(1, cartId);
            pStatement.setString(2, itemId);
            ResultSet resultSet = pStatement.executeQuery();

            if (resultSet.next()) {
                quantity = resultSet.getInt("quantity");
            }

            DBUtil.closeResultSet(resultSet);
            DBUtil.closePreparedStatement(pStatement);
            DBUtil.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quantity;
    }

    @Override
    public void clearCartItems(int cartId) {
        try {
            Connection connection = DBUtil.getConnection();
            PreparedStatement pStatement = connection.prepareStatement(CLEAR_CART_ITEMS);
            pStatement.setInt(1, cartId);
            pStatement.executeUpdate();
            DBUtil.closePreparedStatement(pStatement);
            DBUtil.closeConnection(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}