package com.xdisx.customer.app.mock;

import com.xdisx.customer.app.repository.db.dto.CustomerPageDto;
import java.math.BigInteger;
import java.time.LocalDateTime;

import static com.xdisx.customer.app.mock.CustomerMock.*;

public class CustomerPageDtoMock implements CustomerPageDto {
  @Override
  public BigInteger getId() {
    return BigInteger.ONE;
  }

  @Override
  public LocalDateTime getCreated() {
    return LocalDateTime.now();
  }

  @Override
  public LocalDateTime getModified() {
    return LocalDateTime.now();
  }

  @Override
  public String getFirstName() {
    return FIRST_NAME;
  }

  @Override
  public String getLastName() {
    return LAST_NAME;
  }

  @Override
  public String getEmail() {
    return EMAIL;
  }

  @Override
  public String getPhoneNumber() {
    return PHONE_NUMBER;
  }

  @Override
  public String getAddress() {
    return ADDRESS;
  }
}
