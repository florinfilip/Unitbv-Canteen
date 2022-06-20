package com.florin.restaurant.userService;


import com.florin.restaurant.repository.UserRepository;
import com.florin.restaurant.user.User;
import org.springframework.beans.factory.annotation.Autowired;

public class UserFactory {

    public static User createUser(){
       return User.builder()
                .firstName("Florin")
                .password("juhgn")
                .enabled(true)
                .build();

}}
