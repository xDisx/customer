package com.xdisx.customer.app.repository.db.dto;

import java.math.BigInteger;
import java.time.LocalDateTime;

public interface CustomerPageDto {
  BigInteger getId();

  LocalDateTime getCreated();

  LocalDateTime getModified();

  String getFirstName();

  String getLastName();

  String getEmail();

  String getPhoneNumber();

  String getAddress();
}
