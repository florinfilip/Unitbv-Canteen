package com.florin.restaurant.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.florin.restaurant.annotations.ValidPassword;
import com.florin.restaurant.model.Reward;
import com.florin.restaurant.role.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@Entity
@Table(name="user",schema = "public")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "You must provide a username!")
    @Size(min=5, max=30)
    private String username;

    @NotEmpty(message = "You must provide a password!")
    @ValidPassword
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String rpassword;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean enabled;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "user_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "role_id"
            ))
    private List<Role> roles;

    @JoinColumn(name="last_played")
    private LocalDate lastPlayed;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public List<String> getRoleNames(){
      return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }

    @OneToMany(orphanRemoval = true,mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reward> rewards;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", roles=" + roles +
                ", lastPlayed=" + lastPlayed +
                ", rewards=" + rewards +
                '}';
    }
}
