package com.poc.ecommerce.reward.infrastructure.repositories;

import com.poc.ecommerce.reward.domain.model.aggregates.Reward;
import com.poc.ecommerce.reward.domain.model.repository.RewardRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RewardRepositoryImpl implements RewardRepository {

    private final RewardJPARepository rewardJPARepository;

    public RewardRepositoryImpl(RewardJPARepository rewardJPARepository) {
        this.rewardJPARepository = rewardJPARepository;
    }

    @Override
    public void save(Reward reward) {
        this.rewardJPARepository.save(reward);
    }

    @Override
    public Optional<Reward> findByUserId(String userId) {
        return this.rewardJPARepository.findByUserId(userId);
    }

}
