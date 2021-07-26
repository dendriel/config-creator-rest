Feature: Test Template resource API
  Template API should allow CRUD operations over template resources.

  Scenario: Create a template
    When creating a template with data "{\"name\":\"dummy\"}"
    Then last created template has data "{\"name\":\"dummy\"}"
