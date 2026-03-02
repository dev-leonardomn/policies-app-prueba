package com.lmejia.springboot.policies.app.policiesapp.repository;

import com.lmejia.springboot.policies.app.policiesapp.entities.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
}
