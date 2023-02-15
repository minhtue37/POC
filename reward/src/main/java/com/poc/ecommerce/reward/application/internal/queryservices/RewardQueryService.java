package com.poc.ecommerce.reward.application.internal.queryservices;

import com.poc.ecommerce.reward.domain.model.aggregates.Reward;
import com.poc.ecommerce.reward.domain.model.commands.RewardCommand;
import com.poc.ecommerce.reward.domain.model.commands.RewardHistoryInquiryCommand;
import com.poc.ecommerce.reward.domain.model.repository.RewardRepository;
import com.poc.ecommerce.reward.infrastructure.repositories.StickerHistoryCachingRepository;
import com.poc.ecommerce.reward.interfaces.rest.dtos.StickerHistoryResponse;
import com.poc.ecommerce.shareddomain.model.StickerHistory;
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

    /**
     * Inquiry sticker history.<br>
     * Query from cache first. If not exist then query from database and update in cache.
     *
     * @param command reward history inquiry command
     * @return StickerHistoryResponse sticker history response
     */
    public StickerHistoryResponse historyInquiry(RewardHistoryInquiryCommand command) {
        String userId = command.getUserId();
        StickerHistory stickerHistory = this.stickerHistoryCachingRepository.findById(userId)
                .orElse(null);
        if (stickerHistory == null) {
            Reward reward = this.rewardRepository.findByUserId(userId)
                    .orElse(new Reward(new RewardCommand(userId)));
            stickerHistory = new StickerHistory(reward);
            this.stickerHistoryCachingRepository.save(stickerHistory);
        }
        return new StickerHistoryResponse(stickerHistory);
    }
}
