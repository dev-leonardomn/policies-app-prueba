package com.lmejia.springboot.policies.app.policiesapp.dto;

import com.lmejia.springboot.policies.app.policiesapp.entities.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionRequestDTO {
    private Long idClient;
    private Long idPolicy;
    private SubscriptionStatus status;
    List<BeneficiaryDTO> beneficiaries;
    private List<Long> vehicleIds;
}
