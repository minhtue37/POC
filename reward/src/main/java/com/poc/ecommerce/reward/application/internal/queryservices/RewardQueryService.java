package com.poc.ecommerce.reward.application.internal.queryservices;

import com.poc.ecommerce.reward.domain.model.commands.RewardHistoryInquiryCommand;
import com.poc.ecommerce.reward.infrastructure.repositories.RewardRepository;
import org.springframework.stereotype.Service;

@Service
public class RewardQueryService {

    private final RewardRepository rewardRepository;

    public RewardQueryService(RewardRepository rewardRepository) {
        this.rewardRepository = rewardRepository;
    }

    public void historyInquiry(RewardHistoryInquiryCommand rewardHistoryInquiryCommand) {
    }
}
