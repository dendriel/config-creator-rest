package com.rozsa.dao;

import com.rozsa.dao.persistence.DatabaseConnection;
import org.bson.types.ObjectId;

import java.util.List;


public abstract class ProjectDependentDao<TType extends Identifiable> extends AbstractDao<TType> {
    private final String projectIdKey;

    public ProjectDependentDao(DatabaseConnection db, Class<TType> kind, String collectionName, String projectIdKey) {
        super(db, kind, collectionName);
        this.projectIdKey = projectIdKey;
    }

    public List<TType> findAllByProjectId(ObjectId projectId, int offset, int limit) {
        return findAll(projectIdKey, projectId.toString(), offset, limit);
    }

    public long countByProjectId(ObjectId projectId) {
        return count(projectIdKey, projectId.toString());
    }
}
