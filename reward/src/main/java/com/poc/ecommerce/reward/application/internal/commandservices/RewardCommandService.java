package com.poc.ecommerce.reward.application.internal.commandservices;

import com.poc.ecommerce.reward.application.internal.outboundservices.acl.ExternalOrderDetailService;
import com.poc.ecommerce.reward.domain.model.aggregates.Reward;
import com.poc.ecommerce.reward.domain.model.commands.RewardCancelCommand;
import com.poc.ecommerce.reward.domain.model.commands.RewardCommand;
import com.poc.ecommerce.reward.domain.model.commands.RewardSendCommand;
import com.poc.ecommerce.shareddomain.model.OrderDetail;
import com.poc.ecommerce.reward.infrastructure.repositories.RewardRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RewardCommandService {

    private final ExternalOrderDetailService externalOrderDetailService;

    private final RewardRepository rewardRepository;

    public RewardCommandService(ExternalOrderDetailService externalOrderDetailService, RewardRepository rewardRepository) {
        this.externalOrderDetailService = externalOrderDetailService;
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
    @Transactional
    public void rewardSend(RewardSendCommand rewardSendCommand) {
        OrderDetail orderDetail = externalOrderDetailService.getOrderDetail(rewardSendCommand);
        rewardSendCommand.setRewardSendDetails(orderDetail.getOrderItems());
        String userId = rewardSendCommand.getUserId();
        Reward reward = rewardRepository.findByUserId(userId)
                .orElse(new Reward(new RewardCommand(userId)));
        reward.rewardSend(rewardSendCommand);
        rewardRepository.save(reward);
    }
}
