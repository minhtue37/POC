package com.poc.ecommerce.reward.domain.model.entities;

import com.poc.ecommerce.reward.domain.model.commands.RewardSendCommand;
import com.poc.ecommerce.reward.domain.model.valueobjects.OrderId;
import com.poc.ecommerce.reward.domain.model.valueobjects.StickerAmount;
import com.poc.ecommerce.reward.domain.model.valueobjects.StickerType;
import com.poc.ecommerce.reward.interfaces.rest.dtos.SKUType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Sticker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

    @Embedded
    @Setter
    private OrderId orderId;

    @Embedded
    private StickerAmount amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private StickerType type; //Sticker Type of the Sticker

    public Sticker(RewardSendCommand rewardSendCommand) {
        this.orderId = new OrderId(rewardSendCommand.getOrderId());
        this.type = rewardSendCommand.getSkuType() == SKUType.NORMAL ? StickerType.NORMAL : StickerType.MISSION;
        this.calculateStickerAmount();
    }

    public void calculateStickerAmount() {
        this.amount = type == StickerType.NORMAL ? new StickerAmount(1L) : new StickerAmount(2L);
    }
}
