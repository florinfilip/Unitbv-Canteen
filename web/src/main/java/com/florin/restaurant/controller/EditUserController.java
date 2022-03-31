package com.florin.restaurant.controller;

import com.florin.restaurant.role.Role;
import com.florin.restaurant.service.IUserDetailsService;
import com.florin.restaurant.service.RoleService;
import com.florin.restaurant.user.MyUserDetails;
import com.florin.restaurant.user.User;
import com.florin.restaurant.util.AttributeNames;
import com.florin.restaurant.util.Mappings;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.florin.restaurant.util.AttributeNames.USER;
import static com.florin.restaurant.util.Mappings.USERS_DELETE_ID;
import static com.florin.restaurant.util.Mappings.USERS_SAVE;
import static com.florin.restaurant.util.ViewNames.*;

@Controller
@RequiredArgsConstructor
public class EditUserController {

    private final IUserDetailsService userService;
    private final RoleService roleService;

        @PostMapping(USERS_SAVE)
    public String saveEdit( @ModelAttribute(USER) User user,
                            BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return EDIT_USER;
        }

            System.out.println(bindingResult.getModel().entrySet().stream().findFirst().get().getValue().toString());
        userService.updateUser(user);
        return REDIRECT+ USERS;
    }


    @GetMapping("/users/{id}")
    public String editUser(@PathVariable int id, Model model){

        List<Role> roleList = roleService.findAll();
        MyUserDetails userDetails=userService.findUserById(id);
        User user = userDetails.getUser();
        model.addAttribute(USER, user);
        model.addAttribute(AttributeNames.ROLE_LIST, roleList);
        return EDIT_USER;
    }

    @DeleteMapping(USERS_DELETE_ID)
    public String deleteUser(@PathVariable int id){
            userService.deleteUser(id);
            return REDIRECT+USERS;
}
}


