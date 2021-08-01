Feature: Test Template resource API
  Template API should allow CRUD operations over template resources.

  Background:
    Given clear test environment
    And request is setup with headers
    | Authorization | Bearer token eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZW5kcmllbCIsImV4cCI6MTYyNjI2NjUyMywiaWF0IjoxNjI2MjYyOTIzfQ.oC321gaDxPHyu09Fw-a8ZeiKeMYDL4AOgjyZgwF2eZA |
    And authentication service is setup and accepts token "Bearer token eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZW5kcmllbCIsImV4cCI6MTYyNjI2NjUyMywiaWF0IjoxNjI2MjYyOTIzfQ.oC321gaDxPHyu09Fw-a8ZeiKeMYDL4AOgjyZgwF2eZA"

  Scenario: Create and get template by id
    When create a template with data '{"name": "dummy"}'
    Then get last created template status code is 200 and has data '{"name": "dummy"}'

  Scenario: Update template data
    Given create a template with data '{"name": "dummy"}'
    When update last created template with data '{"name": "new-dummy", "age": 123}' status code is 204
    Then get last created template status code is 200 and has data '{"name": "new-dummy", "age": 123}'

  Scenario: Update a non-existent template
    Given create a template with data '{"name": "dummy"}'
    And delete last created template status code is 204
    Then update last created template with data '{"name": "new-dummy", "age": 123}' status code is 404

  Scenario: Count all templates
    When create many templates with data
      | {"name": "dummy01"} |
      | {"name": "dummy02"} |
      | {"name": "dummy03"} |
      | {"name": "dummy04"} |
      | {"name": "dummy05"} |
    Then templates count should be 5

  Scenario: Get all templates with offset and limit
    When create many templates with data
      | {"name": "dummy01"} |
      | {"name": "dummy02"} |
      | {"name": "dummy03"} |
      | {"name": "dummy04"} |
      | {"name": "dummy05"} |
    Then get all templates in offset 2 limit 2 should be
      | {"name": "dummy03"} |
      | {"name": "dummy04"} |

  Scenario: Delete a template and it can't be retrieved anymore
    Given create a template with data '{"name": "dummy"}'
    And delete last created template status code is 204
    Then get last created template status code is 404 and has data ""

  Scenario: Delete an non-existent template
    Given create a template with data '{"name": "dummy"}'
    And delete last created template status code is 204
    Then delete last created template status code is 404

