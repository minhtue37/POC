package com.poc.ecommerce.reward.domain.model.valueobjects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class User {

    @Column(name = "user_id")
    private String userId;
}
