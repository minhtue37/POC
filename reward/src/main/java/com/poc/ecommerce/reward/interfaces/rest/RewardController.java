package com.poc.ecommerce.reward.interfaces.rest;

import com.poc.ecommerce.reward.application.internal.commandservices.RewardCommandService;
import com.poc.ecommerce.reward.application.internal.queryservices.RewardQueryService;
import com.poc.ecommerce.reward.domain.model.commands.RewardHistoryInquiryCommand;
import com.poc.ecommerce.reward.domain.model.commands.RewardSendCommand;
import com.poc.ecommerce.reward.domain.model.valueobjects.RewardStatistics;
import com.poc.ecommerce.reward.interfaces.rest.dtos.RewardHistoryInquiryRequest;
import com.poc.ecommerce.reward.interfaces.rest.dtos.RewardSendRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reward")
public class RewardController {

    private final RewardCommandService rewardCommandService;

    private final RewardQueryService rewardQueryService;

    public RewardController(RewardCommandService rewardCommandService,
                            RewardQueryService rewardQueryService) {
        this.rewardCommandService = rewardCommandService;
        this.rewardQueryService = rewardQueryService;
    }

    @PostMapping("/reward-send")
    public ResponseEntity<Void> rewardSend(@RequestBody RewardSendRequest request) {

        this.rewardCommandService.rewardSend(new RewardSendCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/history-inquiry")
    public ResponseEntity<RewardStatistics> historyInquiry(@RequestBody RewardHistoryInquiryRequest request) {
        RewardStatistics rewardStatistics = this.rewardQueryService.historyInquiry(new RewardHistoryInquiryCommand(request));
        return ResponseEntity.ok(rewardStatistics);
    }
}
