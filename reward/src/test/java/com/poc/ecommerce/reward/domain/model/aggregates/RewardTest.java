package com.poc.ecommerce.reward.domain.model.aggregates;

import com.poc.ecommerce.reward.domain.model.commands.RewardCancelCommand;
import com.poc.ecommerce.reward.domain.model.commands.RewardCommand;
import com.poc.ecommerce.reward.domain.model.commands.RewardSendCommand;
import com.poc.ecommerce.reward.domain.model.entities.Sticker;
import com.poc.ecommerce.reward.domain.model.valueobjects.StickerType;
import com.poc.ecommerce.reward.interfaces.rest.dtos.RewardSendRequest;
import com.poc.ecommerce.reward.interfaces.rest.dtos.SKUType;
import com.poc.ecommerce.shareddomain.events.OrderCancelledEvent;
import com.poc.ecommerce.shareddomain.model.OrderItem;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class RewardTest {

    @Test
    public void testRewardSend_withNormalSKU() {
        Reward reward = new Reward(new RewardCommand("user_a"));
        RewardSendRequest rewardSendRequest = RewardSendRequest.builder()
                .userId("user_a")
                .orderId("order_1")
                .build();
        RewardSendCommand rewardSendCommand = new RewardSendCommand(rewardSendRequest);
        OrderItem orderItem = OrderItem.builder()
                .sku("sku_1")
                .type(SKUType.NORMAL)
                .amount(2L)
                .build();
        rewardSendCommand.setRewardSendDetails(Arrays.asList(orderItem));
        reward.rewardSend(rewardSendCommand);

        List<Sticker> stickers = reward.getStickers();
        Assert.assertEquals(1, stickers.size());
        Assert.assertEquals(2, (long) stickers.get(0).getAmount().getAmount());
        Assert.assertEquals(2, reward.getStickerAmount(StickerType.NORMAL));
        Assert.assertEquals(0, reward.getStickerAmount(StickerType.MISSION));
    }

    @Test
    public void testRewardSend_withMissionSKU() {
        Reward reward = new Reward(new RewardCommand("user_a"));
        RewardSendRequest rewardSendRequest = RewardSendRequest.builder()
                .userId("user_a")
                .orderId("order_1")
                .build();
        RewardSendCommand rewardSendCommand = new RewardSendCommand(rewardSendRequest);
        OrderItem orderItem = OrderItem.builder()
                .sku("sku_1")
                .type(SKUType.MISSION)
                .amount(3L)
                .build();
        rewardSendCommand.setRewardSendDetails(Arrays.asList(orderItem));
        reward.rewardSend(rewardSendCommand);
        List<Sticker> stickers = reward.getStickers();
        Assert.assertEquals(1, stickers.size());
        Assert.assertEquals(6, (long) stickers.get(0).getAmount().getAmount());
        Assert.assertEquals(0, reward.getStickerAmount(StickerType.NORMAL));
        Assert.assertEquals(6, reward.getStickerAmount(StickerType.MISSION));
    }

    @Test
    public void testCancel() {
        Reward reward = new Reward(new RewardCommand("user_a"));
        RewardSendRequest rewardSendRequest1 = RewardSendRequest.builder()
                .userId("user_a")
                .orderId("order_1")
                .build();
        RewardSendCommand rewardSendCommand1 = new RewardSendCommand(rewardSendRequest1);
        OrderItem orderItem1 = OrderItem.builder()
                .sku("sku_1")
                .type(SKUType.NORMAL)
                .amount(2L)
                .build();
        rewardSendCommand1.setRewardSendDetails(Arrays.asList(orderItem1));
        reward.rewardSend(rewardSendCommand1);

        RewardSendRequest rewardSendRequest2 = RewardSendRequest.builder()
                .userId("user_a")
                .orderId("order_2")
                .build();
        RewardSendCommand rewardSendCommand2 = new RewardSendCommand(rewardSendRequest2);
        OrderItem orderItem2 = OrderItem.builder()
                .sku("sku_1")
                .type(SKUType.MISSION)
                .amount(3L)
                .build();
        rewardSendCommand2.setRewardSendDetails(Arrays.asList(orderItem2));
        reward.rewardSend(rewardSendCommand2);

        List<Sticker> stickers = reward.getStickers();
        Assert.assertEquals(2, stickers.size());
        Assert.assertEquals(2, (long) stickers.get(0).getAmount().getAmount());
        Assert.assertEquals(6, (long) stickers.get(1).getAmount().getAmount());

        Assert.assertEquals(2, reward.getStickerAmount(StickerType.NORMAL));
        Assert.assertEquals(6, reward.getStickerAmount(StickerType.MISSION));

        OrderCancelledEvent orderCancelledEvent = OrderCancelledEvent.builder()
                .userId("user_a")
                .orderId("order_1")
                .build();
        RewardCancelCommand rewardCancelCommand = new RewardCancelCommand(orderCancelledEvent);
        reward.cancel(rewardCancelCommand);

        stickers = reward.getStickers();
        Assert.assertEquals(1, stickers.size());
        Assert.assertEquals(6, (long) stickers.get(0).getAmount().getAmount());
    }
}
