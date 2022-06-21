package com.florin.restaurant.controller;

import com.florin.restaurant.exceptions.ChangePasswordException;
import com.florin.restaurant.exceptions.NotFoundException;
import com.florin.restaurant.service.MyUserDetailsService;
import com.florin.restaurant.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static com.florin.restaurant.util.ViewNames.REDIRECT;
import static com.florin.restaurant.util.ViewNames.SETTINGS;

@Controller
@RequestMapping("/settings")
@AllArgsConstructor
public class SettingsController {

    private final MyUserDetailsService userService;

@GetMapping
public void settingsHome(@AuthenticationPrincipal Authentication authentication, Model model){
    model.addAttribute("basicUser",Optional.ofNullable(userService.getCurrentlyLoggedUser(authentication)
                    .getUser())
            .orElseThrow(()->new NotFoundException("User Not Found")));
}

@PostMapping("/changePass")
public String changePassword(@AuthenticationPrincipal Authentication authentication,
                              @ModelAttribute("basicUser") User user,
                             BindingResult bindingResult,
                             @RequestParam(name="newPassword") String newPassword) throws ChangePasswordException {
    User loggedUser=userService.getCurrentlyLoggedUser(authentication).getUser();
        if(bindingResult.hasErrors()){
            return SETTINGS;
        }
        userService.changePassword(loggedUser, user, newPassword);
        return SETTINGS;
    }
}
