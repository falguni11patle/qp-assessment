package com.user.service;

import com.user.feignclient.GroceryListClient;
import com.user.model.*;


import com.user.repository.CustomerRepository;
import com.user.repository.OrderRepository;
import com.user.repository.ShoppingCartRepository;
import com.user.payload.GroceryList;
import com.user.request.OrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private GroceryListClient groceryListClient;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    //Get grocery list contaning name , price from Admin module using feign client
    public List<GroceryList> getAllGroceryList(){
        log.info("Inside getAllGroceryList method of OrderService class");
        ResponseEntity<List<Product>> itemListResponse = groceryListClient.getAllGroceryItem();
        List<Product> itemList = itemListResponse.getBody();
        List<GroceryList> groceryLists=new ArrayList<>();
        if (itemList != null) {
            for (Product item : itemList) {
                GroceryList groceryItem = new GroceryList();
                groceryItem.setName(item.getName());
                groceryItem.setPrice(item.getPrice());
                groceryLists.add(groceryItem);
            }
        } else {
            System.out.println("The grocery list is empty.");
        }
        log.info("Exiting getAllGroceryList method of OrderService class");
        return groceryLists;
    }

    //Add Product in Shopping Cart and then calculate Order total and manage inventory at the same
    public List<ShoppingCart> cartValue(OrderRequest orderRequest){
        //orderRequest contains CartList and email
        log.info("Inside cartValue method of OrderService class");
        List<ShoppingCart> shoppingCartList=new ArrayList<>();
        /*
        CartList contains name and quantity. Using name from cartList to find Product from product table using Admin API(getGroceryItemByName),
        with the help of feign client. And setting product details in Shopping cart object. and adding Shopping cart object in shoppingCartList.
         */
        for(CartList item:orderRequest.getCartLists()){
            ResponseEntity<Product> productDetails= groceryListClient.getGroceryItemByName(item.getName());
            Product product=productDetails.getBody();
            ShoppingCart shoppingCart=new ShoppingCart();
            shoppingCart.setProductId(product.getId());
            shoppingCart.setProductName(product.getName());
            shoppingCart.setProductQuantity(item.getQuantity());
            shoppingCart.setAmount(product.getPrice()*item.getQuantity());
            shoppingCartList.add(shoppingCart);
        }
        //using email field of orderRequest find customer details from DB.
        Customer customerDetail= customerRepository.findByEmail(orderRequest.getEmail());
        OrderDetails order=new OrderDetails();
        List<ShoppingCart> orderList=new ArrayList<>();
        Float orderValue = 0f;
        String message=null;
        String error="Item is out of stock";
        // setting customer details in order
        order.setCustomer(customerDetail);
        //managing inventory(sub from product table quantity).
        for (ShoppingCart item:shoppingCartList){
            InventoryRequest inventoryRequest =new InventoryRequest();
            inventoryRequest.setName(item.getProductName());
            inventoryRequest.setValue(item.getProductQuantity());
            inventoryRequest.setOperation("sub");
            //calling Admin API using feign client
            ResponseEntity<String> output= groceryListClient.updateInventory(inventoryRequest);
            message =output.getBody();
            if(!message.equals(error)) {
                orderValue = orderValue + item.getAmount();
                orderList.add(item);
                shoppingCartRepository.save(item);
            }
        }
        if(!message.equals(error)) {
            order.setCartItems(orderList);
            order.setOrderValue(orderValue);
            orderRepository.save(order);
        }
        log.info("Exiting cartValue method of OrderService class");
        return orderList;
    }

    // save customer details in DB
    public String saveCustomerDetails(Customer customer) {
        customerRepository.save(customer);
        return customer.getName();
    }
}
