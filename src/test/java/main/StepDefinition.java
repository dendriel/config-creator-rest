package main;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.rozsa.controller.dto.BaseDto;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class StepDefinition extends IntegrationTest {

    private static final Logger log = LoggerFactory.getLogger(StepDefinition.class);

    @Autowired
    private TestRestTemplate restTemplate;

    private final Map<String, String> requestHeaders;

    private String resourceId;

    public StepDefinition() {
        requestHeaders = new HashMap<>();
    }

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

    @When("create a template with data {string}")
    public void createTemplate(String data) {
        log.info("Create template with data: {}", data);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.forEach(headers::add);

        BaseDto dto = new BaseDto(data);
        HttpEntity<BaseDto> request = new HttpEntity<>(dto, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "/template",
                request,
                String.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        resourceId = response.getBody();
        log.info("Resource created with ID: {}", resourceId);
    }

    @Then("last created template has data {string}")
    public void findLastTemplateCreated(String data) {
        log.info("Find template with data: " + data);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        requestHeaders.forEach(headers::add);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<BaseDto> response = restTemplate.exchange(
                String.format("/template/%s", resourceId),
                HttpMethod.GET,
                request,
                BaseDto.class
        );

        BaseDto template = response.getBody();

        assertNotNull(template);
        assertEquals(resourceId, template.getId());
        assertEquals(data, template.getData());
    }
}
