package com.rozsa.dao;

import com.rozsa.dao.persistence.DatabaseConnection;
import com.rozsa.model.Configuration;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import static com.rozsa.dao.Collection.CONFIGURATION;

@Repository
public class ConfigurationDao extends ProjectDependentDao<Configuration> {
    private static final String projectIdKey = "projectId";

    public ConfigurationDao(DatabaseConnection db) {
        super(db, Configuration.class, CONFIGURATION.getName(), projectIdKey);
    }

    @Override
    protected Object parseProjectId(ObjectId projectId) {
        return projectId;
    }
}
