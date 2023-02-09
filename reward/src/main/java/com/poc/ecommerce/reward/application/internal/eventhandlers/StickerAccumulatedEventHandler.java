package com.poc.ecommerce.reward.application.internal.eventhandlers;

import com.poc.ecommerce.reward.domain.model.events.StickerAccumulatedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class StickerAccumulatedEventHandler {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void receiveEvent(StickerAccumulatedEvent stickerAccumulatedEvent) {
    }
}
