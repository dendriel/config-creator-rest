package main;

import com.github.tomakehurst.wiremock.client.WireMock;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class StepDefinition extends IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> response;

    private Map<String, String> requestHeaders;

    public StepDefinition() {
        requestHeaders = new HashMap<>();
    }

    String data = "";

    @Given("request is setup with headers")
    public void setupRequest(Map<String, String> headers) {
        requestHeaders.putAll(headers);
    }

    @Given("authentication service is setup and accepts token {string}")
    public void setupAuthenticationService(String token) {
        stubFor(WireMock.get("/validate")
                .withHeader(HttpHeaders.AUTHORIZATION, matching(token))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withFixedDelay(1)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                        .withBody("{\"authenticated\": true, \"username\": \"dendriel\", \"userId\": 123, \"authorities\": []}")
                )
        );
    }

    @When("create template API is setup and will return id {string}")
    public void setupCreateTemplateApi(String id) {
        stubFor(WireMock.post("/template")
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withFixedDelay(1)
                        .withBody(id)
                )
        );
    }

    @When("creating a template with data {string}")
    public void createTemplate(String data) {
        System.out.println("Create template with data: " + data);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.forEach(headers::add);

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
