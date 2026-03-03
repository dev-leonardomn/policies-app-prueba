package com.lmejia.springboot.policies.app.policiesapp.services.impl;

import com.lmejia.springboot.policies.app.policiesapp.dto.BeneficiaryDTO;
import com.lmejia.springboot.policies.app.policiesapp.dto.PageResponse;
import com.lmejia.springboot.policies.app.policiesapp.dto.SubscriptionRequestDTO;
import com.lmejia.springboot.policies.app.policiesapp.dto.SubscriptionResponseDTO;
import com.lmejia.springboot.policies.app.policiesapp.entities.*;
import com.lmejia.springboot.policies.app.policiesapp.exceptions.BusinessRuleException;
import com.lmejia.springboot.policies.app.policiesapp.exceptions.ResourceNotFoundException;
import com.lmejia.springboot.policies.app.policiesapp.repository.*;
import com.lmejia.springboot.policies.app.policiesapp.services.ISubscriptionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements ISubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionBeneficiaryRepository subBeneficiaryRepository;
    private final SubscriptionVehicleRepository subVehicleRepository;
    private final ClientRepository clientRepository;
    private final PolicyRepository policyRepository;
    private final BeneficiaryRepository beneficiaryRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    @Transactional
    public Subscription createSubscription(SubscriptionRequestDTO subscription) {
        Client client = clientRepository.findById(subscription.getIdClient()).orElseThrow(() -> new ResourceNotFoundException("Client not found"));
        Policy policy = policyRepository.findById(subscription.getIdPolicy())
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found"));

        validateBusinessRules(subscription, policy);

        BigDecimal totalSum = calculateTotal(subscription);

        Subscription sub = Subscription.builder()
                .client(client)
                .policy(policy)
                .status(subscription.getStatus())
                .totalPrice(totalSum)
                .build();

        Subscription saved = subscriptionRepository.save(sub);

        if (subscription.getBeneficiaries() != null && !subscription.getBeneficiaries().isEmpty()) {
            saveBeneficiaries(subscription.getBeneficiaries(), saved);
        }

        if (subscription.getVehicleIds() != null && !subscription.getVehicleIds().isEmpty()) {
            saveVehicles(subscription.getVehicleIds(), saved);
        }


        return saved;
    }

    @Override
    public PageResponse<Subscription> getSubscriptionsById(Long clientId, int page, int size, String sortBy, String sortDirection) {

        if (!clientRepository.existsById(clientId)) {
            throw new ResourceNotFoundException("Client not found");
        }

        Sort sort = sortDirection.equalsIgnoreCase("DESC")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Subscription> subPage = subscriptionRepository.findByClientId(clientId, pageable);

        return PageResponse.<Subscription>builder()
                .content(subPage.getContent())
                .page(subPage.getNumber())
                .size(subPage.getSize())
                .totalElements(subPage.getTotalElements())
                .totalPages(subPage.getTotalPages())
                .last(subPage.isLast())
                .build();
    }

    @Override
    public Optional<Subscription> getSubscriptionDetail(Long subscriptionId) {
        return subscriptionRepository.findById(subscriptionId);
    }

    @Override
    public List<SubscriptionBeneficiary> getBeneficiariesBySubscriptionId(Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId).orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));

        if (!"Salud".equalsIgnoreCase(subscription.getPolicy().getPolicyType().getName())) {
            throw new RuntimeException("Policy type not supported");
        }

        return subBeneficiaryRepository.findBySubscriptionId(subscriptionId);
    }

    private void saveBeneficiaries(List<BeneficiaryDTO> beneficiaries, Subscription sub) {
        for (BeneficiaryDTO bDto : beneficiaries) {
            Beneficiary b = beneficiaryRepository.findById(bDto.getBeneficiaryId()).orElseThrow();
            SubscriptionBeneficiary relation = SubscriptionBeneficiary.builder()
                    .subscription(sub)
                    .beneficiary(b)
                    .relationshipType(bDto.getRelationshipType())
                    .totalPrice(bDto.getPrice())
                    .build();
            subBeneficiaryRepository.save(relation);
        }
    }

    private void saveVehicles(List<Long> vehicleIds, Subscription sub) {
        for (Long vId : vehicleIds) {
            Vehicle v = vehicleRepository.findById(vId).orElseThrow();
            SubscriptionVehicle relation = SubscriptionVehicle.builder()
                    .subscription(sub)
                    .vehicle(v)
                    .build();
            subVehicleRepository.save(relation);
        }
    }

    private void validateBusinessRules(SubscriptionRequestDTO dto, Policy policy) {
        String type = policy.getPolicyType().getName();

        switch (type) {
            case "Vida":
                validateLifePolicy(dto);
                break;
            case "Salud":
                validateHealthPolicy(dto);
                break;
            case "Vehiculo":
                if (dto.getVehicleIds() == null || dto.getVehicleIds().isEmpty()) {
                    throw new BusinessRuleException("You must associate at least one vehicle.");
                }
                break;
        }
    }

    private void validateLifePolicy(SubscriptionRequestDTO dto) {
        long existingLifePolicies = subscriptionRepository
                .countByClientIdAndPolicyPolicyTypeName(dto.getIdClient(), "Vida");

        if (existingLifePolicies > 0) {
            throw new BusinessRuleException("Client already has an active life policy.");
        }

        if (dto.getBeneficiaries() != null && dto.getBeneficiaries().size() > 2) {
            throw new BusinessRuleException("A life policy supports up to 2 beneficiaries only.");
        }
    }

    private void validateHealthPolicy(SubscriptionRequestDTO dto) {
        if (dto.getBeneficiaries() == null || dto.getBeneficiaries().isEmpty()) {
            return;
        }

        List<String> relationships = dto.getBeneficiaries().stream()
                .map(b -> b.getRelationshipType().toUpperCase())
                .toList();

        boolean hasParents = relationships.contains("PADRE") || relationships.contains("MADRE");
        boolean hasSpouse = relationships.contains("ESPOSA") || relationships.contains("ESPOSO");
        boolean hasChildren = relationships.contains("HIJO") || relationships.contains("HIJA");

        if (hasParents && (hasSpouse || hasChildren)) {
            throw new BusinessRuleException("Health policy does not allow mixing parent coverage with spouse/children");
        }

        for (String rel : relationships) {
            if (!Arrays.asList("PADRE", "MADRE", "ESPOSA", "ESPOSO", "HIJO", "HIJA").contains(rel)) {
                throw new BusinessRuleException("Relationship '" + rel + "' is not permitted for this health policy.");
            }
        }
    }

    private BigDecimal calculateTotal(SubscriptionRequestDTO dto) {
        if (dto.getBeneficiaries() == null || dto.getBeneficiaries().isEmpty()) {
            return BigDecimal.ZERO;
        }
        return dto.getBeneficiaries().stream()
                .map(BeneficiaryDTO::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
