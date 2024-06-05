package com.grocery.controller;

import com.grocery.model.Product;
import com.grocery.request.InventoryRequest;
import com.grocery.service.GroceryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/admin/grocery-items")
public class AdminController {

    @Autowired
    private GroceryItemService groceryItemService;

    //Get all product
    @GetMapping
    public ResponseEntity<List<Product>> getAllGroceryItems() {
        List<Product> groceryItemList= groceryItemService.getAllGroceryItems();
        return ResponseEntity.ok(groceryItemList);
    }

    // Get product by Id
    @GetMapping("/{id}")
    public ResponseEntity<Product> getGroceryItemById(@PathVariable int id) {
        Product groceryItem = groceryItemService.getGroceryItemById(id);
        return ResponseEntity.ok(groceryItem);
    }

    // Get product by name
    @GetMapping("/name/{name}")
    public ResponseEntity<Product> getGroceryItemByName(@PathVariable String name) {
        Product groceryItem = groceryItemService.getGroceryItemByName(name);
        return ResponseEntity.ok(groceryItem);
    }

    //Add product in DB
    @PostMapping
    public ResponseEntity<Map<String,String>> createGroceryItem(@RequestBody List<Product> groceryItem) {
        Map<String,String> stringMap = groceryItemService.saveGroceryItem(groceryItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(stringMap);
    }

    // Update product details in DB
    @PutMapping("/{id}")
    public ResponseEntity<Map<String,String>>  updateGroceryItem(@PathVariable int id, @RequestBody Product groceryItem) {
        Map<String,String> reponseMap = groceryItemService.updateGroceryItem(id, groceryItem);
        return ResponseEntity.ok(reponseMap);
    }

    // Delete product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroceryItem(@PathVariable int id) {
        groceryItemService.deleteGroceryItem(id);
        return ResponseEntity.noContent().build();
    }

    // Update quantity of product (add or sub from current quantity)
    @PutMapping
    public ResponseEntity<String> updateInventory( @RequestBody InventoryRequest inventoryRequest) {
        String message = groceryItemService.manageInventory(inventoryRequest);
        return ResponseEntity.ok(message);
    }


}
