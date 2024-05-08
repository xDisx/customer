Feature: Fetching customers from db

  Scenario: Get first page of customers
    When I request the first page of customers
    Then I receive a page of customers