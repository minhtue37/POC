package com.poc.ecommerce.reward.application.internal.eventhandlers;

import com.poc.ecommerce.reward.domain.model.events.StickerAccumulatedEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Log4j2
public class StickerAccumulatedEventHandler {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void receiveEvent(StickerAccumulatedEvent stickerAccumulatedEvent) {
        log.info("receive event");
    }
}
