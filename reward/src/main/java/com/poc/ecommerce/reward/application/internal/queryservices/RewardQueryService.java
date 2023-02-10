package com.poc.ecommerce.reward.application.internal.queryservices;

import com.poc.ecommerce.reward.domain.model.aggregates.Reward;
import com.poc.ecommerce.reward.domain.model.commands.RewardCommand;
import com.poc.ecommerce.reward.domain.model.commands.RewardHistoryInquiryCommand;
import com.poc.ecommerce.reward.domain.model.valueobjects.StickerHistory;
import com.poc.ecommerce.reward.infrastructure.repositories.RewardRepository;
import com.poc.ecommerce.reward.infrastructure.repositories.StickerHistoryCachingRepository;
import org.springframework.stereotype.Service;

@Service
public class RewardQueryService {

    private final RewardRepository rewardRepository;

    private final StickerHistoryCachingRepository stickerHistoryCachingRepository;

    public RewardQueryService(RewardRepository rewardRepository,
                              StickerHistoryCachingRepository stickerHistoryCachingRepository) {
        this.rewardRepository = rewardRepository;
        this.stickerHistoryCachingRepository = stickerHistoryCachingRepository;
    }

    public StickerHistory historyInquiry(RewardHistoryInquiryCommand command) {
        String userId = command.getUserId();
        StickerHistory stickerHistory = this.stickerHistoryCachingRepository.findById(userId)
                .orElse(null);
        if (stickerHistory == null) {
            Reward reward = this.rewardRepository.findByUserId(userId)
                    .orElse(new Reward(new RewardCommand(userId)));
            stickerHistory = reward.historyInquiry();
            this.stickerHistoryCachingRepository.save(stickerHistory);
        }
        return stickerHistory;
    }
}
