package com.lmejia.springboot.policies.app.policiesapp.repository;

import com.lmejia.springboot.policies.app.policiesapp.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByClientId(Long clientId);
    long countByClientIdAndPolicyPolicyTypeName(Long clientId, String policyTypeName);
}
