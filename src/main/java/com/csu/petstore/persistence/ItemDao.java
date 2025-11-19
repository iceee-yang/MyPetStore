package com.csu.petstore.persistence;

import com.csu.petstore.domain.Item;

import java.util.List;
import java.util.Map;

public interface ItemDao {
    void updateInventoryQuantity(Map<String, Object> var1);

    int getInventoryQuantity(String var1);

    List<Item> getItemListByProduct(String var1);

    Item getItem(String var1);
}
