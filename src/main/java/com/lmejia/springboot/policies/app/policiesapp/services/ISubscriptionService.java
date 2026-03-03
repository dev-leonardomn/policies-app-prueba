package com.lmejia.springboot.policies.app.policiesapp.services;

import com.lmejia.springboot.policies.app.policiesapp.dto.PageResponse;
import com.lmejia.springboot.policies.app.policiesapp.dto.SubscriptionRequestDTO;
import com.lmejia.springboot.policies.app.policiesapp.dto.SubscriptionResponseDTO;
import com.lmejia.springboot.policies.app.policiesapp.entities.Subscription;
import com.lmejia.springboot.policies.app.policiesapp.entities.SubscriptionBeneficiary;

import java.util.List;
import java.util.Optional;

public interface ISubscriptionService {
    Subscription createSubscription(SubscriptionRequestDTO subscription);
    PageResponse<Subscription> getSubscriptionsById(Long clientId, int page, int size, String sortBy, String sortDirection);
    Optional<Subscription> getSubscriptionDetail(Long subscriptionId);
    List<SubscriptionBeneficiary> getBeneficiariesBySubscriptionId(Long subscriptionId);
}
