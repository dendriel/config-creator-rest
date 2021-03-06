package com.rozsa.dao;

import com.rozsa.dao.persistence.DatabaseConnection;
import org.bson.types.ObjectId;

import java.util.List;

public abstract class AbstractDao<TType extends Identifiable> {
    protected final DatabaseConnection db;

    protected final String collectionName;

    private Class<TType> objKind;

    public AbstractDao(DatabaseConnection db, Class<TType> kind, String collectionName) {
        this.db = db;
        this.objKind = kind;
        this.collectionName = collectionName;
    }

    public TType create(TType obj) {
        return db.create(obj, objKind, collectionName);
    }

    public TType update(TType obj) {
        return db.update(obj, objKind, collectionName);
    }

    public void create(List<TType> obj) {
        obj.forEach(this::create);
    }

    public TType save(TType obj) {
        if (obj.getId() == null) {
            return db.create(obj, objKind, collectionName);
        }
        return db.update(obj, objKind, collectionName);
    }

    public void save(List<TType> obj) {
        obj.forEach(this::save);
    }

    public TType update(TType obj, String key, Object value) {
        return db.update(obj, objKind, collectionName, key, value);
    }

    public TType find(String key, Object value) {
        return db.find(objKind, collectionName, key, value);
    }

    public TType findById(ObjectId id) {
        return db.findById(id, objKind, collectionName);
    }

    public List<TType> findAll() {
        return db.findAll(objKind, collectionName);
    }

    public List<TType> findAll(String key, Object value, int offset, int limit) {
        return db.findAll(objKind, collectionName, key, value, offset, limit);
    }

    public List<TType> findAll(String key, Object value) {
        return db.findAll(objKind, collectionName, key, value);
    }

    public List<TType> findAll(int offset, int limit) {
        return db.findAll(objKind, collectionName, offset, limit);
    }

    public boolean deleteById(ObjectId id) {
        return db.deleteById(id, objKind, collectionName);
    }

    public long count() {
        return db.count(objKind, collectionName);
    }

    public long count(String key, Object value) {
        return db.count(objKind, collectionName, key, value);
    }
}
