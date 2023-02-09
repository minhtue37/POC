package com.poc.ecommerce.reward.application.internal.commandservices;

import com.poc.ecommerce.reward.domain.model.commands.RewardCancelCommand;
import com.poc.ecommerce.reward.domain.model.commands.RewardSendCommand;
import com.poc.ecommerce.reward.infrastructure.repositories.RewardRepository;
import org.springframework.stereotype.Service;

@Service
public class RewardCommandService {

    private final RewardRepository rewardRepository;

    public RewardCommandService(RewardRepository rewardRepository) {
        this.rewardRepository = rewardRepository;
    }

    public void cancel(RewardCancelCommand rewardCancelCommand) {
    }

    public void rewardSend(RewardSendCommand rewardSendCommand) {
        // find Reward by userId
        // Not exist => new Reward(rewardSendCommand)
        //reward.rewardSend(params)
    }
}
