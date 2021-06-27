package com.rozsa.dao;

import com.rozsa.dao.persistence.DatabaseConnection;
import com.rozsa.model.Resource;
import org.springframework.stereotype.Repository;

import static com.rozsa.dao.Collection.RESOURCE;

@Repository
public class ResourceDao extends AbstractDao<Resource> {
    public ResourceDao(DatabaseConnection db) {
        super(db, Resource.class, RESOURCE.getName());
    }
}
