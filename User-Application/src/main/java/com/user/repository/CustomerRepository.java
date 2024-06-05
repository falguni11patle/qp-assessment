package com.user.repository;

import com.user.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    @Query("SELECT c FROM Customer c WHERE c.email = ?1 ")
    Customer findByEmail(String email);
}
