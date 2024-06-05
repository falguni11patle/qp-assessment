package com.user.feignclient;

import com.user.model.InventoryRequest;
import com.user.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@FeignClient(name = "Admin-Grocery-Management" , url = "http://localhost:8080", path = "/api/admin/grocery-items")
public interface GroceryListClient {

    @GetMapping
    public ResponseEntity<List<Product>> getAllGroceryItem();

    @GetMapping("/name/{name}")
    public ResponseEntity<Product> getGroceryItemByName(@PathVariable String name);

    @PutMapping
    public ResponseEntity<String> updateInventory( @RequestBody InventoryRequest inventoryRequest);

}
