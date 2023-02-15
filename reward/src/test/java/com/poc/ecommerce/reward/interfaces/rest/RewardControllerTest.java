package com.poc.ecommerce.reward.interfaces.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.ecommerce.reward.application.internal.commandservices.RewardCommandService;
import com.poc.ecommerce.reward.application.internal.queryservices.RewardQueryService;
import com.poc.ecommerce.reward.domain.model.valueobjects.StickerType;
import com.poc.ecommerce.reward.interfaces.rest.dtos.RewardSendRequest;
import com.poc.ecommerce.reward.interfaces.rest.dtos.StickerHistoryResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RewardController.class)
public class RewardControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RewardCommandService rewardCommandService;

    @MockBean
    private RewardQueryService rewardQueryService;

    @Test
    public void testRewardSend_withBody() throws Exception {
        RewardSendRequest request = new RewardSendRequest();
        request.setUserId("user_a");
        request.setOrderId("order_1");
        ObjectMapper objectMapper = new ObjectMapper();
        String payload = objectMapper.writeValueAsString(request);
        mvc.perform(post("/reward/reward-send").contentType(MediaType.APPLICATION_JSON).content(payload)).
                andExpect(status().isCreated());
        verify(rewardCommandService, VerificationModeFactory.times(1)).rewardSend(Mockito.any());
        reset(rewardQueryService);
    }

    @Test
    public void testRewardSend_withoutBody() throws Exception {
        mvc.perform(post("/reward/reward-send").contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isBadRequest());
        verify(rewardCommandService, VerificationModeFactory.times(0)).rewardSend(Mockito.any());
    }

    @Test
    public void testHistoryInquiry() throws Exception {
        List<StickerHistoryResponse.Item> items = Arrays.asList(StickerHistoryResponse.Item.builder().type(StickerType.NORMAL).amount(1l).build());
        StickerHistoryResponse stickerHistory = StickerHistoryResponse.builder().items(items).sum(1l).build();
        given(rewardQueryService.historyInquiry(Mockito.any())).willReturn(stickerHistory);

        mvc.perform(get("/reward/history-inquiry?userId=fpt-user")).andExpect(status().isOk()).
                andExpect(jsonPath("$.sum", is(1))).
                andExpect(jsonPath("$.items", hasSize(equalTo(1)))).
                andExpect(jsonPath("$.items[0].type", is("NORMAL"))).
                andExpect(jsonPath("$.items[0].amount", is(1)));;
        verify(rewardQueryService, VerificationModeFactory.times(1)).historyInquiry(Mockito.any());
        reset(rewardQueryService);
    }

    @Test
    public void testHistoryInquiry_withoutParam() throws Exception {
        mvc.perform(get("/reward/history-inquiry")).andExpect(status().isBadRequest());
        verify(rewardQueryService, VerificationModeFactory.times(0)).historyInquiry(Mockito.any());
    }

}
