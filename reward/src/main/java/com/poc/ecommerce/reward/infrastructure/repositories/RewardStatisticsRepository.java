package com.poc.ecommerce.reward.infrastructure.repositories;

import com.poc.ecommerce.reward.domain.model.valueobjects.RewardStatistics;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardStatisticsRepository extends CrudRepository<RewardStatistics, String> {
}
