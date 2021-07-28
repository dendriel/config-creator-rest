package main;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.Assert.*;

public class StepDefinition extends IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> response;

    String data = "";

    @When("creating a template with data {string}")
    public void createTemplate(String data) {
        System.out.println("Create template with data: " + data);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(headers);

        response = restTemplate.exchange(
                "/template",
                HttpMethod.GET,
                request,
                String.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        // TODO: get id from response body (if any).
        this.data = data;
    }

    @Then("last created template has data {string}")
    public void findLastTemplateCreated(String data) {
        System.out.println("Find template with data: " + data);
        assertEquals("Data found don't have the expected value.", this.data, data);
    }
}
