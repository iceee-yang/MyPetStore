package com.csu.petstore.persistence;

import com.csu.petstore.domain.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> getCategoryList();
    Category getCategory(String categoryId);
}
