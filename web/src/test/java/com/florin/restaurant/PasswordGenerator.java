package com.florin.restaurant;


import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
public class PasswordGenerator {



    @Test
    void shouldGeneratePassword(){
        BCryptPasswordEncoder encoder  = new BCryptPasswordEncoder();
        String rawPass="Endava1!";
        String encodedPass= encoder.encode(rawPass);
        System.out.println(encodedPass);
    }

    @Test
    void givenSamePasswordAndEncodedPasswordShouldReturnTrue(){
        BCryptPasswordEncoder encoder  = new BCryptPasswordEncoder();
        String rawPass="endava";
        String encodedPass= encoder.encode(rawPass);
        assertThat(encoder.matches(rawPass,encodedPass),is(true));
    }


}
