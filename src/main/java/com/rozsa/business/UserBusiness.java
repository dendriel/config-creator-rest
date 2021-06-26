package com.rozsa.business;

import com.rozsa.model.User;
import org.bson.types.ObjectId;

public interface UserBusiness {
    User create(long userId);

    User getOrCreateUserByUserId(long userId);

    void setDefaultProject(ObjectId id, ObjectId projectId);
}
