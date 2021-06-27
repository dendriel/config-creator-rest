package com.rozsa.business.impl;

import com.rozsa.business.ResourceBusiness;
import com.rozsa.dao.ResourceDao;
import com.rozsa.model.Resource;
import org.springframework.stereotype.Component;

@Component
public class ResourceBusinessImpl extends BaseBusinessImpl<Resource, ResourceDao> implements ResourceBusiness {
    public ResourceBusinessImpl(ResourceDao dao) {
        super(dao);
    }
}
