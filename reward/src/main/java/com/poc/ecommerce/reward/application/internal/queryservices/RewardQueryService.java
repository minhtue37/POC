package com.poc.ecommerce.reward.application.internal.queryservices;

import com.poc.ecommerce.reward.domain.model.aggregates.Reward;
import com.poc.ecommerce.reward.domain.model.commands.RewardHistoryInquiryCommand;
import com.poc.ecommerce.reward.domain.model.valueobjects.RewardStatistics;
import com.poc.ecommerce.reward.infrastructure.repositories.RewardRepository;
import com.poc.ecommerce.reward.infrastructure.repositories.RewardStatisticsRepository;
import org.springframework.stereotype.Service;

@Service
public class RewardQueryService {

    private final RewardRepository rewardRepository;

    private final RewardStatisticsRepository rewardStatisticsRepository;

    public RewardQueryService(RewardRepository rewardRepository,
                              RewardStatisticsRepository rewardStatisticsRepository) {
        this.rewardRepository = rewardRepository;
        this.rewardStatisticsRepository = rewardStatisticsRepository;
    }

    public RewardStatistics historyInquiry(RewardHistoryInquiryCommand command) {
        String userId = command.getUserId();
        Reward reward = this.rewardRepository.findByUserId(userId)
                .orElse(new Reward());
        RewardStatistics rewardStatistics = reward.historyInquiry();
        return rewardStatistics;
    }
}
