package com.rozsa.dao.persistence;

import com.rozsa.dao.Identifiable;
import org.bson.types.ObjectId;

import java.util.List;

public interface DatabaseConnection {
    <T> T create(T npc, Class<T> kind, String collection);

    <T extends Identifiable> T update(T obj, Class<T> kind, String collection);

    <T extends Identifiable> T update(T obj, Class<T> kind, String collection, String key, Object value);

    <T> T find(Class<T> kind, String collection, String key, Object value);

    <T> T findById(ObjectId id, Class<T> kind, String collection);

    <T> List<T> findAll(Class<T> kind, String collection, String key, Object value, int offset, int limit);

    <T> List<T> findAll(Class<T> kind, String collection);

    <T> List<T> findAll(Class<T> kind, String collection, int offset, int limit);

    <T> boolean deleteById(ObjectId id, Class<T> kind, String collection);

    <T> long count(Class<T> kind, String collection);

    <T> long count(Class<T> kind, String collection, String key, Object value);
}
