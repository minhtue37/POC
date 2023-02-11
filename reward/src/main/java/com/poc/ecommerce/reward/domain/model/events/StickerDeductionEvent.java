package com.poc.ecommerce.reward.domain.model.events;

import com.poc.ecommerce.reward.domain.model.aggregates.Reward;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StickerDeductionEvent {
    private Reward reward;
}
