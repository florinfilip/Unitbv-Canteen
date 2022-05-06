package com.florin.restaurant.token;

import com.florin.restaurant.exceptions.NotFoundException;
import com.florin.restaurant.repository.ConfirmationTokenRepository;
import com.florin.restaurant.service.IUserDetailsService;
import com.florin.restaurant.user.MyUserDetails;
import com.florin.restaurant.user.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void saveConfirmationToken(ConfirmationToken token){
        confirmationTokenRepository.save(token);
    }

    public ConfirmationToken from(LocalDateTime createdAt, LocalDateTime expiresAt, User user){
        return ConfirmationToken.builder()
                .token(UUID.randomUUID().toString())
                .createdAt(createdAt)
                .expiresAt(expiresAt)
                .user(user)
                .build();
    }

    public String confirmToken(String token) {
      ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token)
              .orElseThrow(() -> new NotFoundException("Token Not Found"));
      if(confirmationToken.getConfirmedAt() != null){
          throw new IllegalStateException("Email already confirmed!");
      }
      if(confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())){
          throw new IllegalStateException("token expired");
      }
      confirmationToken.setConfirmedAt(LocalDateTime.now());
      confirmationToken.getUser().setEnabled(true);
       return token;
    }
}
