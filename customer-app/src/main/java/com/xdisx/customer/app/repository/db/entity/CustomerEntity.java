package com.xdisx.customer.app.repository.db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Entity
@Table(name = "customers")
@Getter
@Setter
public class CustomerEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_gen")
    @SequenceGenerator(name = "customer_id_gen", sequenceName = "customer_id_seq", allocationSize = 1)
    private BigInteger id;

    @NotNull
    @Column(name = "customer_type")
    @Enumerated(EnumType.STRING)
    private CustomerType customerType;
}
