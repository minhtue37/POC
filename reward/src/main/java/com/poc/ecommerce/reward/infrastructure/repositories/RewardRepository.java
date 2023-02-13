package com.poc.ecommerce.reward.infrastructure.repositories;

import com.poc.ecommerce.reward.domain.model.aggregates.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {

    Optional<Reward> findByUserId(String userId);
}
