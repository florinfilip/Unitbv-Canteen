package com.florin.restaurant.controller;


import com.florin.restaurant.model.CheckoutOrder;
import com.florin.restaurant.order_item.OrderItem;
import com.florin.restaurant.service.IUserDetailsService;
import com.florin.restaurant.service.OrderService;
import com.florin.restaurant.user.User;
import com.florin.restaurant.util.AttributeNames;
import com.florin.restaurant.util.Mappings;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CheckoutRestController {

    private final IUserDetailsService userDetailsService;
    private final OrderService orderService;

    @PostMapping(Mappings.CHECKOUT)
    public ResponseEntity readcheckoutOrders(@ModelAttribute(AttributeNames.CHECKOUT_ORDER)
                                   CheckoutOrder checkoutOrder,
                                   @AuthenticationPrincipal Authentication authentication){


        User user=userDetailsService.getCurrentlyLoggedUser(authentication).getUser();
        List<OrderItem> orderItemList = orderService.findOrderByUser(user);
        checkoutOrder.setOrderItemList(orderItemList);
        orderItemList.forEach(order->orderService.deleteOrder(order.getId()));


        return new ResponseEntity(checkoutOrder, HttpStatus.OK);
    }





}
