package com.poc.ecommerce.reward.domain.model.valueobjects;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@RedisHash("RewardStatistics")
public class RewardStatistics implements Serializable {
    private String id;
    private Long sum;
    private List<StickerStatistics> stickers;

    public RewardStatistics(List<StickerStatistics> stickers) {
        this.id = UUID.randomUUID().toString();
        this.stickers = stickers;
        this.sum = this.stickers
                .stream()
                .mapToLong(StickerStatistics::getAmount)
                .sum();
    }
}
