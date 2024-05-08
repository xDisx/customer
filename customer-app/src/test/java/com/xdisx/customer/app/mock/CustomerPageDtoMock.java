package com.xdisx.customer.app.mock;

import com.xdisx.customer.app.repository.db.dto.CustomerPageDto;
import com.xdisx.customer.app.repository.db.entity.CustomerType;
import java.math.BigInteger;
import java.time.LocalDateTime;

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
    public CustomerType getCustomerType() {
        return CustomerType.PRIVATE;
    }
}
