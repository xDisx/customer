package com.xdisx.customer.app.repository.db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customers")
@Getter
@Setter
public class CustomerEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_gen")
  @SequenceGenerator(name = "customer_id_gen", sequenceName = "customer_id_seq", allocationSize = 1)
  private BigInteger id;

  @Column(name = "first_name")
  @NotNull
  private String firstName;

  @Column(name = "last_name")
  @NotNull
  private String lastName;

  @Column(name = "email")
  @NotNull
  private String email;

  @Column(name = "phone_number")
  @NotNull
  private String phoneNumber;

  @Column(name = "address")
  @NotNull
  private String address;
}
