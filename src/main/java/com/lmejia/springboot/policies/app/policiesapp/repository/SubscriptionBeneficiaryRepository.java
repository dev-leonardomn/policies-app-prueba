package com.lmejia.springboot.policies.app.policiesapp.repository;

import com.lmejia.springboot.policies.app.policiesapp.entities.SubscriptionBeneficiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionBeneficiaryRepository extends JpaRepository<SubscriptionBeneficiary, Long> {
    List<SubscriptionBeneficiary> findBySubscriptionId(Long subscriptionId);
}
