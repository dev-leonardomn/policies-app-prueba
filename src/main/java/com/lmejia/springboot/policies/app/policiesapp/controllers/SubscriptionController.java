package com.lmejia.springboot.policies.app.policiesapp.controllers;

import com.lmejia.springboot.policies.app.policiesapp.dto.BeneficiaryResponseDTO;
import com.lmejia.springboot.policies.app.policiesapp.dto.SubscriptionRequestDTO;
import com.lmejia.springboot.policies.app.policiesapp.dto.SubscriptionResponseDTO;
import com.lmejia.springboot.policies.app.policiesapp.entities.Subscription;
import com.lmejia.springboot.policies.app.policiesapp.entities.SubscriptionBeneficiary;
import com.lmejia.springboot.policies.app.policiesapp.mapper.SubscriptionMapper;
import com.lmejia.springboot.policies.app.policiesapp.services.ISubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscription")
@RequiredArgsConstructor
@Tag(name = "Subscriptions", description = "Operation releated for managing subscriptions")
public class SubscriptionController {
    private final ISubscriptionService subscriptionService;
    private final SubscriptionMapper mapper;

    @Operation(
            summary = "Create subscription",
            description = "Creates a new subscription associated with a client and policy"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Subscription created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "404", description = "Policy not found")
    })
    @PostMapping
    public ResponseEntity<SubscriptionResponseDTO> create(@RequestBody SubscriptionRequestDTO request) {
        Subscription sub = subscriptionService.createSubscription(request);
        return new ResponseEntity<>(mapper.toResponseDto(sub), HttpStatus.CREATED);
    }

    @Operation(summary = "Get subscription by client", description = "Get subscription list associated with a client")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Subscription retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
    })
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<SubscriptionResponseDTO>> getByClient(@Parameter(description = "Client identifier", required = true) @PathVariable Long clientId) {
        List<Subscription> subs = subscriptionService.getSubscriptionsById(clientId);
        return ResponseEntity.ok(mapper.toResponseDtoList(subs));
    }

    @Operation(summary = "Get subscription details", description = "Get a subscription detail")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Subscription retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Subscription not found"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionResponseDTO> getDetail(@Parameter(description = "Subscription identifier", required = true) @PathVariable Long id) {
        return subscriptionService.getSubscriptionDetail(id)
                .map(mapper::toResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get beneficiaries list", description = "Returns the complete list of beneficiaries by subscription")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Subscription retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Subscription not found"),
    })
    @GetMapping("/{id}/beneficiaries")
    public ResponseEntity<List<BeneficiaryResponseDTO>> getBeneficiaries(@Parameter(description = "Subscription identifier", required = true) @PathVariable Long id) {
        List<SubscriptionBeneficiary> beneficiaries = subscriptionService.getBeneficiariesBySubscriptionId(id);
        return ResponseEntity.ok(mapper.toBeneficiaryDtoList(beneficiaries));
    }

}
