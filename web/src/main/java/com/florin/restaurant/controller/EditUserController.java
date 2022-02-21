package com.florin.restaurant.controller;



import com.florin.restaurant.role.Role;
import com.florin.restaurant.service.IUserDetailsService;
import com.florin.restaurant.service.RoleService;
import com.florin.restaurant.user.MyUserDetails;
import com.florin.restaurant.user.User;
import com.florin.restaurant.util.AttributeNames;
import com.florin.restaurant.util.Mappings;
import com.florin.restaurant.util.ViewNames;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Path;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class EditUserController {

    private final IUserDetailsService userService;
    private final RoleService roleService;

        @PostMapping(Mappings.USERS_SAVE)
    public String saveEdit( @ModelAttribute(AttributeNames.USER) User user, BindingResult bindingResult){
        if(bindingResult.hasErrors() ){
            return ViewNames.EDIT_USER;
        }

//   userService.saveUser(user);
        return ViewNames.REDIRECT+ViewNames.USERS;
    }

    @GetMapping("/users/{id}")
    public String editUser(@PathVariable int id, Model model){
        List<Role> roleList = roleService.findAll();
        model.addAttribute(AttributeNames.USER, userService.findUserById(id));
        model.addAttribute(AttributeNames.ROLELIST, roleList);
        return ViewNames.EDIT_USER;
    }

@DeleteMapping(Mappings.USERS_DELETE_ID)
    public String deleteUser(@PathVariable int id){
            userService.deleteUser(id);
            return ViewNames.REDIRECT+ViewNames.USERS;
}
}


