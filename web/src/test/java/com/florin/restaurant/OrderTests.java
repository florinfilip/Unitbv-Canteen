package com.florin.restaurant;

import com.florin.restaurant.model.CheckoutOrder;
import com.florin.restaurant.model.Menu;
import com.florin.restaurant.order_item.OrderItem;
import com.florin.restaurant.repository.OrderRepository;
import com.florin.restaurant.repository.UserRepository;
import com.florin.restaurant.service.OrderService;
import com.florin.restaurant.user.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class OrderTests {


        @Autowired
        private OrderRepository orderRepository;

        @Autowired
        private UserRepository userRepository;
        @Autowired
        private TestEntityManager entityManager;

        @Test
        public void shouldAddOrderItem() {
            Menu menu = entityManager.find(Menu.class, 11);
            User user = entityManager.find(User.class, 4);

            OrderItem newOrderItem = new OrderItem();
            newOrderItem.setUser(user);
            newOrderItem.setMenu(menu);
            newOrderItem.setQuantity(3);

            OrderItem saveOrderItem = orderRepository.save(newOrderItem);

            Assertions.assertTrue(saveOrderItem.getId() > 0);
        }

@Test void shouldremoveItem(){

    Menu menu = entityManager.find(Menu.class, 11);
    User user = entityManager.find(User.class, 4);

    OrderItem orderItem = orderRepository.findByUserAndMenu(user,menu);

    if(orderItem.getQuantity()>1){
        orderItem.setQuantity(orderItem.getQuantity()-1);

    }

    else
    {
        orderRepository.delete(orderItem);
    }

}


@Test
public void shouldcreateCheckout(){
    User user = entityManager.find(User.class, 1);
    List<OrderItem> orderItemList=orderRepository.findByUser(user);

    CheckoutOrder checkoutOrder= new CheckoutOrder();

    checkoutOrder.setAddress("Matei Basarab nr. 12");
    checkoutOrder.setPaymentMethod("cash");
    checkoutOrder.setOrderItemList(orderItemList);

    System.out.println(checkoutOrder);

}



        @Test
    public void shouldGetOrderItemByUser(){
            User user = new User();
            user.setId(1);

           List<OrderItem> orderItemList= orderRepository.findByUser(user);
           Assertions.assertEquals(1,orderItemList.size());
        }
    }

