package com.florin.restaurant.controller;


import com.florin.restaurant.menu.Menu;
import com.florin.restaurant.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.florin.restaurant.util.ViewNames.REDIRECT;

@Controller
@RequestMapping("menu")
@RequiredArgsConstructor
public class EditMenuController {

private final MenuService menuService;

    @PostMapping("/save")
    public String saveEdit( @ModelAttribute("menu") Menu menu,
                            BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "menu";
        }
        menuService.updateMenu(menu);
        return REDIRECT+ "edit-menus";
    }

    @GetMapping("/{id}")
    public String editUser(@PathVariable int id, Model model){
        Menu menu = menuService.getMenuById(id);
        model.addAttribute("menu", menu);
        return "menu";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteMenu(@PathVariable int id){
        menuService.deleteMenu(id);
        return REDIRECT+"edit-menus";
    }



}
