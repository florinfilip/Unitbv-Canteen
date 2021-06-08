package com.florin.restaurant.controller;


import com.florin.restaurant.model.Menu;
import com.florin.restaurant.service.IUserDetailsService;
import com.florin.restaurant.user.User;
import com.florin.restaurant.util.ViewNames;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(ViewNames.USERS)
@RequiredArgsConstructor
public class UserController {

private final IUserDetailsService userService;

    @GetMapping()
    public ModelAndView list(Model model){
        List<User> users= userService.getUsers();
        model.addAttribute(ViewNames.USERS, users);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName(ViewNames.USERS);
        return modelAndView;
    }
}
