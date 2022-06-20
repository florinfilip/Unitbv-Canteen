package com.florin.restaurant.service.Impl;

import com.florin.restaurant.exceptions.NotFoundException;
import com.florin.restaurant.menu.Menu;
import com.florin.restaurant.order_item.OrderItem;
import com.florin.restaurant.repository.MenuRepository;
import com.florin.restaurant.repository.OrderRepository;
import com.florin.restaurant.service.OrderService;
import com.florin.restaurant.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final EntityManager entityManager;

    public List<OrderItem> findOrderByUser(User user){
        return orderRepository.findByUser(user);
    }

    @Override
public int addMenuToOrder(@NotNull int id, int qty, User user){
        int qtyAdded = qty;
        Menu menu = menuRepository.findById(id).get();
        OrderItem orderItem = orderRepository.findByUserAndMenu(user,menu);
        Optional<OrderItem> orderOpt=Optional.ofNullable(orderItem);

        if(orderItem!=null){
        qtyAdded = orderItem.getQuantity()+qty;
        orderItem.setQuantity(qtyAdded);
    }

    else{
        orderItem = new OrderItem();
        orderItem.setQuantity(qty);
        orderItem.setUser(user);
        orderItem.setMenu(menu);
    }

    orderRepository.save(orderItem);
    return qtyAdded;
}

@Override
public List<OrderItem> getMenus(){
        return orderRepository.findAll();
}

public OrderItem getOrderItemById(int id) {
        return orderRepository.findById(id).orElseThrow(
                ()->new NotFoundException("OrderItem not found"));
    }

public void deleteOrder(int orderId){
        OrderItem orderItem = getOrderItemById(orderId);
        orderRepository.delete(orderItem);
}

public void removeOrderItem(int id, User user){

        log.info("INSIDE removeOrderItem()");
    Menu menu = menuRepository.findById(id).get();

    OrderItem orderItem = orderRepository.findByUserAndMenu(user,menu);
log.info(orderItem.toString());
    if(orderItem.getQuantity()>1){
        orderItem.setQuantity(orderItem.getQuantity()-1);
        log.info("Deleting orderItem quantity");
        log.info(orderItem.toString());
        orderRepository.save(orderItem);

    }

    else
    {
        orderRepository.delete(orderItem);
        log.info("Deleting orderItem");
    }

}
}




