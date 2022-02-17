package com.florin.restaurant.service;

import com.florin.restaurant.model.Menu;
import com.florin.restaurant.model.Reward;
import java.util.List;

public interface RewardService {
    List<Reward> getRewards();
    void saveReward(Reward rewardCode);
    void deleteReward(int id);
    Reward getRewardById(int id);
}
