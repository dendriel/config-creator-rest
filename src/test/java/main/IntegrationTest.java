package main;

import com.rozsa.Application;
import io.cucumber.spring.CucumberContextConfiguration;
import main.containers.MongoDBServerContainer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("test")
@CucumberContextConfiguration
@SpringBootTest(classes = { Application.class, MongoDBServerContainer.class },
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
public class IntegrationTest {
}
