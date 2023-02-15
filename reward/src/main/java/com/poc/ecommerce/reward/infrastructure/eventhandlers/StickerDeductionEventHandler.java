package com.poc.ecommerce.reward.infrastructure.eventhandlers;

import com.poc.ecommerce.reward.domain.model.aggregates.Reward;
import com.poc.ecommerce.reward.domain.model.events.StickerDeductionEvent;
import com.poc.ecommerce.reward.infrastructure.repositories.StickerHistoryCachingRepository;
import com.poc.ecommerce.shareddomain.model.StickerHistory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class StickerDeductionEventHandler {

    private final StickerHistoryCachingRepository stickerHistoryCachingRepository;

    public StickerDeductionEventHandler(StickerHistoryCachingRepository stickerHistoryCachingRepository) {
        this.stickerHistoryCachingRepository = stickerHistoryCachingRepository;
    }

    /**
     * Receive sticker deduction event then update sticker history cache
     *
     * @param event sticker deduction event
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void receiveEvent(StickerDeductionEvent event) {
        Reward reward = event.getReward();
        boolean isCached = this.stickerHistoryCachingRepository.existsById(reward.getUserId().getUserId());
        // just update the cache when it is retrieved before
        if (isCached) {
            this.stickerHistoryCachingRepository.save(new StickerHistory(reward));
        }
    }
}
