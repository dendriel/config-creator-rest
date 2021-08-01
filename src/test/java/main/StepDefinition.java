package main;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.rozsa.controller.dto.BaseDto;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.micrometer.core.instrument.util.StringUtils;
import main.containers.MongoDBServerContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
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

    private Map<String, String> requestHeaders;

    private String resourceId;

    public StepDefinition() {
        requestHeaders = new HashMap<>();
    }

    @Given("clear database")
    public void clearDatabase() {
        log.info("Clear database");
        MongoDBServerContainer.clearDatabase();
    }

    @And("clear test environment")
    public void clearTestEnvironment() {
        log.info("Clear test environment");
        clearDatabase();

        requestHeaders = new HashMap<>();
        resourceId = null;
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

    @When("create many templates with data")
    public void createTemplate(List<String> data) {
        data.forEach(this::createTemplate);
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

    @When("delete last created template status code is {int}")
    public void deleteTemplate(int expectedStatusCode) {
        log.info("delete last created template with id: {}", resourceId);

        assertNotNull("Test error: can't delete a resource without creating it first", resourceId);

        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.forEach(headers::add);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                String.format("/template/%s", resourceId),
                HttpMethod.DELETE,
                request,
                Void.class
        );

        assertEquals(expectedStatusCode, response.getStatusCode().value());

        log.info("Deleted resource with ID: {}", resourceId);
    }

    @When("update last created template with data {string} status code is {int}")
    public void updateTemplate(String data, int expectedStatusCode) {
        log.info("Update template with data {} and Id {}", data, resourceId);

        assertNotNull("Test error: can't update a resource without creating it first", resourceId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.forEach(headers::add);

        BaseDto dto = new BaseDto(resourceId, data);
        HttpEntity<BaseDto> request = new HttpEntity<>(dto, headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                "/template",
                HttpMethod.PUT,
                request,
                Void.class
        );

        assertEquals(expectedStatusCode, response.getStatusCode().value());
    }

    @Then("get last created template status code is {int} and has data {string}")
    public void getLastTemplateCreated(int expectedStatusCode, String data) {
        log.info("Find template with data: {}", data);

        assertNotNull("Test error: can't find a resource without creating it first", resourceId);

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

        assertEquals(expectedStatusCode, response.getStatusCode().value());

        BaseDto template = response.getBody();
        if (StringUtils.isNotEmpty(data)) {
            assertNotNull(template);
            assertEquals(resourceId, template.getId());
            assertEquals(data, template.getData());
        }
        else {
            assertNull(template);
        }
    }

    @Then("all templates in offset {int} limit {int} should be")
    public void findAllByOffsetAndRange(Integer offset, Integer range, List<String> data) {
        log.info("Find all templates with offset {} and range {}", offset, range);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        requestHeaders.forEach(headers::add);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<BaseDto[]> response = restTemplate.exchange(
                String.format("/template/all?offset=%d&limit=%d", offset, range),
                HttpMethod.GET,
                request,
                BaseDto[].class
        );

        BaseDto[] result = response.getBody();

        assertNotNull(result);
        assertEquals(data.size(), result.length);

        for (int i = 0; i < data.size(); i++) {
            BaseDto dto = result[i];
            String expected = data.get(i);
            String received = dto.getData();

            assertEquals(expected, received);
            assertNotNull(dto.getId());
        }
    }

    @Then("templates count should be {int}")
    public void countAllTemplates(Integer count) {
        log.info("Test expected template count: {}", count);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        requestHeaders.forEach(headers::add);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<Integer> response = restTemplate.exchange(
                "/template/count",
                HttpMethod.GET,
                request,
                Integer.class
        );

        Integer responseCount = response.getBody();

        assertNotNull(count);
        assertEquals(count, responseCount);
    }
}
