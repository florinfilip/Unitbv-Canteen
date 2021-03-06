package com.florin.restaurant.controller;

import com.florin.restaurant.util.Mappings;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.florin.restaurant.util.ViewNames.LOGIN;
import static com.florin.restaurant.util.ViewNames.REDIRECT;

@Controller
public class LoginController {

    @GetMapping(Mappings.LOGIN)
            public String loginInit(){
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
if(authentication==null || authentication instanceof AnonymousAuthenticationToken){
    assert authentication != null;
    return LOGIN;
    }
return REDIRECT;

}}
