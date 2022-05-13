package com.florin.restaurant.service;

import com.florin.restaurant.order_item.OrderItem;
import com.florin.restaurant.user.User;

import java.util.List;

public interface OrderService {

    List<OrderItem> findOrderByUser(User user);
    int addMenuToOrder(int id, int qty, User user);
    List<OrderItem> getMenus();

    void deleteOrder(int orderId);
    OrderItem getOrderItemById(int id);
    void removeOrderItem(int id, User user);
}
