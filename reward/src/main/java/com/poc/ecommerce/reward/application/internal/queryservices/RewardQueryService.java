package com.poc.ecommerce.reward.application.internal.queryservices;

import com.poc.ecommerce.reward.domain.model.aggregates.Reward;
import com.poc.ecommerce.reward.domain.model.commands.RewardHistoryInquiryCommand;
import com.poc.ecommerce.reward.domain.model.valueobjects.RewardStatistics;
import com.poc.ecommerce.reward.infrastructure.repositories.RewardRepository;
import org.springframework.stereotype.Service;

@Service
public class RewardQueryService {

    private final RewardRepository rewardRepository;

    public RewardQueryService(RewardRepository rewardRepository) {
        this.rewardRepository = rewardRepository;
    }

    public RewardStatistics historyInquiry(RewardHistoryInquiryCommand command) {
        String userId = command.getUserId();
        Reward reward = rewardRepository.findByUserId(userId)
                .orElse(new Reward());
        return reward.historyInquiry();
    }
}
