package com.florin.restaurant.service;

import com.florin.restaurant.exceptions.ChangePasswordException;
import com.florin.restaurant.user.MyUserDetails;
import com.florin.restaurant.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface MyUserDetailsService extends UserDetailsService {

    List<User> getUsers();

    void saveUser(User user);

    void deleteUser(int id);

    void updateUser(User user);

    MyUserDetails getCurrentlyLoggedUser(Authentication authentication);

    boolean emailExists(String email);

    MyUserDetails findUserById(int id);

    void changePassword(User user, User modelUser, String newPassword) throws ChangePasswordException;
}