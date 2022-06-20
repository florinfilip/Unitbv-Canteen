package com.florin.restaurant.controller;

import com.florin.restaurant.service.GameService;
import com.florin.restaurant.service.MyUserDetailsService;
import com.florin.restaurant.user.MyUserDetails;
import com.florin.restaurant.user.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.florin.restaurant.util.AttributeNames.GAME_SERVICE;
import static com.florin.restaurant.util.Mappings.HOME;

@Controller
@RequestMapping(HOME)
@Slf4j
@AllArgsConstructor

public class HomeController {

private final GameService gameService;
private final MyUserDetailsService userDetailsService;



    @GetMapping(HOME)
    public String home (Model model, @AuthenticationPrincipal Authentication authentication){
        User loggedUser = userDetailsService.getCurrentlyLoggedUser(authentication).getUser();
        model.addAttribute(GAME_SERVICE, gameService);
        model.addAttribute("user", userDetailsService.loadUserByUsername(loggedUser.getEmail()));

          return HOME;
    }
}
