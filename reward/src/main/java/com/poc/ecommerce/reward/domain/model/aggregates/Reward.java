package com.poc.ecommerce.reward.domain.model.aggregates;

import com.poc.ecommerce.reward.domain.model.entities.Sticker;
import com.poc.ecommerce.reward.domain.model.events.OrderCancelledEvent;
import com.poc.ecommerce.reward.domain.model.events.StickerAccumulatedEvent;
import com.poc.ecommerce.reward.domain.model.valueobjects.User;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
public class Reward extends AbstractAggregateRoot<Reward> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "reward_id")
    private List<Sticker> stickers = Collections.emptyList();

    public void rewardSend() {

        registerEvent(new StickerAccumulatedEvent());
    }

    public void cancel(String orderId) {

        registerEvent(new OrderCancelledEvent());
    }

    public void historyInquiry() {
    }
}
