package com.poc.ecommerce.reward.domain.model.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StickerAmount {
    public static final Long NORMAL_STICKER_PER_PRODUCT = 1L;
    public static final Long MISSION_STICKER_PER_PRODUCT = 2L;

    @Column(name = "amount")
    private Long amount;
}
