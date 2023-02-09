package com.poc.ecommerce.reward.domain.model.valueobjects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class StickerAmount {

    @Column(name = "amount")
    private Long amount;
}
