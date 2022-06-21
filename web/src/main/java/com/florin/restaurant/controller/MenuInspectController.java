package com.florin.restaurant.controller;

import com.florin.restaurant.menu.Menu;
import com.florin.restaurant.service.MenuService;
import com.florin.restaurant.util.Mappings;
import com.florin.restaurant.util.ViewNames;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping(Mappings.MENUS_ID)
public class MenuInspectController {
    private final MenuService menuService;

    @ModelAttribute
    protected Menu modelMenu(@PathVariable int id){
        return Optional.ofNullable(menuService.getMenuById(id)).get();
    }

    @GetMapping
    public String show(){
        return ViewNames.VIEW;
    }



}
