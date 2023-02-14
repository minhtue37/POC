package com.poc.ecommerce.reward.interfaces.rest.dtos;

import com.poc.ecommerce.shareddomain.model.StickerHistory;
import com.poc.ecommerce.reward.domain.model.valueobjects.StickerType;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class StickerHistoryResponse {
    private Long sum;
    private List<Item> items;

    public StickerHistoryResponse(StickerHistory stickerHistory) {
        this.items = stickerHistory.getItems()
                .stream()
                .map(Item::new)
                .collect(Collectors.toList());
        this.sum = this.items
                .stream()
                .mapToLong(StickerHistoryResponse.Item::getAmount)
                .sum();
    }

    @Getter
    public static class Item {
        private StickerType type;
        private Long amount;

        public Item(StickerHistory.Item item) {
            this.type = item.getType();
            this.amount = item.getAmount();
        }
    }
}
