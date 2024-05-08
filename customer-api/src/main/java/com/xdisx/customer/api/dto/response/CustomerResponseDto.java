package com.xdisx.customer.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigInteger;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDto {
  private BigInteger ID;

  private String firstName;

  private String lastName;

  private String email;

  private String phoneNumber;

  private String address;

  @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
  private LocalDateTime created;
}
