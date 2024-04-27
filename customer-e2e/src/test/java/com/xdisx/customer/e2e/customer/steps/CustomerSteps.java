package com.xdisx.customer.e2e.customer.steps;

import com.xdisx.customer.e2e.CucumberBootstrap;
import com.xdisx.customer.e2e.customer.rest.CustomerController;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerSteps extends CucumberBootstrap {
    private final CustomerController customerController;

    @When("I call the test api")
    public void iCallTheTestApi() {
        String rez = customerController.salut(22);
        System.out.println("am primit rezultat " + rez);
    }

    @Then("I receive the doubled number")
    public void iReceiveTheDoubledNumber() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("am fost pe aici");
    }
}
