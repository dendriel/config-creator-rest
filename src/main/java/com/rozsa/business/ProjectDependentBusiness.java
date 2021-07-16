package com.rozsa.business;

import org.bson.types.ObjectId;

import java.util.List;

public interface ProjectDependentBusiness<TModel> extends BaseBusiness<TModel> {
    List<TModel> findAll(ObjectId projectId, int offset, int limit);

    List<TModel> findAll(ObjectId projectId);

    long count(ObjectId projectId);
}
