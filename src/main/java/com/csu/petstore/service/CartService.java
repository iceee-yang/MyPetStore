package com.csu.petstore.service;

import com.csu.petstore.domain.Cart;
import com.csu.petstore.domain.Item;
import com.csu.petstore.persistence.CartDao;
import com.csu.petstore.persistence.CartItemDao;
import com.csu.petstore.persistence.impl.CartDaoImpl;
import com.csu.petstore.persistence.impl.CartItemDaoImpl;

public class CartService {

    private CartDao cartDao;
    private CartItemDao cartItemDao;

    public CartService() {
        this.cartDao = new CartDaoImpl();
        this.cartItemDao = new CartItemDaoImpl();
    }

    /**
     * 根据用户名从数据库加载购物车
     */
    public Cart getCartByUsername(String username) {
        return cartDao.getCartByUsername(username);
    }

    /**
     * 添加商品到购物车（数据库持久化）
     */
    public void addItemToCart(String username, Item item, boolean isInStock) {
        int cartId = cartDao.getOrCreateCartId(username);

        // 检查购物车中是否已存在该商品
        if (cartItemDao.containsItem(cartId, item.getItemId())) {
            // 如果存在，增加数量
            int currentQuantity = cartItemDao.getCartItemQuantity(cartId, item.getItemId());
            cartItemDao.updateCartItemQuantity(cartId, item.getItemId(), currentQuantity + 1);
        } else {
            // 如果不存在，添加新商品
            cartItemDao.addCartItem(cartId, item.getItemId(), 1, isInStock);
        }
    }

    /**
     * 更新购物车商品数量
     */
    public void updateCartItemQuantity(String username, String itemId, int quantity) {
        int cartId = cartDao.getOrCreateCartId(username);

        if (quantity <= 0) {
            // 如果数量小于等于0，删除该商品
            cartItemDao.removeCartItem(cartId, itemId);
        } else {
            // 否则更新数量
            cartItemDao.updateCartItemQuantity(cartId, itemId, quantity);
        }
    }

    /**
     * 从购物车中移除商品
     */
    public void removeItemFromCart(String username, String itemId) {
        int cartId = cartDao.getOrCreateCartId(username);
        cartItemDao.removeCartItem(cartId, itemId);
    }

    /**
     * 清空购物车
     */
    public void clearCart(String username) {
        cartDao.clearCart(username);
    }

    /**
     * 删除购物车（包括购物车记录和所有商品项）
     */
    public void deleteCart(String username) {
        cartDao.deleteCart(username);
    }
}