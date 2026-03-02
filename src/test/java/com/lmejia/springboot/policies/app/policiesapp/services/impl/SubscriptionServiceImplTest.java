package com.lmejia.springboot.policies.app.policiesapp.services.impl;

import com.lmejia.springboot.policies.app.policiesapp.dto.BeneficiaryDTO;
import com.lmejia.springboot.policies.app.policiesapp.dto.SubscriptionRequestDTO;
import com.lmejia.springboot.policies.app.policiesapp.entities.Client;
import com.lmejia.springboot.policies.app.policiesapp.entities.Policy;
import com.lmejia.springboot.policies.app.policiesapp.entities.PolicyType;
import com.lmejia.springboot.policies.app.policiesapp.exceptions.BusinessRuleException;
import com.lmejia.springboot.policies.app.policiesapp.repository.ClientRepository;
import com.lmejia.springboot.policies.app.policiesapp.repository.PolicyRepository;
import com.lmejia.springboot.policies.app.policiesapp.repository.SubscriptionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceImplTest {
    @Mock
    private SubscriptionRepository subscriptionRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private PolicyRepository policyRepository;
    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    @Test
    void createSubscription_LifePolicy_MoreThanTwoBeneficiaries_ThrowsException() {
        SubscriptionRequestDTO dto = new SubscriptionRequestDTO();
        dto.setIdClient(1L);
        dto.setIdPolicy(1L);
        dto.setBeneficiaries(Arrays.asList(new BeneficiaryDTO(), new BeneficiaryDTO(), new BeneficiaryDTO()));

        Policy policy = new Policy();
        PolicyType type = new PolicyType();
        type.setName("Vida");
        policy.setPolicyType(type);

        when(clientRepository.findById(any())).thenReturn(Optional.of(new Client()));
        when(policyRepository.findById(any())).thenReturn(Optional.of(policy));

        assertThrows(BusinessRuleException.class, () -> subscriptionService.createSubscription(dto));
    }

    @Test
    void validateHealtPolicy_MixingParentsAndSppouse_ThrowsException() {
        SubscriptionRequestDTO dto = new SubscriptionRequestDTO();
        BeneficiaryDTO b1 = new BeneficiaryDTO();
        b1.setRelationshipType("PADRE");
        BeneficiaryDTO b2 = new BeneficiaryDTO();
        b2.setRelationshipType("ESPOSA");
        dto.setBeneficiaries(Arrays.asList(b1, b2));

        Policy policy = new Policy();
        PolicyType type = new PolicyType();
        type.setName("Salud");
        policy.setPolicyType(type);

        when(clientRepository.findById(any())).thenReturn(Optional.of(new Client()));
        when(policyRepository.findById(any())).thenReturn(Optional.of(policy));

        assertThrows(BusinessRuleException.class, () -> subscriptionService.createSubscription(dto));
    }
}
