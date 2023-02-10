package com.poc.ecommerce.reward.domain.model.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RewardCommand {
    private String userId;
}
