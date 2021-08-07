package com.rozsa.business.impl;

import com.rozsa.business.ConfigurationBusiness;
import com.rozsa.dao.ConfigurationDao;
import com.rozsa.model.Configuration;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.rozsa.model.Configuration.State.READY;


@Component
public class ConfigurationBusinessImpl extends ProjectDependentBusinessImpl<Configuration, ConfigurationDao> implements ConfigurationBusiness {
    public ConfigurationBusinessImpl(ConfigurationDao dao) {
        super(dao);
    }

    @Override
    public void export(ObjectId projectId, ObjectId requesterId) {
        Date requestedAt = new Date();
        Configuration configuration = new Configuration(null, projectId, requesterId, null, requestedAt, null, Configuration.State.REQUESTED);

        Configuration saved = dao.save(configuration);

        System.out.println(saved);
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
}
