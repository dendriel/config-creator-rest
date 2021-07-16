package com.rozsa.business.impl;

import com.rozsa.business.ProjectDependentBusiness;
import com.rozsa.dao.Identifiable;
import com.rozsa.dao.ProjectDependentDao;
import org.bson.types.ObjectId;

import java.util.List;

public class ProjectDependentBusinessImpl<TModel extends Identifiable, TDao extends ProjectDependentDao<TModel>>
        extends BaseBusinessImpl<TModel, TDao>
        implements ProjectDependentBusiness<TModel> {

    public ProjectDependentBusinessImpl(TDao dao) {
        super(dao);
    }

    @Override
    public List<TModel> findAll(ObjectId projectId, int offset, int limit) {
        return dao.findAllByProjectId(projectId, offset, limit);
    }

    @Override
    public List<TModel> findAll(ObjectId projectId) {
        return dao.findAllByProjectId(projectId);
    }

    @Override
    public long count(ObjectId projectId) {
        return dao.countByProjectId(projectId);
    }
}
