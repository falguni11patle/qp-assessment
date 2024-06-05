package com.grocery.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class InventoryRequest {
    private String name;
    private String operation;
    private int value;

}
