package com.rozsa.business;

import org.bson.types.ObjectId;

import java.util.List;

public interface ProjectDependentBusiness<TModel> extends BaseBusiness<TModel> {
    List<TModel> findAll(ObjectId projectId, int offset, int limit);

    long count(ObjectId projectId);
}
