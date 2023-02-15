package com.poc.ecommerce.reward.application.internal.commandservices;

import com.poc.ecommerce.reward.application.internal.outboundservices.acl.ExternalOrderDetailService;
import com.poc.ecommerce.reward.domain.model.aggregates.Reward;
import com.poc.ecommerce.reward.domain.model.commands.RewardCancelCommand;
import com.poc.ecommerce.reward.domain.model.commands.RewardSendCommand;
import com.poc.ecommerce.reward.domain.model.repository.RewardRepository;
import com.poc.ecommerce.reward.domain.model.valueobjects.UserId;
import com.poc.ecommerce.reward.interfaces.rest.dtos.RewardSendRequest;
import com.poc.ecommerce.shareddomain.events.OrderCancelledEvent;
import com.poc.ecommerce.shareddomain.model.OrderDetail;
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
import java.util.Optional;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RewardCommandService.class})
public class RewardCommandServiceTest {
    private static final String USER_A = "user_a";
    private static final String USER_B = "user_b";
    private static final String ORDER_1 = "order_1";

    @Autowired
    private RewardCommandService rewardCommandService;

    @MockBean
    private ExternalOrderDetailService externalOrderDetailService;

    @MockBean
    private RewardRepository rewardRepository;

    private Optional<Reward> mockReward(String userId) throws NoSuchFieldException, IllegalAccessException {
        Reward reward = new Reward();
        Field user = reward.getClass().getDeclaredField("userId");
        user.setAccessible(true);
        UserId userObj = new UserId(userId);
        user.set(reward, userObj);
        return Optional.of(reward);
    }

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        Mockito.when(rewardRepository.findByUserId(USER_A)).thenReturn(mockReward(USER_A));
        Mockito.when(rewardRepository.findByUserId(USER_B)).thenReturn(Optional.ofNullable(null));
        Mockito.when(externalOrderDetailService.getOrderDetail(Mockito.any())).thenReturn(OrderDetail.builder().
                orderItems(Mockito.anyList()).build());
    }

    @Test
    public void testRewardCancel_hasData() {
        OrderCancelledEvent event = OrderCancelledEvent.builder().userId(USER_A).orderId(ORDER_1).build();
        RewardCancelCommand rewardCancelCommand = new RewardCancelCommand(event);

        rewardCommandService.rewardCancel(rewardCancelCommand);
        verify(rewardRepository, VerificationModeFactory.times(1)).findByUserId(USER_A);
        verify(rewardRepository, VerificationModeFactory.times(1)).save(Mockito.any());
        reset(rewardRepository);
    }

    @Test
    public void testRewardCancel_hasNoData() {
        OrderCancelledEvent event = OrderCancelledEvent.builder().userId(USER_B).orderId(ORDER_1).build();
        RewardCancelCommand rewardCancelCommand = new RewardCancelCommand(event);

        rewardCommandService.rewardCancel(rewardCancelCommand);
        verify(rewardRepository, VerificationModeFactory.times(1)).findByUserId(USER_B);
        verify(rewardRepository, VerificationModeFactory.times(0)).save(Mockito.any());
        reset(rewardRepository);
    }

    @Test
    public void testRewardSend() {
        RewardSendRequest request = RewardSendRequest.builder().userId(USER_A).orderId(ORDER_1).build();
        RewardSendCommand rewardSendCommand = new RewardSendCommand(request);

        rewardCommandService.rewardSend(rewardSendCommand);
        verify(externalOrderDetailService, VerificationModeFactory.times(1)).
                getOrderDetail(Mockito.any());
        reset(externalOrderDetailService);
        verify(rewardRepository, VerificationModeFactory.times(1)).findByUserId(USER_A);
        verify(rewardRepository, VerificationModeFactory.times(1)).save(Mockito.any());
        reset(rewardRepository);
    }
}
