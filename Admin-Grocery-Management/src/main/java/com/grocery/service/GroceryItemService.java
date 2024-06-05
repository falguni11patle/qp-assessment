package com.grocery.service;

import com.grocery.model.Product;
import com.grocery.request.InventoryRequest;

import java.util.List;
import java.util.Map;

public interface GroceryItemService {

    List<Product> getAllGroceryItems();

    Product getGroceryItemById(int id);

    Map<String,String> saveGroceryItem(List<Product> groceryItem);

    void deleteGroceryItem(int id);

    Map<String,String> updateGroceryItem(int id, Product groceryItemDetails);

    String manageInventory(InventoryRequest inventoryRequest);

    Product getGroceryItemByName(String name);
}
