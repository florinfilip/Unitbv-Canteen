package com.florin.restaurant.userService;

import com.florin.restaurant.repository.UserRepository;
import com.florin.restaurant.service.Impl.UserDetailsServiceImpl;
import com.florin.restaurant.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserServiceImplTest {

    @InjectMocks
    UserDetailsServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);

    }

    @Test
    final void testGetUser(){
        User user = UserFactory.createUser();

    }

}
