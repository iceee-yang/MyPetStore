package com.csu.petstore.persistence;

import com.csu.petstore.domain.Cart;

public interface CartDao {
    // 根据用户名获取购物车ID，如果不存在则创建
    int getOrCreateCartId(String username);

    // 根据用户名获取完整的购物车对象
    Cart getCartByUsername(String username);

    // 清空用户的购物车
    void clearCart(String username);

    // 删除购物车
    void deleteCart(String username);
}