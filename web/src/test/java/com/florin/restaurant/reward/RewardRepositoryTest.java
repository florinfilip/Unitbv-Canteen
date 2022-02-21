package com.florin.restaurant.reward;


import com.florin.restaurant.model.Reward;
import com.florin.restaurant.repository.RewardRepository;
import com.florin.restaurant.repository.UserRepository;
import com.florin.restaurant.user.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback
public class RewardRepositoryTest {

    @Autowired
    private RewardRepository rewardRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;

    @Test
    void givenRewardCodeSaveToDatabase(){
        addRewardToDatabase();
        assertThat(rewardRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    void givenInputInDataBaseDeleteWithoutModifyUserTable(){
addRewardToDatabase();
rewardRepository.deleteAll();
assertThat(rewardRepository.findAll().size()).isEqualTo(0);
assertThat(userRepository.findAll().size()).isEqualTo(3);
    }

    private void addRewardToDatabase() {
        Reward reward= Reward.builder()
                .user(entityManager.find(User.class,1))
                .build();
        rewardRepository.save(reward);
    }
}
