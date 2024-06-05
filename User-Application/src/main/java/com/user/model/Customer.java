package com.user.model;


import jakarta.persistence.*;
import org.springframework.core.annotation.Order;

import java.util.List;

@Entity
@Table(name = "Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String password;

    @OneToMany(mappedBy = "customer")
    private List<OrderDetails> orderList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<OrderDetails> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderDetails> orderList) {
        this.orderList = orderList;
    }
}
