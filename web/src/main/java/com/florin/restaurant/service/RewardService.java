package com.florin.restaurant.service;

import com.florin.restaurant.model.Reward;
import com.florin.restaurant.user.User;

import java.util.List;

public interface RewardService {
    List<Reward> getRewards();
    void saveReward(Reward rewardCode, User user);
    void deleteReward(int id);
    Reward getRewardById(int id);
}
