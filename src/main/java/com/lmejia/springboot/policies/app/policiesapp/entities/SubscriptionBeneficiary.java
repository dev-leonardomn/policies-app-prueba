package com.lmejia.springboot.policies.app.policiesapp.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscription_beneficiary")
public class SubscriptionBeneficiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_subscription")
    private Subscription subscription;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_beneficiary")
    private Beneficiary beneficiary;

    @Column(name = "relationship_type")
    private String relationshipType;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;
}
