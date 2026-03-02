package com.lmejia.springboot.policies.app.policiesapp.repository;

import com.lmejia.springboot.policies.app.policiesapp.entities.SubscriptionVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionVehicleRepository extends JpaRepository<SubscriptionVehicle, Long> {
    List<SubscriptionVehicle> findBySubscriptionId(Long subscriptionId);
}
