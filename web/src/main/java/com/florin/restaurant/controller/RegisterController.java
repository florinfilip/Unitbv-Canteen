package com.florin.restaurant.controller;

import com.florin.restaurant.exceptions.SignUpException;
import com.florin.restaurant.service.IUserDetailsService;
import com.florin.restaurant.token.ConfirmationTokenService;
import com.florin.restaurant.user.User;
import com.florin.restaurant.util.Mappings;
import com.florin.restaurant.util.ViewNames;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Objects;

import static com.florin.restaurant.util.AttributeNames.USER;
import static com.florin.restaurant.util.Mappings.REGISTER;
import static com.florin.restaurant.util.ViewNames.HOME;
import static com.florin.restaurant.util.ViewNames.REDIRECT;

@Controller
@RequiredArgsConstructor
public class RegisterController {
    private final IUserDetailsService userService;
    private final ConfirmationTokenService confirmationTokenService;

    @GetMapping(REGISTER)
    public String register(Model model){
        User user = new User();
        model.addAttribute(USER, user);
        return ViewNames.REGISTER;
    }

    @GetMapping("confirm")
    public String confirm(@RequestParam("token") String token){
        return confirmationTokenService.confirmToken(token);
    }


    @PostMapping(REGISTER)
    public String saveUser(@Valid @ModelAttribute(USER)  User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ViewNames.REGISTER;
        }
        if(!Objects.equals(user.getPassword(),user.getRpassword())){
            throw new SignUpException("Passwords don't match!");
        }
        if(userService.userExists(user.getUsername())){
            throw new SignUpException("Username is taken by another user! Please pick another one.");
        }
        if(userService.emailExists(user.getEmail())){
            throw new SignUpException("This Email has been already registered!");
        }
        userService.saveUser(user);

        return REDIRECT + HOME;
    }






}
