package com.poc.ecommerce.reward.application.internal.eventhandlers;

import com.poc.ecommerce.reward.domain.model.aggregates.Reward;
import com.poc.ecommerce.reward.domain.model.events.OrderCancelledEvent;
import com.poc.ecommerce.reward.infrastructure.repositories.StickerHistoryCachingRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderCancelledEventHandler {

    private final StickerHistoryCachingRepository stickerHistoryCachingRepository;

    public OrderCancelledEventHandler(StickerHistoryCachingRepository stickerHistoryCachingRepository) {
        this.stickerHistoryCachingRepository = stickerHistoryCachingRepository;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void receiveEvent(OrderCancelledEvent event) {
        Reward reward = event.getReward();
        this.stickerHistoryCachingRepository.save(reward.historyInquiry());
    }
}
