package com.rozsa.dao;

import com.rozsa.dao.persistence.DatabaseConnection;
import com.rozsa.model.Resource;
import org.springframework.stereotype.Repository;

import static com.rozsa.dao.Collection.RESOURCE;

@Repository
public class ResourceDao extends ProjectDependentDao<Resource> {
    private static final String projectIdKey = "data.projectId";

    public ResourceDao(DatabaseConnection db) {
        super(db, Resource.class, RESOURCE.getName(), projectIdKey);
    }
}
