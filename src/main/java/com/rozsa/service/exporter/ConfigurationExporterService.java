package com.rozsa.service.exporter;

import org.bson.types.ObjectId;

public interface ConfigurationExporterService {
    /**
     * Send a request to export the target configuration.
     * @param configId id from configuration to be exported.
     */
    void requestConfigurationExport(ObjectId configId);
}
