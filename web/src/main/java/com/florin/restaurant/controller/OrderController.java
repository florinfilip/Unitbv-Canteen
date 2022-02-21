package com.florin.restaurant.controller;

import com.florin.restaurant.model.Reward;
import com.florin.restaurant.order_item.OrderItem;
import com.florin.restaurant.service.IUserDetailsService;
import com.florin.restaurant.service.MenuService;
import com.florin.restaurant.service.OrderService;
import com.florin.restaurant.user.MyUserDetails;
import com.florin.restaurant.user.User;
import com.florin.restaurant.util.AttributeNames;
import com.florin.restaurant.util.Mappings;
import com.florin.restaurant.util.ViewNames;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.florin.restaurant.util.AttributeNames.ORDER_ITEM_LIST;
import static com.florin.restaurant.util.AttributeNames.REWARD_LIST;
import static com.florin.restaurant.util.Mappings.ORDER;

@Controller
@Slf4j
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;
    private final IUserDetailsService userDetailsService;

    @GetMapping(ORDER)
    public String showList(Model model,
                           @AuthenticationPrincipal Authentication authentication) {

        MyUserDetails loggedUserDetails = userDetailsService.getCurrentlyLoggedUser(authentication);
        List<OrderItem> orderItemList = orderService.findOrderByUser(loggedUserDetails.getUser());
        List<Reward> rewardList = loggedUserDetails.getUser().getRewards();
        model.addAttribute(ORDER_ITEM_LIST, orderItemList);
        model.addAttribute(REWARD_LIST, rewardList);
        return ViewNames.ORDER;
    }

    @PostMapping(path = ORDER + Mappings.ADD + "/{id}/{qty}")
    public String addOrder(@PathVariable("id") int id,
                           @PathVariable("qty") int quantity,
                           @AuthenticationPrincipal Authentication authentication) {
        User user = userDetailsService.getCurrentlyLoggedUser(authentication).getUser();
        int addedQuantity = orderService.addMenuToOrder(id, quantity, user);
        return ViewNames.REDIRECT + ViewNames.ORDER;
    }


    @DeleteMapping(path = ORDER + "/delete/{menuId}")
    public String deleteOrder(@AuthenticationPrincipal Authentication authentication,
                              @PathVariable("menuId") int id) {

        User user = userDetailsService.getCurrentlyLoggedUser(authentication).getUser();
        orderService.removeOrderItem(id, user);

        return ViewNames.REDIRECT + ViewNames.ORDER;
    }
}

