package com.rozsa.service.exporter.impl;

import com.rozsa.service.exporter.ConfigurationExporterService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@ConditionalOnProperty(value="exporter.enabled", havingValue = "false")
public class MockConfigurationExporterSQS implements ConfigurationExporterService {
    @Override
    public void requestConfigurationExport(ObjectId configId) {
        log.error("Configuration exporter is disabled! Can't process {}", configId);
    }
}
