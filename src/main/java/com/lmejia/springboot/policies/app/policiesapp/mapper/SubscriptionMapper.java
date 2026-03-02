package com.lmejia.springboot.policies.app.policiesapp.mapper;

import com.lmejia.springboot.policies.app.policiesapp.dto.BeneficiaryResponseDTO;
import com.lmejia.springboot.policies.app.policiesapp.dto.SubscriptionResponseDTO;
import com.lmejia.springboot.policies.app.policiesapp.entities.Subscription;
import com.lmejia.springboot.policies.app.policiesapp.entities.SubscriptionBeneficiary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {
    @Mapping(target = "policyDescription", source = "policy.description")
    @Mapping(target = "policyType", source = "policy.policyType.name")
    SubscriptionResponseDTO toResponseDto(Subscription subscription);

    List<SubscriptionResponseDTO> toResponseDtoList(List<Subscription> subscriptions);

    @Mapping(target = "fullName", expression = "java(sb.getBeneficiary().getName() + ' ' + sb.getBeneficiary().getLastName())")
    @Mapping(target = "price", source = "totalPrice")
    BeneficiaryResponseDTO toBeneficiaryDto(SubscriptionBeneficiary sb);

    List<BeneficiaryResponseDTO> toBeneficiaryDtoList(List<SubscriptionBeneficiary> list);
}
