package com.rozsa.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "exporter")
public class ConfigurationExporterProperties {

    @Value("${exporter.queue.name}")
    private String queueName;

    @Value("${exporter.queue.url}")
    private String queueUrl;

    @Value("${exporter.queue.region}")
    private String signingRegion;
}
