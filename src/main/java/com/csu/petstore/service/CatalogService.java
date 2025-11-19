package com.csu.petstore.service;

import com.csu.petstore.domain.Category;
import com.csu.petstore.domain.Item;
import com.csu.petstore.domain.Product;
import com.csu.petstore.persistence.CategoryDao;
import com.csu.petstore.persistence.ItemDao;
import com.csu.petstore.persistence.ProductDao;
import com.csu.petstore.persistence.impl.CategoryDaoImpl;
import com.csu.petstore.persistence.impl.ItemDaoImpl;
import com.csu.petstore.persistence.impl.ProductDaoImpl;

import javax.xml.catalog.Catalog;
import java.util.List;

public class CatalogService {
    private CategoryDao categoryDao;
    private ProductDao productDao;
    private ItemDao itemDao;

    public CatalogService(){
        this.categoryDao = new CategoryDaoImpl();
        this.productDao = new ProductDaoImpl();
        this.itemDao = new ItemDaoImpl();
    }

    public List<Category> getCategoryList() {
        return this.categoryDao.getCategoryList();
    }

    public Category getCategory(String categoryId) {

        return this.categoryDao.getCategory(categoryId);
    }

    public Product getProduct(String productId) {
        return this.productDao.getProduct(productId);
    }

    public List<Product> getProductListByCategory(String categoryId) {
        return this.productDao.getProductListByCategory(categoryId);
    }

    public List<Product> searchProductList(String keyword) {
        return this.productDao.searchProductList("%" + keyword.toLowerCase() + "%");
    }

    public List<Item> getItemListByProduct(String productId) {
        return this.itemDao.getItemListByProduct(productId);
    }

    public Item getItem(String itemId) {
        return this.itemDao.getItem(itemId);
    }

    public boolean isItemInStock(String itemId) {
        return this.itemDao.getInventoryQuantity(itemId) > 0;
    }
}
