package com.rozsa.business;

import org.bson.types.ObjectId;

import java.util.List;

public interface BaseBusiness<TModel> {
    TModel create(TModel model);

    TModel update(TModel model);

    TModel find(ObjectId id);

    List<TModel> findAll(int offset, int limit);

    boolean remove(ObjectId id);

    long count();
}
