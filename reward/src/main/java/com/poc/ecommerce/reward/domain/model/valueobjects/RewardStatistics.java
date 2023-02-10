package com.poc.ecommerce.reward.domain.model.valueobjects;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RewardStatistics {
    private Long sum;
    private List<StickerStatistics> stickers;

    public RewardStatistics(List<StickerStatistics> stickers) {
        this.stickers = stickers;
        this.sum = this.stickers
                .stream()
                .mapToLong(StickerStatistics::getAmount)
                .sum();
    }
}
