package com.florin.restaurant.controller;

import com.florin.restaurant.exceptions.UserExistsException;
import com.florin.restaurant.service.IUserDetailsService;
import com.florin.restaurant.user.User;
import com.florin.restaurant.util.AttributeNames;
import com.florin.restaurant.util.Mappings;
import com.florin.restaurant.util.ViewNames;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.swing.text.View;
import javax.validation.Valid;
import java.net.HttpRetryException;

import static com.florin.restaurant.util.ViewNames.REDIRECT;
import static com.florin.restaurant.util.ViewNames.REGISTER;

@Controller
@RequiredArgsConstructor
public class RegisterController {
    private final IUserDetailsService userService;


    @GetMapping(Mappings.REGISTER)
    public String register(Model model){
        User user = new User();
        model.addAttribute(AttributeNames.USER, user);
        return REGISTER;
    }


    @PostMapping(Mappings.REGISTER)
    public String saveUser(@Valid @ModelAttribute(AttributeNames.USER)  User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return REGISTER;
        }
        if(userService.userExists(user.getUsername())){
            throw new UserExistsException("Username is taken by another user! Please pick another one.");
        }
        userService.saveUser(user);
        return REDIRECT+ViewNames.HOME;
    }





}
