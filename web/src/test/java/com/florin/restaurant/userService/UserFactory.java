package com.florin.restaurant.userService;


import com.florin.restaurant.repository.UserRepository;
import com.florin.restaurant.user.User;
import org.springframework.beans.factory.annotation.Autowired;

public class UserFactory {

    @Autowired
    private static UserRepository userRepository;

    public static User createUser(){
       return User.builder()
                .username("Florin")
                .password("juhgn")
                .enabled(true)
                .build();

}}
