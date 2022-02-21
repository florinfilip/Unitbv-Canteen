package com.florin.restaurant.service.Impl;

import com.florin.restaurant.exceptions.NotFoundException;
import com.florin.restaurant.model.Reward;
import com.florin.restaurant.repository.RewardRepository;
import com.florin.restaurant.service.RewardService;
import com.florin.restaurant.user.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RewardServiceImpl implements RewardService {

    private final RewardRepository rewardRepository;

    @Override
    public List<Reward> getRewards() {
        return rewardRepository.findAll();
    }

    @Override
    public void saveReward(Reward reward, User user) {
        reward.setUser(user);
        if (!rewardCodeExists(reward)){
           try{ rewardRepository.save(reward);}
           catch (Exception ex){
               reward.setId(reward.getId());
               log.debug(ex.getMessage());
           }
        }
    }

    @Override
    public void deleteReward(int id) {
rewardRepository.deleteById(id);
    }

    @Override
    public Reward getRewardById(int id) {
       return rewardRepository.findById(id)
               .orElseThrow(()->new NotFoundException("Reward not found"));
    }

    private boolean rewardCodeExists(Reward reward){
        return rewardRepository.findByRewardCode(reward.getRewardCode()) == reward;
    }
}
