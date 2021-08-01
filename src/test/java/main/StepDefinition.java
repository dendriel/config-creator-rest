package main;

import com.rozsa.controller.dto.BaseDto;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.micrometer.core.instrument.util.StringUtils;
import main.containers.MongoDBServerContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class StepDefinition extends IntegrationTest {
    private static final Logger log = LoggerFactory.getLogger(StepDefinition.class);

    private static final String templateResource = "template";

    private final RestClientHelper restClient;
    private final MockHelper mockHelper;

    private String resourceId;


    public StepDefinition(TestRestTemplate restTemplate) {
        restClient = new RestClientHelper(restTemplate);
        mockHelper = new MockHelper();
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

        restClient.clear();
        resourceId = null;
    }

    @Given("request is setup with headers")
    public void setupRequest(Map<String, String> headers) {
        restClient.addRequestHeaders(headers);
    }

    @Given("authentication service is setup and accepts token {string}")
    public void setupAuthenticationService(String token) {
        mockHelper.setupAuthenticationService(token);
    }

    @When("create many templates with data")
    public void createTemplate(List<String> data) {
        data.forEach(this::createTemplate);
    }

    @When("create a template with data {string}")
    public void createTemplate(String data) {
        ResponseEntity<String> response = restClient.create(templateResource, data, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        resourceId = response.getBody();
        log.info("Resource created with ID: {}", resourceId);
    }

    @When("delete last created template status code is {int}")
    public void deleteTemplate(int expectedStatusCode) {
        ResponseEntity<Void> response = restClient.delete(templateResource, resourceId);

        assertEquals(expectedStatusCode, response.getStatusCode().value());
        log.info("Deleted resource with ID: {}", resourceId);
    }

    @When("update last created template with data {string} status code is {int}")
    public void updateTemplate(String data, int expectedStatusCode) {
        ResponseEntity<Void> response = restClient.put(templateResource, resourceId, data);

        assertEquals(expectedStatusCode, response.getStatusCode().value());
        log.info("Updated resource with ID: {}", resourceId);
    }

    @Then("get last created template status code is {int} and has data {string}")
    public void getLastTemplateCreated(int expectedStatusCode, String data) {
        ResponseEntity<BaseDto> response = restClient.get(templateResource, resourceId, BaseDto.class);

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

    @Then("get all templates in offset {int} limit {int} should be")
    public void findAllByOffsetAndRange(Integer offset, Integer range, List<String> data) {
        ResponseEntity<BaseDto[]> response = restClient.getAll(templateResource, offset, range, BaseDto[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

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
        ResponseEntity<Integer> response = restClient.count(templateResource);

        Integer responseCount = response.getBody();

        assertNotNull(count);
        assertEquals(count, responseCount);
    }
}
