package com.user.request;

import com.user.model.CartList;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private List<CartList> cartLists;
    private String email;

}
