package com.florin.restaurant.service.Impl;

import com.florin.restaurant.exceptions.NotFoundException;
import com.florin.restaurant.model.Reward;
import com.florin.restaurant.repository.RewardRepository;
import com.florin.restaurant.service.RewardService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RewardServiceImpl implements RewardService {

    @Autowired
    private RewardRepository rewardCodeRepository;

    @Override
    public List<Reward> getRewards() {
        return rewardCodeRepository.findAll();
    }

    @Override
    public void saveReward(Reward reward) {
        rewardCodeRepository.save(reward);
    }

    @Override
    public void deleteReward(int id) {
rewardCodeRepository.deleteById(id);
    }

    @Override
    public Reward getRewardById(int id) {
       return rewardCodeRepository.findById(id)
               .orElseThrow(()->new NotFoundException("Reward not found"));
    }
}
