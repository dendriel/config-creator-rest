Feature: Test Template resource API
  Template API should allow CRUD operations over template resources.

  Background:
    Given request is setup with headers
    | Authorization | Bearer token eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZW5kcmllbCIsImV4cCI6MTYyNjI2NjUyMywiaWF0IjoxNjI2MjYyOTIzfQ.oC321gaDxPHyu09Fw-a8ZeiKeMYDL4AOgjyZgwF2eZA |

  Scenario: Create a template
    Given authentication service is setup and accepts token "Bearer token eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZW5kcmllbCIsImV4cCI6MTYyNjI2NjUyMywiaWF0IjoxNjI2MjYyOTIzfQ.oC321gaDxPHyu09Fw-a8ZeiKeMYDL4AOgjyZgwF2eZA"
    Given create template API is setup and will return id "abcdefg"
    When creating a template with data "{\"name\":\"dummy\"}"
    Then last created template has data "{\"name\":\"dummy\"}"
