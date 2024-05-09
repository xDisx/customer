Feature: Get a customer

  Scenario: Fetching a customer
    Given a customer exists in the system
    When I request the details of the customer
    Then I receive the customer