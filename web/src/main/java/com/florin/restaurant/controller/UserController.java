package com.florin.restaurant.controller;


import com.florin.restaurant.service.MyUserDetailsService;
import com.florin.restaurant.user.User;
import com.florin.restaurant.util.ViewNames;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.florin.restaurant.util.ViewNames.USERS;

@Controller
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

private final MyUserDetailsService userService;

    @GetMapping()
    public ModelAndView list(Model model){
        List<User> users= userService.getUsers();
        model.addAttribute(USERS, users);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName(USERS);
        return modelAndView;
    }
}
