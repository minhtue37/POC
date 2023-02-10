package com.poc.ecommerce.reward.domain.model.commands;

import com.poc.ecommerce.reward.interfaces.rest.dtos.RewardHistoryInquiryRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RewardHistoryInquiryCommand {
    private String userId;

    public RewardHistoryInquiryCommand(RewardHistoryInquiryRequest request) {
        this.userId = request.getUserId();
    }
}
