package com.florin.restaurant.controller;


import com.florin.restaurant.model.CheckoutOrder;
import com.florin.restaurant.model.Reward;
import com.florin.restaurant.order_item.OrderItem;
import com.florin.restaurant.service.IUserDetailsService;
import com.florin.restaurant.service.OrderService;
import com.florin.restaurant.user.User;
import com.florin.restaurant.util.AttributeNames;
import com.florin.restaurant.util.Mappings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.florin.restaurant.util.AttributeNames.ORDER_ITEM_LIST;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CheckoutRestController {

    private final IUserDetailsService userDetailsService;
    private final OrderService orderService;

    String INVALID_DISCOUNT_CODE = "Invalid Discount Code!";

    @PostMapping(Mappings.CHECKOUT)
    public ResponseEntity readCheckoutOrders(@ModelAttribute(AttributeNames.CHECKOUT_ORDER) CheckoutOrder checkoutOrder,
                                             @AuthenticationPrincipal Authentication authentication){

        User user=userDetailsService.getCurrentlyLoggedUser(authentication).getUser();
        List<OrderItem> orderItemList = orderService.findOrderByUser(user);

        checkoutOrder.setOrderItemList(orderItemList);
        applyDiscountCode(checkoutOrder, user);
        orderItemList.forEach(order->orderService.deleteOrder(order.getId()));

        return new ResponseEntity(checkoutOrder, HttpStatus.OK);
    }

    private void applyDiscountCode(CheckoutOrder checkoutOrder, User user) {
        //         checkoutOrder.getOrderItemList().forEach(item->item.getMenu()
        //                 .setPrice(item.getMenu().getPrice()-(item.getMenu().getPrice()*0.2)));
        System.out.println(userHasValidCode(checkoutOrder, user));
//      else log.debug(INVALID_DISCOUNT_CODE);
    }

    private boolean userHasValidCode(CheckoutOrder checkoutOrder, User user) {
        return user.getRewards().stream()
                  .anyMatch(reward -> reward.getRewardCode().equals(checkoutOrder.getDiscountCode()));
    }


}
