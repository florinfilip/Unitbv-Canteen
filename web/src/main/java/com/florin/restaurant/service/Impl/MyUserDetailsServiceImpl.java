package com.florin.restaurant.service.Impl;

import com.florin.restaurant.email.EmailSender;
import com.florin.restaurant.exceptions.ChangePasswordException;
import com.florin.restaurant.exceptions.NotFoundException;
import com.florin.restaurant.exceptions.SignUpException;
import com.florin.restaurant.repository.UserRepository;
import com.florin.restaurant.role.Role;
import com.florin.restaurant.service.MyUserDetailsService;
import com.florin.restaurant.token.ConfirmationToken;
import com.florin.restaurant.token.ConfirmationTokenService;
import com.florin.restaurant.user.MyUserDetails;
import com.florin.restaurant.user.User;
import com.florin.restaurant.validators.PasswordConstraintValidator;
import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MyUserDetailsServiceImpl implements MyUserDetailsService {

private final UserRepository userRepository;
private final BCryptPasswordEncoder passwordEncoder;
private final EntityManager entityManager;
private final ConfirmationTokenService confirmationTokenService;
private final EmailSender emailSender;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserByEmail(email);
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
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setEnabled(false);
        newUser.setRoles(List.of(entityManager.find(Role.class,1)));
        userRepository.save(newUser);
        final ConfirmationToken token = confirmationTokenService.from(
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                newUser);
        confirmationTokenService.saveConfirmationToken(token);
        String link = "http://localhost:8080/register/confirm?token=";
        emailSender.sendEmail(user.getEmail(),buildEmail(user.getFirstName(), link +token.getToken()));
    }
    @Override
    public void updateUser(User user){
        User newUser=userRepository.findById(user.getId()).get();
        newUser.setId(user.getId());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());

        if(passwordsDontMatch(user.getPassword(), newUser.getPassword()))
        { newUser.setPassword(passwordEncoder.encode(user.getPassword())); }
        newUser.setEnabled(user.isEnabled());
        newUser.setRoles(user.getRoles());
        userRepository.save(newUser);
    }

    private boolean passwordsDontMatch(String password , String newPassword ) {
        return !passwordEncoder.matches(password, newPassword)
        && !Objects.equals(password, newPassword)
        && !Objects.equals(password,null)
        && !Objects.equals(password,"");
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
        Optional<User> userOptional = userRepository.findUserByEmail(name);
        User loggedUser = userOptional.orElseThrow(
                ()->new UsernameNotFoundException("No user logged found"));
        return new MyUserDetails(loggedUser);
    }

     @Override
        public boolean emailExists(String email){
            return userRepository.findAll().stream()
                    .anyMatch(user->user.getEmail()
                            .equals(email));
}

    @Override
    public void changePassword(User currentUser, User modelUser, String newPassword) throws ChangePasswordException {
          if(!passwordEncoder.matches(modelUser.getPassword(), currentUser.getPassword())){
              throw new ChangePasswordException("Current password don't match");
        }
            if(passwordEncoder.matches(newPassword, currentUser.getPassword())){
                throw new ChangePasswordException("New password must differ from the current one!");
            }
            if(Strings.isNullOrEmpty(newPassword)){
                throw new ChangePasswordException("Password must not be empty!");
            }
            if(Objects.equals(modelUser.getRpassword(),newPassword)){
            currentUser.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(currentUser);}
          else
              throw new SignUpException("Passwords don't match!");
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
