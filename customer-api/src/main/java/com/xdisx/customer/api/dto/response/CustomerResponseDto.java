package com.xdisx.customer.api.dto.response;

import com.xdisx.customer.api.dto.CustomerTypeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDto {
    private BigInteger ID;
    private CustomerTypeDto customerType;
}
