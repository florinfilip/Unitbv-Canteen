package com.florin.restaurant.controller;

import com.florin.restaurant.exceptions.SignUpException;
import com.florin.restaurant.service.IUserDetailsService;
import com.florin.restaurant.user.User;
import com.florin.restaurant.util.Mappings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

import java.util.Objects;

import static com.florin.restaurant.util.AttributeNames.USER;
import static com.florin.restaurant.util.ViewNames.*;
import static com.florin.restaurant.util.ViewNames.REDIRECT;
import static com.florin.restaurant.util.ViewNames.REGISTER;

@Controller
@RequiredArgsConstructor
public class RegisterController {
    private final IUserDetailsService userService;


    @GetMapping(Mappings.REGISTER)
    public String register(Model model){
        User user = new User();
        model.addAttribute(USER, user);
        return REGISTER;
    }


    @PostMapping(Mappings.REGISTER)
    public String saveUser(@Valid @ModelAttribute(USER)  User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return REGISTER;
        }
        if(!Objects.equals(user.getPassword(),user.getRpassword())){
            throw new SignUpException("Passwords don't match!");
        }
        if(userService.userExists(user.getUsername())){
            throw new SignUpException("Username is taken by another user! Please pick another one.");
        }
        userService.saveUser(user);
        return REDIRECT+ HOME;
    }





}
