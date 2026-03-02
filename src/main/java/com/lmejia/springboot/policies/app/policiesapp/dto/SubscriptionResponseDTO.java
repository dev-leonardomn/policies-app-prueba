package com.lmejia.springboot.policies.app.policiesapp.dto;

import com.lmejia.springboot.policies.app.policiesapp.entities.Client;
import com.lmejia.springboot.policies.app.policiesapp.entities.Policy;
import com.lmejia.springboot.policies.app.policiesapp.entities.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionResponseDTO {
    private Long id;
    private String policyDescription;
    private String policyType;
    private BigDecimal totalPrice;
    private String status;
}
