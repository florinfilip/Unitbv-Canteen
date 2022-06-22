package com.florin.restaurant.controller;


import com.florin.restaurant.menu.Menu;
import com.florin.restaurant.repository.MenuRepository;
import com.florin.restaurant.role.Role;
import com.florin.restaurant.service.MenuService;
import com.florin.restaurant.service.MyUserDetailsService;
import com.florin.restaurant.user.MyUserDetails;
import com.florin.restaurant.user.User;
import com.florin.restaurant.util.AttributeNames;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static com.florin.restaurant.util.AttributeNames.USER;
import static com.florin.restaurant.util.Mappings.USERS_SAVE;
import static com.florin.restaurant.util.ViewNames.*;

@Controller
@RequestMapping("edit-menus")
@RequiredArgsConstructor
public class MenusSettingsController {

private final MenuService menuService;

    @GetMapping()
    public ModelAndView list(Model model){
        List<Menu> menus= menuService.getMenus();
        model.addAttribute("menus", menus);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("edit-menus");
        return modelAndView;
    }

    @PostMapping("/save")
    public String saveEdit( @ModelAttribute("menu") Menu menu,
                            BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "menu";
        }
        menuService.updateMenu(menu);
        return REDIRECT+ "edit-menus";
    }

    @GetMapping("menu/{id}")
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
