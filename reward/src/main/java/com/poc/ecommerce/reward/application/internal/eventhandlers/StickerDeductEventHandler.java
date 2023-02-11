package com.poc.ecommerce.reward.application.internal.eventhandlers;

import com.poc.ecommerce.reward.domain.model.events.OrderCancelledEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class StickerDeductEventHandler {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void receiveEvent(OrderCancelledEvent orderCancelledEvent) {
    }
}
