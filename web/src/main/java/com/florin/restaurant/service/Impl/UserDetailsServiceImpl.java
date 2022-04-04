package com.florin.restaurant.service.Impl;

import com.florin.restaurant.repository.UserRepository;
import com.florin.restaurant.role.Role;
import com.florin.restaurant.service.IUserDetailsService;
import com.florin.restaurant.user.MyUserDetails;
import com.florin.restaurant.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements IUserDetailsService {


@Autowired
private UserRepository userRepository;
@Autowired
private BCryptPasswordEncoder passwordEncoder;
@Autowired
private EntityManager entityManager;

    @Override
    public UserDetails loadUserByUsername( String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        User user = userOptional.orElseThrow(
                ()->new UsernameNotFoundException("Username not found in the database"));
        return new MyUserDetails(user);
    }
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public MyUserDetails findUserById(int id) {
        Optional<User> userOptional= userRepository.findById(id);
        User user = userOptional.orElseThrow(()->new UsernameNotFoundException("User with given id not found"));
        return new MyUserDetails(user);
    }
    @Override
    public void saveUser(User user){
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setEnabled(true);
        newUser.setRoles(List.of(entityManager.find(Role.class,1)
        ));
        userRepository.save(newUser);
    }
    @Override
    public void updateUser(User user){
        User newUser=userRepository.findById(user.getId()).get();
        newUser.setId(user.getId());
        newUser.setUsername(user.getUsername());
        if(!passwordEncoder.matches(user.getPassword(), newUser.getPassword())
        && !Objects.equals(user.getPassword(),newUser.getPassword()))
        { newUser.setPassword(passwordEncoder.encode(user.getPassword())); }
        System.out.println(user.getPassword());
        System.out.println(newUser.getPassword());
        newUser.setEnabled(user.isEnabled());
        newUser.setRoles(user.getRoles());

        userRepository.save(newUser);
    }

    @Override
    public void deleteUser(int id){
        userRepository.findById(id).get().setRoles(null);
        userRepository.deleteById(id);
    }
    @Override
    public MyUserDetails getCurrentlyLoggedUser(Authentication authentication){
        authentication= SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        Optional<User> userOptional = userRepository.findUserByUsername(name);
        User loggedUser = userOptional.orElseThrow(
                ()->new UsernameNotFoundException("No user logged found"));
        return new MyUserDetails(loggedUser);
}

    @Override
    public boolean userExists(String username){
        return userRepository.findAll().stream()
                .anyMatch(user->user.getUsername()
                        .equals(username));
}
}
