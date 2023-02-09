package com.poc.ecommerce.reward.domain.model.entities;

import com.poc.ecommerce.reward.domain.model.valueobjects.Order;
import com.poc.ecommerce.reward.domain.model.valueobjects.StickerAmount;
import com.poc.ecommerce.reward.domain.model.valueobjects.StickerType;

import javax.persistence.*;

@Entity
public class Sticker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Order order;

    @Embedded
    private StickerAmount amount;

    @Enumerated(EnumType.STRING)
    private StickerType type; //Sticker Type of the Sticker
}
