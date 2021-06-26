package com.rozsa.dao;

import com.rozsa.dao.persistence.DatabaseConnection;
import com.rozsa.model.User;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import static com.rozsa.dao.Collection.USER;

@Repository
public class UserDao extends AbstractDao<User> {
    public UserDao(DatabaseConnection db) {
        super(db, User.class, USER.getName());
    }

    public User updateDefaultProjectId(ObjectId userId, ObjectId projectId) {
        User user = new User(userId);
        return update(user, "defaultProjectId", projectId);
    }

    public User findByUserId(long userId) {
        return find("userId", userId);
    }
}
