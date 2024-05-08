package com.xdisx.customer.api.dto.request;

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
  @NotNull private String firstName;

  @NotNull private String lastName;

  @NotNull private String email;

  @NotNull private String phoneNumber;

  @NotNull private String address;
}
