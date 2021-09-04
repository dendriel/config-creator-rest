package com.rozsa.business.impl;

import com.rozsa.business.ConfigurationBusiness;
import com.rozsa.controller.exception.UnprocessableEntityException;
import com.rozsa.dao.ConfigurationDao;
import com.rozsa.model.Configuration;
import com.rozsa.service.exporter.ConfigurationExporterService;
import com.rozsa.service.storage.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.Charset;
import java.util.Date;

import static com.rozsa.model.Configuration.State.FAILED;
import static com.rozsa.model.Configuration.State.READY;


@Slf4j
@Component
public class ConfigurationBusinessImpl extends ProjectDependentBusinessImpl<Configuration, ConfigurationDao> implements ConfigurationBusiness {

    private final ConfigurationExporterService exporterService;
    private final StorageService storageService;

    public ConfigurationBusinessImpl(ConfigurationDao dao, ConfigurationExporterService exporterService, StorageService storageService) {
        super(dao);
        this.exporterService = exporterService;
        this.storageService = storageService;
    }

    @Override
    public void export(ObjectId projectId, ObjectId requesterId) {
        Date requestedAt = new Date();
        Configuration configuration = new Configuration(null, projectId, requesterId, null, requestedAt, null, Configuration.State.REQUESTED);

        Configuration saved = dao.save(configuration);

        exporterService.requestConfigurationExport(saved.getId());
    }

    @Override
    public boolean retryExport(ObjectId id) {
        Configuration saved = find(id);
        if (saved == null) {
            return false;
        }

        if (!saved.getState().equals(FAILED)) {
            log.error("Can't retry export configuration {} because it may be still being processed.", saved.getResourceId());
            throw new UnprocessableEntityException();
        }

        Date requestedAt = new Date();
        saved.setState(Configuration.State.REQUESTED);
        saved.setRequestedAt(requestedAt);

        dao.update(saved);
        exporterService.requestConfigurationExport(saved.getId());

        return true;
    }

    @Override
    public Configuration update(Configuration configuration) {
        Configuration saved = find(configuration.getId());
        if (saved == null) {
            return null;
        }

        if (configuration.getState().equals(READY) && !saved.getState().equals(READY)) {
            saved.setCreatedAt(new Date());
        }

        saved.setState(configuration.getState());
        saved.setResourceId(configuration.getResourceId());

        return super.update(saved);
    }

    @Override
    public boolean remove(ObjectId id) {
        Configuration saved = find(id);
        if (saved == null) {
            return false;
        }

        if (saved.getState().equals(FAILED)) {
            log.info("Failed export request {} was removed from database.", saved.getResourceId());
            return super.remove(id);
        }

        try {
            /* A better way to handle this would be to generate a 'deletion' event after removing the configuration from
             * database. Then storage-service would listen to this event and do everything needed. The user don't care nor
             * want to wait for the 'real' resource removal.
             */
            storageService.deleteResource(saved.getResourceId());
        } catch (Exception ex) {
            log.error("Failed to request resource {} removal from storage.", saved.getResourceId(), ex);
            return false;
        }

        log.info("Resource {} was removed.", saved.getResourceId());

        return super.remove(id);
    }
}
