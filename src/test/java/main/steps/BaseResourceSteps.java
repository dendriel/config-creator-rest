package main.steps;

import com.rozsa.controller.dto.BaseDto;
import io.micrometer.core.instrument.util.StringUtils;
import main.RestClientHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.*;

public class BaseResourceSteps {
    private static final Logger log = LoggerFactory.getLogger(BaseResourceSteps.class);

    private final String resource;

    private final RestClientHelper restClient;

    protected String resourceId;


    public BaseResourceSteps(String resource, RestClientHelper restClient) {
        this.resource = resource;
        this.restClient = restClient;
    }

    public void create(List<String> data) {
        data.forEach(this::create);
    }

    public void create(String data) {
        ResponseEntity<String> response = restClient.create(resource, data, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        resourceId = response.getBody();
        log.info("{} created with ID: {}", resource, resourceId);
    }

    public void update(String data, int expectedStatusCode) {
        ResponseEntity<Void> response = restClient.put(resource, resourceId, data);

        assertEquals(expectedStatusCode, response.getStatusCode().value());
        log.info("Updated {} with ID: {}", resource, resourceId);
    }

    public void getLastCreated(int expectedStatusCode, String data) {
        ResponseEntity<BaseDto> response = restClient.get(resource, resourceId, BaseDto.class);

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

    public void delete(int expectedStatusCode) {
        ResponseEntity<Void> response = restClient.delete(resource, resourceId);

        assertEquals(expectedStatusCode, response.getStatusCode().value());
        log.info("Deleted {} with ID: {}", resource, resourceId);
    }

    public void findAllByOffsetAndRange(Integer offset, Integer range, List<String> data) {
        ResponseEntity<BaseDto[]> response = restClient.getAll(resource, offset, range, BaseDto[].class);

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

    public void countAll(Integer count) {
        ResponseEntity<Integer> response = restClient.count(resource);

        Integer responseCount = response.getBody();

        assertNotNull(count);
        assertEquals(count, responseCount);
    }
}
