package com.rozsa.service.storage;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

public class StorageFeignClientConfiguration {
    private final String authKey;

    public StorageFeignClientConfiguration(
            @Value("${service.auth.key}") String authKey
    ) {
        this.authKey = "Bearer " + authKey;
    }

    @Bean
    public RequestInterceptor authFeign() {
        return template -> template.header(HttpHeaders.AUTHORIZATION, authKey);
    }
}
