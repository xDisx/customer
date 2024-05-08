package com.xdisx.customer.e2e.customer.steps.context;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

import com.xdisx.customer.api.dto.response.CustomerPageResponseDto;
import com.xdisx.customer.e2e.common.context.ValidatedContext;
import feign.FeignException;
import lombok.Data;
import org.springframework.context.annotation.Scope;

@Scope(SCOPE_CUCUMBER_GLUE)
@Data
public class GetCustomersContext implements ValidatedContext {
  private int status;
  private FeignException exception;
  private CustomerPageResponseDto customerPageResponseDto;
}
