package com.lmejia.springboot.policies.app.policiesapp.dto;

import com.lmejia.springboot.policies.app.policiesapp.entities.IdentificationTypeEnum;
import com.lmejia.springboot.policies.app.policiesapp.entities.Subscription;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDTO {
    private Long id;
    private IdentificationTypeEnum identificationType;
    private String identificationNumber;
    private String name;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDateTime birthDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
