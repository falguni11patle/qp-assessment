package com.grocery.repository;

import com.grocery.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GroceryItemRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT g FROM Product g WHERE g.name = ?1 ")
    Product findByItemName(String name);

}
