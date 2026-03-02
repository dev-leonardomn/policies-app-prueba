package com.lmejia.springboot.policies.app.policiesapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeneficiaryDTO {
    private Long beneficiaryId;
    private String relationshipType;
    private BigDecimal price;
}
