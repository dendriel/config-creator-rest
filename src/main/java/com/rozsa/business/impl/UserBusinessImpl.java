package com.rozsa.business.impl;

import com.rozsa.business.UserBusiness;
import com.rozsa.dao.UserDao;
import com.rozsa.model.User;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class UserBusinessImpl extends BaseBusinessImpl<User, UserDao> implements UserBusiness {

    public UserBusinessImpl(UserDao dao) {
        super(dao);
    }

    public void setDefaultProject(ObjectId id, ObjectId projectId) {
        dao.updateDefaultProjectId(id, projectId);
    }

    public User create(long userId) {
        User user = new User(userId);
        return dao.save(user);
    }

    @Override
    public User getOrCreateUserByUserId(long userId) {
        User user = dao.findByUserId(userId);
        if (user != null) {
            return user;
        }

        user = new User(userId);
        return dao.create(user);
    }
}
