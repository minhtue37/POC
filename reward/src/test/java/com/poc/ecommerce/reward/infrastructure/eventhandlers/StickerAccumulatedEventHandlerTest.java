package com.poc.ecommerce.reward.infrastructure.eventhandlers;

import com.poc.ecommerce.reward.domain.model.aggregates.Reward;
import com.poc.ecommerce.reward.domain.model.events.StickerAccumulatedEvent;
import com.poc.ecommerce.reward.domain.model.valueobjects.UserId;
import com.poc.ecommerce.reward.infrastructure.repositories.StickerHistoryCachingRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {StickerAccumulatedEventHandler.class})
public class StickerAccumulatedEventHandlerTest {

    @Autowired
    private StickerAccumulatedEventHandler stickerAccumulatedEventHandler;

    @MockBean
    private StickerHistoryCachingRepository stickerHistoryCachingRepository;

    @Test
    public void testReceiveEvent_hasNotCacheBefore() throws NoSuchFieldException, IllegalAccessException {
        Reward reward = new Reward();
        Field user = reward.getClass().getDeclaredField("userId");
        user.setAccessible(true);
        UserId userObj = new UserId("user_a");
        user.set(reward, userObj);
        Mockito.when(stickerHistoryCachingRepository.existsById(Mockito.any())).thenReturn(false);

        stickerAccumulatedEventHandler.receiveEvent(new StickerAccumulatedEvent(reward));
        verify(stickerHistoryCachingRepository, VerificationModeFactory.times(1)).existsById(Mockito.any());
        verify(stickerHistoryCachingRepository, VerificationModeFactory.times(0)).save(Mockito.any());
        reset(stickerHistoryCachingRepository);
    }

    @Test
    public void testReceiveEvent_hasCacheBefore() throws NoSuchFieldException, IllegalAccessException {
        Reward reward = new Reward();
        Field user = reward.getClass().getDeclaredField("userId");
        user.setAccessible(true);
        UserId userObj = new UserId("user_a");
        user.set(reward, userObj);
        Mockito.when(stickerHistoryCachingRepository.existsById(Mockito.any())).thenReturn(true);

        stickerAccumulatedEventHandler.receiveEvent(new StickerAccumulatedEvent(reward));
        verify(stickerHistoryCachingRepository, VerificationModeFactory.times(1)).existsById(Mockito.any());
        verify(stickerHistoryCachingRepository, VerificationModeFactory.times(1)).save(Mockito.any());
        reset(stickerHistoryCachingRepository);
    }

}
