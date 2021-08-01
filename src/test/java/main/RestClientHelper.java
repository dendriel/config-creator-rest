package main;

import com.rozsa.controller.dto.BaseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

@Component
public class RestClientHelper {
    private static final Logger log = LoggerFactory.getLogger(RestClientHelper.class);

    @Autowired
    private TestRestTemplate restTemplate;

    private final Map<String, String> requestHeaders;

    public RestClientHelper() {
        requestHeaders = new HashMap<>();
    }

    public void clear() {
        clearRequestHeaders();
    }

    public void clearRequestHeaders() {
        requestHeaders.clear();
    }

    public void addRequestHeader(String key, String value) {
        requestHeaders.put(key, value);
    }

    public void addRequestHeaders(Map<String, String> headers) {
        requestHeaders.putAll(headers);
    }

    public <T> ResponseEntity<T> create(String resource, String data, Class<T> clazz) {
        log.info("Create {} with data: {}", resource, data);

        HttpHeaders headers = new HttpHeaders();
        requestHeaders.forEach(headers::add);

        BaseDto dto = new BaseDto(data);
        HttpEntity<BaseDto> request = new HttpEntity<>(dto, headers);

        return restTemplate.postForEntity(
                String.format("/%s", resource),
                request,
                clazz
        );
    }

    public <T> ResponseEntity<T> get(String resource, String resourceId, Class<T> clazz) {
        log.info("Find {} with id {}", resource, resourceId);

        assertNotNull("Test error: can't get a resource without an id", resourceId);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        requestHeaders.forEach(headers::add);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        return restTemplate.exchange(
                String.format("/%s/%s", resource, resourceId),
                HttpMethod.GET,
                request,
                clazz
        );
    }

    public <T> ResponseEntity<T> getAll(String resource, Integer offset, Integer range, Class<T> clazz) {
        log.info("Find all {} with offset {} and range {}", resource, offset, range);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        requestHeaders.forEach(headers::add);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        return restTemplate.exchange(
                String.format("/%s/all?offset=%d&limit=%d", resource, offset, range),
                HttpMethod.GET,
                request,
                clazz
        );
    }

    public ResponseEntity<Void> put(String resource, String resourceId, String data) {
        return put(resource, resourceId, data, Void.class);
    }

    public <T> ResponseEntity<T> put(String resource, String resourceId, String data, Class<T> clazz) {
        log.info("Update {} with data {} and Id {}", resource, data, resourceId);

        assertNotNull("Test error: can't update a resource without creating an id", resourceId);

        HttpHeaders headers = new HttpHeaders();
        requestHeaders.forEach(headers::add);

        BaseDto dto = new BaseDto(resourceId, data);
        HttpEntity<BaseDto> request = new HttpEntity<>(dto, headers);

        return restTemplate.exchange(
                String.format("/%s", resource),
                HttpMethod.PUT,
                request,
                clazz
        );
    }

    public ResponseEntity<Void> delete(String resource, String resourceId) {
        log.info("Delete {} with id: {}", resource, resourceId);

        assertNotNull("Test error: can't delete a resource without an id", resourceId);

        HttpHeaders headers = new HttpHeaders();
        requestHeaders.forEach(headers::add);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        return restTemplate.exchange(
                String.format("/%s/%s", resource, resourceId),
                HttpMethod.DELETE,
                request,
                Void.class
        );
    }

    public ResponseEntity<Integer> count(String resource) {
        log.info("Count {}", resource);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        requestHeaders.forEach(headers::add);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        return restTemplate.exchange(
                String.format("/%s/count", resource),
                HttpMethod.GET,
                request,
                Integer.class
        );
    }
}
