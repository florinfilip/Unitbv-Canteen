package com.florin.restaurant.userService;

import com.florin.restaurant.repository.UserRepository;
import com.florin.restaurant.service.Impl.UserDetailsServiceImpl;
import com.florin.restaurant.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback
public class UserServiceImplTest {

    @Autowired
    EntityManager entityManager;
    @Autowired
    UserRepository userRepository;

    @Test
    final void testGetUser(){
        User user = UserFactory.createUser();

    }

    @Test
    final void testGetRewards(){
       userRepository.findById(1)
               .get()
               .getRewards()
               .forEach(r-> System.out.println(r.getRewardCode()));
    }

}
