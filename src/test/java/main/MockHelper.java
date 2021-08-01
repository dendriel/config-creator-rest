package main;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class MockHelper {
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
}
