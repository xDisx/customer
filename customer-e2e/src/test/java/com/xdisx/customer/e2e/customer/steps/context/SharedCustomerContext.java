package com.xdisx.customer.e2e.customer.steps.context;

import com.xdisx.customer.api.dto.response.CustomerResponseDto;
import com.xdisx.customer.e2e.common.context.ValidatedContext;
import feign.FeignException;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
@Data
public class SharedCustomerContext implements ValidatedContext {
    private int status;
    private FeignException exception;
    private CustomerResponseDto response;

    public void reset() {
        status = 0;
        exception = null;
        response = null;
    }
}
