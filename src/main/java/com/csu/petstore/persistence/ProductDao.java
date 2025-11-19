package com.csu.petstore.persistence;

import com.csu.petstore.domain.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getProductListByCategory(String var1);

    Product getProduct(String var1);

    List<Product> searchProductList(String var1);
}
