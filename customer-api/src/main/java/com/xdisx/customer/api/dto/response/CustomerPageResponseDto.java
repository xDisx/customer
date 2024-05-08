package com.xdisx.customer.api.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPageResponseDto {
    private int totalPages;
    private long totalElements;
    private List<CustomerResponseDto> contracts;
}
