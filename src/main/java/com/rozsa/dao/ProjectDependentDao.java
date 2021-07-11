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

    protected abstract Object parseProjectId(ObjectId projectId);

    public List<TType> findAllByProjectId(ObjectId projectId, int offset, int limit) {
        return findAll(projectIdKey, parseProjectId(projectId), offset, limit);
    }

    public long countByProjectId(ObjectId projectId) {
        return count(projectIdKey, parseProjectId(projectId));
    }
}
