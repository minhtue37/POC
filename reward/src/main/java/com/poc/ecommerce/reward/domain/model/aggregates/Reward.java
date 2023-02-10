package com.poc.ecommerce.reward.domain.model.aggregates;

import com.poc.ecommerce.reward.domain.model.commands.RewardCommand;
import com.poc.ecommerce.reward.domain.model.commands.RewardSendCommand;
import com.poc.ecommerce.reward.domain.model.entities.Sticker;
import com.poc.ecommerce.reward.domain.model.events.OrderCancelledEvent;
import com.poc.ecommerce.reward.domain.model.events.StickerAccumulatedEvent;
import com.poc.ecommerce.reward.domain.model.valueobjects.RewardStatistics;
import com.poc.ecommerce.reward.domain.model.valueobjects.StickerStatistics;
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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "reward_id")
    private List<Sticker> stickers = new ArrayList<>();

    public Reward(RewardCommand rewardCommand) {
        this.userId = new UserId(rewardCommand.getUserId());
    }

    public void rewardSend(RewardSendCommand rewardSendCommand) {
        Sticker sticker = new Sticker(rewardSendCommand);
        this.addSticker(sticker);
        this.registerEvent(new StickerAccumulatedEvent());
    }

    public void cancel(String orderId) {

        this.registerEvent(new OrderCancelledEvent());
    }

    public RewardStatistics historyInquiry() {
        StickerStatistics normal = new StickerStatistics(StickerType.NORMAL, getStickerAmount(StickerType.NORMAL));
        StickerStatistics mission = new StickerStatistics(StickerType.MISSION, getStickerAmount(StickerType.MISSION));
        return new RewardStatistics(Arrays.asList(normal, mission));
    }

    private long getStickerAmount(StickerType type) {
        return this.stickers
                .stream()
                .filter(s -> s.getType() == type)
                .mapToLong(s -> s.getAmount().getAmount())
                .sum();
    }

    private void addSticker(Sticker sticker) {
        this.stickers.add(sticker);
    }
}
