package com.csu.petstore.persistence;

import com.csu.petstore.domain.CartItem;
import java.util.List;

public interface CartItemDao {
    // 添加购物车项
    void addCartItem(int cartId, String itemId, int quantity, boolean inStock);

    // 更新购物车项数量
    void updateCartItemQuantity(int cartId, String itemId, int quantity);

    // 删除购物车项
    void removeCartItem(int cartId, String itemId);

    // 获取购物车中的所有项
    List<CartItem> getCartItemsByCartId(int cartId);

    // 检查购物车中是否包含某个商品
    boolean containsItem(int cartId, String itemId);

    // 获取购物车项的数量
    int getCartItemQuantity(int cartId, String itemId);

    // 清空购物车的所有项
    void clearCartItems(int cartId);
}