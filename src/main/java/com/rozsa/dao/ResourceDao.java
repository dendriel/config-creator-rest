package com.rozsa.dao;

import com.rozsa.dao.persistence.DatabaseConnection;
import com.rozsa.model.Resource;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.rozsa.dao.Collection.RESOURCE;

@Repository
public class ResourceDao extends AbstractDao<Resource> {

    private static final String projectIdKey = "data.projectId";

    public ResourceDao(DatabaseConnection db) {
        super(db, Resource.class, RESOURCE.getName());
    }

    public List<Resource> findAllByProjectId(ObjectId projectId, int offset, int limit) {
        return findAll(projectIdKey, projectId.toString(), offset, limit);
    }

    public long countByProjectId(ObjectId projectId) {
        return count(projectIdKey, projectId.toString());
    }
}
