package com.florin.restaurant.config;

import com.florin.restaurant.service.Impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.httpBasic();


        http.csrf().disable()
                .anonymous().principal("Guest").authorities("GUEST").and()
                .authorizeRequests()
                .mvcMatchers("/play").hasAuthority("USER")
                .mvcMatchers("/order").hasAuthority("USER")
                .mvcMatchers("/checkout").hasAuthority("USER")
                .mvcMatchers("/menu").hasAuthority("ADMIN")
                .mvcMatchers("/add").hasAuthority("ADMIN")
                .mvcMatchers("/delete").hasAuthority("ADMIN")
                .mvcMatchers("/admin").hasAuthority("ADMIN")
                .mvcMatchers("/users").hasAuthority("ADMIN")
                .mvcMatchers("/register").permitAll()
                .anyRequest().permitAll()
                .and()
                  .formLogin()
                .loginPage("/login")
                       .defaultSuccessUrl("/home",true)
                       .permitAll()
                .and()
                        .logout().permitAll()
        .and().rememberMe();
    }

    @Bean
  public DaoAuthenticationProvider authenticationProvider(){

    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    authenticationProvider.setUserDetailsService(userDetailsService());

    return authenticationProvider;

    }


}
