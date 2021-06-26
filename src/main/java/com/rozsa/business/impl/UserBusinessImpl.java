package com.rozsa.business.impl;

import com.rozsa.business.UserBusiness;
import com.rozsa.dao.UserDao;
import com.rozsa.model.User;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserBusinessImpl implements UserBusiness {

    private final UserDao userDao;

    public void setDefaultProject(ObjectId id, ObjectId projectId) {
        userDao.updateDefaultProjectId(id, projectId);
    }

    public User create(long userId) {
        User user = new User(userId);
        return userDao.save(user);
    }

    @Override
    public User getOrCreateUserByUserId(long userId) {
        User user = userDao.findByUserId(userId);
        if (user != null) {
            return user;
        }

        user = new User(userId);
        return userDao.create(user);
    }
}
