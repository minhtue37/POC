package com.poc.ecommerce.reward.interfaces.rest;

import com.poc.ecommerce.reward.application.internal.commandservices.RewardCommandService;
import com.poc.ecommerce.reward.application.internal.queryservices.RewardQueryService;
import com.poc.ecommerce.reward.domain.model.commands.RewardHistoryInquiryCommand;
import com.poc.ecommerce.reward.domain.model.commands.RewardSendCommand;
import com.poc.ecommerce.reward.interfaces.rest.dtos.RewardHistoryInquiryRequest;
import com.poc.ecommerce.reward.interfaces.rest.dtos.RewardSendRequest;
import com.poc.ecommerce.reward.interfaces.rest.dtos.StickerHistoryResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reward")
@OpenAPIDefinition(info = @Info(title = "Reward API"))
public class RewardController {

    private final RewardCommandService rewardCommandService;

    private final RewardQueryService rewardQueryService;

    public RewardController(RewardCommandService rewardCommandService, RewardQueryService rewardQueryService) {
        this.rewardCommandService = rewardCommandService;
        this.rewardQueryService = rewardQueryService;
    }

    @PostMapping("/reward-send")
    @Operation(summary = "Save sticker",
            tags = {"Reward Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Sticker successfully saved")
    })
    public ResponseEntity<Void> rewardSend(@RequestBody RewardSendRequest request) {
        this.rewardCommandService.rewardSend(new RewardSendCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/history-inquiry")
    @Operation(summary = "Get sticker history",
            tags = {"Reward Controller"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Response successfully")})
    public ResponseEntity<StickerHistoryResponse> historyInquiry(@RequestParam String userId) {
        RewardHistoryInquiryRequest request = new RewardHistoryInquiryRequest(userId);
        StickerHistoryResponse stickerHistory = this.rewardQueryService.historyInquiry(new RewardHistoryInquiryCommand(request));
        return ResponseEntity.ok(stickerHistory);
    }

}
