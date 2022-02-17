package com.florin.restaurant.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.florin.restaurant.annotations.ValidPassword;
import com.florin.restaurant.model.Reward;
import com.florin.restaurant.role.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
    @Size(min=10, max=30)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean enabled;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="user_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
            )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Role> roles;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public List<String> getRoleNames(){

      return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }

    @OneToMany(orphanRemoval = true, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reward> rewards;


}
