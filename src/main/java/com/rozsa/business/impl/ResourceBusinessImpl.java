package com.rozsa.business.impl;

import com.rozsa.business.ResourceBusiness;
import com.rozsa.dao.ResourceDao;
import com.rozsa.model.Resource;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ResourceBusinessImpl extends BaseBusinessImpl<Resource, ResourceDao> implements ResourceBusiness {

    public ResourceBusinessImpl(ResourceDao dao) {
        super(dao);
    }

    @Override
    public List<Resource> findAll(ObjectId projectId, int offset, int limit) {
        return dao.findAllByProjectId(projectId, offset, limit);
    }
}
