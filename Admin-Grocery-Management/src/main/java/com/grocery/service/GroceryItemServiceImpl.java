package com.grocery.service;

import com.grocery.exception.ResourceNotFoundException;
import com.grocery.model.Product;
import com.grocery.repository.GroceryItemRepository;
import com.grocery.request.InventoryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class GroceryItemServiceImpl implements GroceryItemService {

    @Autowired
    private GroceryItemRepository groceryItemRepository;

    @Autowired
    private InventoryRequest inventoryRequest;

    //Get all product List
    public List<Product> getAllGroceryItems() {
        return groceryItemRepository.findAll();
    }

    // Get product object using id
    public Product getGroceryItemById(int id) {
        return groceryItemRepository.findById(id).orElse(null);
    }

    // Get product object using product name
    @Override
    public Product getGroceryItemByName(String name) {
        return groceryItemRepository.findByItemName(name);
    }

    // Save product List in DB( single or multiple)
    @Override
    public Map<String,String> saveGroceryItem(List<Product> groceryItem) {
        log.info("Inside saveGroceryItem method of GroceryItemServiceImpl class");
        Map<String,String> outputResponse=new HashMap<>();
        for (Product item:groceryItem) {
            groceryItemRepository.save(item);
            outputResponse.put(item.getName(),"saved in DB.");
        }
        log.info("Exiting saveGroceryItem method of GroceryItemServiceImpl class");
        return outputResponse;
    }

    // Delete product from DB
    public void deleteGroceryItem(int id) {
        groceryItemRepository.deleteById(id);
    }

    //Update details of proudct such as name ,details or else.
    public Map<String,String> updateGroceryItem(int id, Product groceryItemDetails) {
        log.info("Inside updateGroceryItem method of GroceryItemServiceImpl class");
        Product groceryItem = groceryItemRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Grocery not found on server!"));
        Map<String,String> outputResponse=new HashMap<>();
        if (groceryItem != null) {
            groceryItem.setName(groceryItemDetails.getName());
            groceryItem.setPrice(groceryItemDetails.getPrice());
            groceryItem.setDescription(groceryItemDetails.getDescription());
            groceryItem.setQuantity(groceryItemDetails.getQuantity());
            groceryItemRepository.save(groceryItem);
            outputResponse.put(groceryItemDetails.getName(),"updated in DB");
            return outputResponse;
        }
        log.info("Exiting updateGroceryItem method of GroceryItemServiceImpl class");
        return null;
    }

    // Add or subtract quantity from product
    public String manageInventory(InventoryRequest inventoryRequest){
        log.info("Inside manageInventory method of GroceryItemServiceImpl class");
        Product groceryItem = groceryItemRepository.findByItemName(inventoryRequest.getName());
        String output=null;
        if(groceryItem==null){
            throw new ResourceNotFoundException("Grocery with name "+inventoryRequest.getName()+" not found on server!");
        }
        int currentValue = groceryItem.getQuantity();
        if(inventoryRequest.getOperation().equals("add")){
            currentValue=currentValue+inventoryRequest.getValue();
            output=groceryItem.getName()+" quantity updated.(added)";
        }else if(currentValue>=inventoryRequest.getValue() && inventoryRequest.getOperation().equals("sub")){
            currentValue=currentValue-inventoryRequest.getValue();
            output=groceryItem.getName()+" quantity updated.(removed)";
        }else {
            output="Item is out of stock";
        }
        groceryItem.setQuantity(currentValue);
        groceryItemRepository.save(groceryItem);
        log.info("Exiting manageInventory method of GroceryItemServiceImpl class");
        return output;
    }


}
