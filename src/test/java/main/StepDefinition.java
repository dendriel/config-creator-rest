package main;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import main.containers.MongoDBServerContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;


public class StepDefinition extends IntegrationTest {
    private static final Logger log = LoggerFactory.getLogger(StepDefinition.class);

    @Autowired
    private RestClientHelper restClient;

    @Autowired
    private MockHelper mockHelper;


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
    }

    @Given("request is setup with headers")
    public void setupRequest(Map<String, String> headers) {
        restClient.addRequestHeaders(headers);
    }

    @Given("authentication service is setup and accepts token {string}")
    public void setupAuthenticationService(String token) {
        mockHelper.setupAuthenticationService(token);
    }
}
