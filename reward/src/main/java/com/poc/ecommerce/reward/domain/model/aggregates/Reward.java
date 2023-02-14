package com.poc.ecommerce.reward.domain.model.aggregates;

import com.poc.ecommerce.reward.domain.model.commands.RewardCancelCommand;
import com.poc.ecommerce.reward.domain.model.commands.RewardCommand;
import com.poc.ecommerce.reward.domain.model.commands.RewardSendCommand;
import com.poc.ecommerce.reward.domain.model.entities.Sticker;
import com.poc.ecommerce.reward.domain.model.events.StickerAccumulatedEvent;
import com.poc.ecommerce.reward.domain.model.events.StickerDeductionEvent;
import com.poc.ecommerce.reward.domain.model.valueobjects.StickerHistory;
import com.poc.ecommerce.reward.domain.model.valueobjects.StickerType;
import com.poc.ecommerce.reward.domain.model.valueobjects.UserId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Reward.findByUserId",
                query = "Select r from Reward r where r.userId.userId = :userId")})
public class Reward extends AbstractAggregateRoot<Reward> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private UserId userId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "reward_id")
    private List<Sticker> stickers = new ArrayList<>();

    public Reward(RewardCommand rewardCommand) {
        this.userId = new UserId(rewardCommand.getUserId());
    }

    public void rewardSend(RewardSendCommand rewardSendCommand) {
        List<Sticker> stickers = rewardSendCommand.getRewardSendDetails().stream().
                map(item -> new Sticker(rewardSendCommand.getOrderId(), item.getSkuType(), item.getAmount())).
                collect(Collectors.toList());
        this.addStickers(stickers);
        this.registerEvent(new StickerAccumulatedEvent(this));
    }

    public void cancel(RewardCancelCommand command) {
        String orderId = command.getOrderId();
        List<Sticker> remainStickers = this.stickers.stream()
                .filter(s -> !s.getOrderId().getOrderId().equals(orderId))
                .collect(Collectors.toList());
        this.stickers.clear();
        this.stickers.addAll(remainStickers);

        this.registerEvent(new StickerDeductionEvent(this));
    }

    public StickerHistory historyInquiry() {
        StickerHistory.Item normal = new StickerHistory.Item(StickerType.NORMAL, getStickerAmount(StickerType.NORMAL));
        StickerHistory.Item mission = new StickerHistory.Item(StickerType.MISSION, getStickerAmount(StickerType.MISSION));
        return new StickerHistory(this.userId.getUserId(), Arrays.asList(normal, mission));
    }

    private long getStickerAmount(StickerType type) {
        return this.stickers
                .stream()
                .filter(s -> s.getType() == type)
                .mapToLong(s -> s.getAmount().getAmount())
                .sum();
    }

    private void addStickers(List<Sticker> stickers) {
        this.stickers.addAll(stickers);
    }
}
