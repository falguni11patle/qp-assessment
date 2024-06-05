package com.user.controller;

import com.user.model.Customer;
import com.user.model.ShoppingCart;
import com.user.payload.GroceryList;
import com.user.request.OrderRequest;
import com.user.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/user/grocery")
public class UserController {

    @Autowired
    private OrderService order;

    //Get grocery List from Admin api
    @GetMapping
    public ResponseEntity<List<GroceryList>> getAllGroceryItems() {
        List<GroceryList> groceryList = order.getAllGroceryList();
        return ResponseEntity.ok(groceryList);
    }

    //Create Shopping List and order
    @PostMapping
    public ResponseEntity<List<ShoppingCart>> createShoppingList(@RequestBody OrderRequest orderRequest) {
        List<ShoppingCart> shoppingCartList= order.cartValue(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(shoppingCartList);
    }

    //Save Users information in DB
    @PostMapping("/user")
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {
        String customerDetails = order.saveCustomerDetails(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer.getName());
    }

}
