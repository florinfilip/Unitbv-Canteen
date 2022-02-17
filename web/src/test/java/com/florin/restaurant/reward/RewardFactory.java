package com.florin.restaurant.reward;

import com.florin.restaurant.model.Reward;
import com.florin.restaurant.user.User;
import com.florin.restaurant.userService.UserFactory;

public class RewardFactory {

    public static Reward createBasicRewardCode(){
        User user = UserFactory.createUser();
       return Reward.builder()
                .id(123)
                .rewardCode("23RTHYP")
                .user(user)
                .build();
    }
}
