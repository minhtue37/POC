package com.poc.ecommerce.shareddomain.model;

import com.poc.ecommerce.reward.domain.model.aggregates.Reward;
import com.poc.ecommerce.reward.domain.model.valueobjects.StickerType;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("StickerHistory")
public class StickerHistory {
    private String id;
    private Long sum = 0L;
    private List<Item> items = Collections.emptyList();

    public StickerHistory(Reward reward) {
        StickerHistory.Item normal = new StickerHistory.Item(StickerType.NORMAL, reward.getStickerAmount(StickerType.NORMAL));
        StickerHistory.Item mission = new StickerHistory.Item(StickerType.MISSION, reward.getStickerAmount(StickerType.MISSION));
        this.id = reward.getUserId().getUserId();
        this.items = Arrays.asList(normal, mission);
        this.sum = this.items
                .stream()
                .mapToLong(Item::getAmount)
                .sum();
    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Item {
        private StickerType type;
        private Long amount = 0L;
    }
}
