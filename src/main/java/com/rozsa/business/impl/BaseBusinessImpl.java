package com.rozsa.business.impl;

import com.rozsa.business.BaseBusiness;
import com.rozsa.dao.AbstractDao;
import com.rozsa.dao.Identifiable;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

@AllArgsConstructor
public class BaseBusinessImpl<TModel extends Identifiable, TDao extends AbstractDao<TModel>>
        implements BaseBusiness<TModel> {

    private final TDao dao;

    @Override
    public TModel create(TModel model) {
        return dao.create(model);
    }

    @Override
    public TModel find(ObjectId id) {
        return dao.findById(id);
    }

    @Override
    public List<TModel> findAll(int offset, int limit) {
        return dao.findAll(offset, limit);
    }

    @Override
    public boolean remove(ObjectId id) {
        return dao.deleteById(id);
    }

    @Override
    public TModel update(TModel model) {
        return dao.update(model);
    }

    @Override
    public long count() {
        return dao.count();
    }
}
