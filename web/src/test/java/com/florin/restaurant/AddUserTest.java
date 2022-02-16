package com.florin.restaurant;

import com.florin.restaurant.repository.UserRepository;
import com.florin.restaurant.role.Role;
import com.florin.restaurant.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AddUserTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
  private TestEntityManager entityManager;


   private BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    @Test
    @Rollback(value = false)
    void shouldaddUser(){
User newUser =  new User();
newUser.setUsername("Filipp");
newUser.setPassword(passwordEncoder.encode("Fuin"));
newUser.setEnabled(true);
newUser.setRoles(List.of(entityManager.find(Role.class,1)));

User saveUser = userRepository.save(newUser);
assertEquals(saveUser.getId(),4);

    }
    @Test
    void shouldgetRoles(){
        User newUser = entityManager.find(User.class,1);

       List<Role> roles = newUser.getRoles();
        System.out.println(roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList()));

    }

    @Test
    void getUserById(){
        System.out.println(userRepository.findById(1).get());
    }

}
