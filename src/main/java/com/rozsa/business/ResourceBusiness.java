package com.rozsa.business;

import com.rozsa.model.Resource;
import org.bson.types.ObjectId;

import java.util.List;

public interface ResourceBusiness extends BaseBusiness<Resource> {

    List<Resource> findAll(ObjectId projectId, int offset, int limit);
}
