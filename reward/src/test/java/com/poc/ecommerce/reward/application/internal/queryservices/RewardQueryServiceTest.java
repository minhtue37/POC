package com.poc.ecommerce.reward.application.internal.queryservices;

import com.poc.ecommerce.reward.domain.model.aggregates.Reward;
import com.poc.ecommerce.reward.domain.model.commands.RewardHistoryInquiryCommand;
import com.poc.ecommerce.reward.domain.model.valueobjects.StickerType;
import com.poc.ecommerce.reward.domain.model.valueobjects.UserId;
import com.poc.ecommerce.reward.infrastructure.repositories.RewardRepositoryImpl;
import com.poc.ecommerce.reward.infrastructure.repositories.StickerHistoryCachingRepository;
import com.poc.ecommerce.reward.interfaces.rest.dtos.RewardHistoryInquiryRequest;
import com.poc.ecommerce.reward.interfaces.rest.dtos.StickerHistoryResponse;
import com.poc.ecommerce.shareddomain.model.StickerHistory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RewardQueryService.class})
public class RewardQueryServiceTest {
    private static final String USER_A = "user_a";
    private static final String USER_B = "user_b";

    @Autowired
    private RewardQueryService rewardQueryService;

    @MockBean
    private StickerHistoryCachingRepository stickerHistoryCachingRepository;

    @MockBean
    private RewardRepositoryImpl rewardRepository;

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        Mockito.when(stickerHistoryCachingRepository.findById(USER_A)).thenReturn(mockStickerHistory(USER_A));
        Mockito.when(stickerHistoryCachingRepository.findById(USER_B)).thenReturn(Optional.ofNullable(null));
        Mockito.when(rewardRepository.findByUserId(USER_B)).thenReturn(mockReward(USER_B));
    }

    private Optional<StickerHistory> mockStickerHistory(String userId) {
        StickerHistory.Item normalItem = new StickerHistory.Item(StickerType.NORMAL, 1l);
        StickerHistory.Item missionItem = new StickerHistory.Item(StickerType.MISSION, 2l);
        StickerHistory stickerHistory = StickerHistory.builder().id(userId).sum(3l).
                items(Arrays.asList(normalItem, missionItem)).build();
        return Optional.of(stickerHistory);
    }

    private Optional<Reward> mockReward(String userId) throws NoSuchFieldException, IllegalAccessException {
        Reward reward = new Reward();
        Field user = reward.getClass().getDeclaredField("userId");
        user.setAccessible(true);
        UserId userObj = new UserId(USER_B);
        user.set(reward, userObj);
        return Optional.of(reward);
    }

    private RewardHistoryInquiryCommand createInquiryData(String userId) {
        RewardHistoryInquiryRequest request = new RewardHistoryInquiryRequest(userId);
        return new RewardHistoryInquiryCommand(request);
    }

    @Test
    public void testHistoryInquiry_cache_hasData() {
        StickerHistoryResponse history = rewardQueryService.historyInquiry(createInquiryData(USER_A));
        assertThat(history.getSum()).isEqualTo(3l);
        assertThat(history.getItems().size()).isEqualTo(2);
        assertThat(history.getItems().stream().
                filter(item -> StickerType.NORMAL.equals(item.getType())).findFirst().get().getAmount()).isEqualTo(1l);
        assertThat(history.getItems().stream().
                filter(item -> StickerType.MISSION.equals(item.getType())).findFirst().get().getAmount()).isEqualTo(2l);

        verify(stickerHistoryCachingRepository, VerificationModeFactory.times(1)).findById(USER_A);
        reset(stickerHistoryCachingRepository);
        verify(rewardRepository, VerificationModeFactory.times(0)).findByUserId(Mockito.any());
    }

    @Test
    public void testHistoryInquiry_cache_hasNoData() {
        StickerHistoryResponse history = rewardQueryService.historyInquiry(createInquiryData(USER_B));
        assertThat(history.getSum()).isEqualTo(0l);
        assertThat(history.getItems().size()).isEqualTo(2);
        assertThat(history.getItems().stream().
                filter(item -> StickerType.NORMAL.equals(item.getType())).findFirst().get().getAmount()).isEqualTo(0l);
        assertThat(history.getItems().stream().
                filter(item -> StickerType.MISSION.equals(item.getType())).findFirst().get().getAmount()).isEqualTo(0l);
        verify(stickerHistoryCachingRepository, VerificationModeFactory.times(1)).findById(USER_B);
        verify(stickerHistoryCachingRepository, VerificationModeFactory.times(1)).save(Mockito.any());
        reset(stickerHistoryCachingRepository);
        verify(rewardRepository, VerificationModeFactory.times(1)).findByUserId(USER_B);
        reset(rewardRepository);
    }

}
