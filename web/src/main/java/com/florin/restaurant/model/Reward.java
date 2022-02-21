package com.florin.restaurant.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.florin.restaurant.game.CodeGenerator;
import com.florin.restaurant.user.User;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Builder
@Table(schema = "public", name = "reward")
@AllArgsConstructor
@NoArgsConstructor
public class Reward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reward_id", nullable = false)
    private int id;

    @Size(min=6, max=6)
    @NotBlank
    @Column(name = "reward_code", length = 6)
    private final String rewardCode = CodeGenerator.generateRewardCode();

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "user_id")
    private User user;
}
