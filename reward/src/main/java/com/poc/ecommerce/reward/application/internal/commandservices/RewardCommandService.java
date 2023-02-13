package com.poc.ecommerce.reward.application.internal.commandservices;

import com.poc.ecommerce.reward.domain.model.aggregates.Reward;
import com.poc.ecommerce.reward.domain.model.commands.RewardCancelCommand;
import com.poc.ecommerce.reward.domain.model.commands.RewardCommand;
import com.poc.ecommerce.reward.domain.model.commands.RewardSendCommand;
import com.poc.ecommerce.reward.infrastructure.repositories.RewardRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RewardCommandService {

    private final RewardRepository rewardRepository;

    public RewardCommandService(RewardRepository rewardRepository) {
        this.rewardRepository = rewardRepository;
    }

    /**
     * Cancel reward
     *
     * @param rewardCancelCommand reward cancel command
     */
    @Transactional
    public void rewardCancel(RewardCancelCommand rewardCancelCommand) {
        String userId = rewardCancelCommand.getUserId();
        Reward reward = this.rewardRepository.findByUserId(userId).orElse(null);
        if (reward == null) {
            return;
        }

        reward.cancel(rewardCancelCommand);
        this.rewardRepository.save(reward);
    }

    /**
     * Send reward
     *
     * @param rewardSendCommand reward send command
     */
    public void rewardSend(RewardSendCommand rewardSendCommand) {
        String userId = rewardSendCommand.getUserId();
        Reward reward = rewardRepository.findByUserId(userId)
                .orElse(new Reward(new RewardCommand(userId)));
        reward.rewardSend(rewardSendCommand);
        rewardRepository.save(reward);
    }
}
