package com.poc.ecommerce.reward.domain.model.entities;

import com.poc.ecommerce.reward.domain.model.valueobjects.OrderId;
import com.poc.ecommerce.reward.domain.model.valueobjects.StickerAmount;
import com.poc.ecommerce.reward.domain.model.valueobjects.StickerType;
import com.poc.ecommerce.reward.interfaces.rest.dtos.SKUType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.poc.ecommerce.reward.domain.model.valueobjects.StickerAmount.MISSION_STICKER_PER_PRODUCT;
import static com.poc.ecommerce.reward.domain.model.valueobjects.StickerAmount.NORMAL_STICKER_PER_PRODUCT;

@Entity
@Getter
@NoArgsConstructor
public class Sticker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private OrderId orderId;

    @Embedded
    private StickerAmount amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private StickerType type; //Sticker Type of the Sticker

    public Sticker(String orderId, SKUType skuType, Long amount) {
        this.orderId = new OrderId(orderId);
        this.type = skuType == SKUType.NORMAL ? StickerType.NORMAL : StickerType.MISSION;
        this.calculateStickerAmount(amount);
    }

    public void calculateStickerAmount(Long amount) {
        this.amount = type == StickerType.NORMAL ?
                new StickerAmount(NORMAL_STICKER_PER_PRODUCT * amount) : new StickerAmount(MISSION_STICKER_PER_PRODUCT * amount);
    }
}
