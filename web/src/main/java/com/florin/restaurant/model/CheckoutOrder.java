package com.florin.restaurant.model;

import com.florin.restaurant.order_item.OrderItem;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CheckoutOrder {

    private int id;
    private String address;
    private String paymentMethod;
    private List<OrderItem> orderItemList;
}
