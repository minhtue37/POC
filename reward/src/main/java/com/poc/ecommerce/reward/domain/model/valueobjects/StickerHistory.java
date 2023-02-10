package com.poc.ecommerce.reward.domain.model.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.util.Collections;
import java.util.List;

@Getter
@NoArgsConstructor
@RedisHash("StickerHistory")
public class StickerHistory {
    private String id;
    private Long sum = 0L;
    private List<Item> items = Collections.emptyList();

    public StickerHistory(String id, List<Item> items) {
        this.id = id;
        this.items = items;
        this.sum = this.items
                .stream()
                .mapToLong(Item::getAmount)
                .sum();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Item {
        private StickerType type;
        private Long amount = 0L;
    }
}
