package com.lmejia.springboot.policies.app.policiesapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleDTO {
    private Long id;
    private String brand;
    private String model;
    private String type;
    private String registrationCertificateNumber;

}
