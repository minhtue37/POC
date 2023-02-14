package com.poc.ecommerce.reward.domain.model.repository;

import com.poc.ecommerce.reward.domain.model.aggregates.Reward;

import java.util.Optional;

public interface RewardRepository {

    void save(Reward reward);

    Optional<Reward> findByUserId(String userId);
}
