package com.florin.restaurant.repository;

import com.florin.restaurant.model.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardRepository extends JpaRepository<Reward,Integer> {
    Reward findByRewardCode(String rewardCode);
}
