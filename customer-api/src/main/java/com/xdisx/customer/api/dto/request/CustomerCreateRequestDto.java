package com.xdisx.customer.api.dto.request;

import com.xdisx.customer.api.dto.CustomerTypeDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCreateRequestDto {
    @NotNull(message = "Customer type must not be null!")
    private CustomerTypeDto customerType;
}
