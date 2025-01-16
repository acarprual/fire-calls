Feature: Obtain fire calls

  Scenario: With a correct radius
    When I send a POST request to "/api/fire-calls" with type med and x 47.59815 and y -122.33454 and r 50
    Then the response status code should be 200
    And the response should be:
    """
    {
      "Multiple Medic Resp 14 Per":1,
      "Medic Response":49,
      "Medic Response- Overdose":1,
      "Medic Response, 6 per Rule":3,
      "Medic Response- 6 per Rule":2,
      "Medic Response, 7 per Rule":1
     }
    """
