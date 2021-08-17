package com.rozsa.service.exporter.impl;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.rozsa.configuration.ConfigurationExporterProperties;
import com.rozsa.service.exporter.ConfigurationExporterService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConfigurationExporterSQS implements ConfigurationExporterService {
    private final AmazonSQS sqs;

    private final String queueUrl;

    public ConfigurationExporterSQS(ConfigurationExporterProperties exporterProperties) {
        sqs = buildSqsClient(exporterProperties);
        queueUrl = sqs.getQueueUrl(exporterProperties.getQueueName()).getQueueUrl();
    }

    private AmazonSQS buildSqsClient(ConfigurationExporterProperties exporterProperties) {
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(
                exporterProperties.getQueueUrl(), exporterProperties.getSigningRegion());

        AmazonSQSClientBuilder builder = AmazonSQSClientBuilder.standard();
        builder.setEndpointConfiguration(endpointConfiguration);

        return builder.build();
    }

    @Override
    public void requestConfigurationExport(ObjectId configId) {
        ExportRequest exportRequest = new ExportRequest(configId.toString());

        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(exportRequest.encode());

        try {
            sqs.sendMessage(send_msg_request);
        } catch (Exception e) {
            log.error("Failed to enqueue export request for configuration {}", configId, e);
        }
    }

    @AllArgsConstructor
    private static class ExportRequest {
        private final String configId;

        public String encode() {
            return String.format("{\"id\":\"%s\"}", configId);
        }
    }
}
