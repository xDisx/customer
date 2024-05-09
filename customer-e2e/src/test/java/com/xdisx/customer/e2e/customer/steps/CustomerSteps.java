package com.xdisx.customer.e2e.customer.steps;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.OK;

import com.xdisx.customer.api.dto.request.CustomerPageRequestDto;
import com.xdisx.customer.api.dto.response.CustomerResponseDto;
import com.xdisx.customer.e2e.CucumberBootstrap;
import com.xdisx.customer.e2e.common.utils.AssertionsUtils;
import com.xdisx.customer.e2e.customer.rest.CustomerController;
import com.xdisx.customer.e2e.customer.steps.context.GetCustomerContext;
import com.xdisx.customer.e2e.customer.steps.context.GetCustomersContext;
import com.xdisx.customer.e2e.customer.steps.service.RequestBuilderServiceCustomer;
import feign.FeignException;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerSteps extends CucumberBootstrap {
  private final CustomerController customerController;
  private final RequestBuilderServiceCustomer requestBuilder;
  private GetCustomersContext getCustomersContext;
  private GetCustomerContext getCustomerContext;

  @Before
  public void setup() {
    customerCreationContext.reset();
  }

  @When("I create a new customer")
  public void iCreateANewCustomer() {
    var createCustomerRequest = requestBuilder.buildCustomerCreateRequest();

    try {
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

  @When("I request the first page of customers")
  public void iRequestTheFirstPageOfCustomers() {
    var getCustomersRequest = CustomerPageRequestDto.builder().build();
    getCustomersContext = new GetCustomersContext();
    try {
      getCustomersContext.setCustomerPageResponseDto(
          customerController.getCustomers(getCustomersRequest));
      getCustomersContext.setStatus(OK.value());
    } catch (FeignException e) {
      getCustomersContext.setException(e);
    }
  }

  @Then("I receive a page of customers")
  public void iReceiveAPageOfCustomers() {
    AssertionsUtils.assertAPISuccess(getCustomersContext);
    var response = getCustomersContext.getCustomerPageResponseDto();
    assertNotNull(response);
  }

  @Given("a customer exists in the system")
  public void aCustomerExistsInTheSystem() {
    customerCreationContext.setResponse(fetchOrCreateACustomer());
  }

  private CustomerResponseDto fetchOrCreateACustomer() {
    var customersRequest = CustomerPageRequestDto.builder().build();
    var customers = customerController.getCustomers(customersRequest);
    return customers.getCustomers().isEmpty() ? createCustomer() : customers.getCustomers().get(0);
  }

  private CustomerResponseDto createCustomer() {
    var createCustomerRequest = requestBuilder.buildCustomerCreateRequest();
    return customerController.createCustomer(createCustomerRequest);
  }

  @When("I request the details of the customer")
  public void iRequestTheDetailsOfTheCustomer() {
    getCustomerContext = new GetCustomerContext();
    try {
      getCustomerContext.setCustomerResponseDto(
          customerController.getCustomer(customerCreationContext.getResponse().getID()));
      getCustomerContext.setStatus(OK.value());
    } catch (FeignException e) {
      getCustomerContext.setException(e);
    }
  }

  @Then("I receive the customer")
  public void iReceiveTheCustomer() {
    AssertionsUtils.assertAPISuccess(getCustomerContext);
    var customer = getCustomerContext.getCustomerResponseDto();
    assertNotNull(customer);
  }
}
