package com.xdisx.customer.app.repository.db.dto;

import com.xdisx.customer.app.repository.db.entity.CustomerType;

import java.math.BigInteger;
import java.time.LocalDateTime;

public interface CustomerPageDto {
  BigInteger getId();

  LocalDateTime getCreated();

  LocalDateTime getModified();

  CustomerType getCustomerType();
}
