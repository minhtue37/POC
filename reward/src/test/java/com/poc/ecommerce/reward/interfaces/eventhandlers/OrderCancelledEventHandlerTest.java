package com.poc.ecommerce.reward.interfaces.eventhandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.ecommerce.reward.application.internal.commandservices.RewardCommandService;
import com.poc.ecommerce.shareddomain.events.OrderCancelledEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {OrderCancelledEventHandler.class})
public class OrderCancelledEventHandlerTest {

    @Autowired
    private OrderCancelledEventHandler orderCancelledEventHandler;

    @MockBean
    private RewardCommandService rewardCommandService;

    @Test
    public void testReceive_correctMsg() throws JsonProcessingException {
        OrderCancelledEvent event = OrderCancelledEvent.builder().userId("user_A").orderId("order_1").build();
        ObjectMapper objectMapper = new ObjectMapper();
        String msg = "{ \"userId\" : \"userId\", \"orderId\" : \"orderId\" }";

        orderCancelledEventHandler.receive(msg);
        verify(rewardCommandService, VerificationModeFactory.times(1)).rewardCancel(Mockito.any());
        reset(rewardCommandService);
    }

    @Test
    public void testReceive_incorrectMsg() {
        orderCancelledEventHandler.receive("incorrect msg");
        verify(rewardCommandService, VerificationModeFactory.times(0)).rewardCancel(Mockito.any());
        reset(rewardCommandService);
    }
}
