package com.xdisx.customer.e2e.customer.steps;

import com.xdisx.customer.e2e.CucumberBootstrap;
import com.xdisx.customer.e2e.common.utils.AssertionsUtils;
import com.xdisx.customer.e2e.customer.rest.CustomerController;
import com.xdisx.customer.e2e.customer.steps.service.RequestBuilderServiceCustomer;
import feign.FeignException;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
public class CustomerSteps extends CucumberBootstrap {
    private final CustomerController customerController;
    private final RequestBuilderServiceCustomer requestBuilder;

    @Before
    public void setup() {
        customerCreationContext.reset();
    }

    @When("I create a new customer")
    public void iCreateANewCustomer() {
        var createCustomerRequest = requestBuilder.buildCustomerCreateRequest();

        try{
            customerCreationContext.setResponse(customerController.createCustomer(createCustomerRequest));
            customerCreationContext.setStatus(OK.value());
        } catch (FeignException e) {
            customerCreationContext.setStatus(e.status());
            customerCreationContext.setException(e);
        }
    }

    @Then("I receive the created customer")
    public void iReceiveTheCreatedCustomer() {
        AssertionsUtils.assertAPISuccess(customerCreationContext);

        var customerResponse = customerCreationContext.getResponse();
        assertNotNull(customerResponse);
    }
}
