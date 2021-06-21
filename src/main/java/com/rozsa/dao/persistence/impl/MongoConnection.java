package com.rozsa.dao.persistence.impl;

import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.rozsa.dao.persistence.DatabaseConnection;
import com.rozsa.dao.Identifiable;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.*;

@PropertySource("classpath:mongo.properties")
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@Service
public class MongoConnection implements DatabaseConnection {
    private final MongoClient client;
    private final MongoDatabase db;

    public MongoConnection(
            @Value("${mongo.db.host}") String host,
            @Value("${mongo.db.port}") Integer port,
            @Value("${mongo.db.name}") String dbName,
            @Value("${mongo.db.user}") String user,
            @Value("${mongo.db.pass}") String pass
    ) {
        assert dbName != null : "DB_NAME option must be non-empty (ex.: -DDB_NAME=database-name)";

        client = createClient(host, port, user, pass, dbName);
        db = getDatabase(dbName);
    }

    private MongoClient createClient(String host, Integer port, String user, String pass, String dbName) {
        final ServerAddress serverAddress = new ServerAddress(host, port);

        MongoCredential credential = MongoCredential.createCredential(user, dbName, pass.toCharArray());
        MongoClientSettings settings = MongoClientSettings.builder()
                .credential(credential)
                .applyToClusterSettings(builder ->
                        builder.hosts(Collections.singletonList(serverAddress)))
                .writeConcern(WriteConcern.JOURNALED)
                .build();

        return MongoClients.create(settings);
    }

    private MongoDatabase getDatabase(String dbName) {
        CodecRegistry pojoCodecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(
                        PojoCodecProvider
                                .builder()
                                .automatic(true)
                                .build()
                )
        );

        MongoDatabase plainDb = client.getDatabase(dbName);
        return plainDb.withCodecRegistry(pojoCodecRegistry);
    }

    public <T> T create(T obj, Class<T> kind, String collection) {
        assert obj != null : String.format("Can't save null obj!");
        MongoCollection<T> coll = db.getCollection(collection, kind);
        coll.insertOne(obj);
        return obj;
    }

    public <T extends Identifiable> T update(T obj, Class<T> kind, String collection) {
        assert obj != null : String.format("Can't update null obj!");
        MongoCollection<T> coll = db.getCollection(collection, kind);
        Document targetDoc = new Document("_id", obj.getId());
        UpdateResult updateResult = coll.replaceOne(targetDoc, obj);
        if (updateResult.getModifiedCount() == 0) {
            return null;
        }
        return obj;
    }

    public <T> T findById(ObjectId id, Class<T> kind, String collection) {
        MongoCollection<T> coll = db.getCollection(collection, kind);
        Document targetDoc = new Document("_id", id);
        FindIterable<T> iterDoc = coll.find(targetDoc);

        return iterDoc.first();
    }

    public <T> List<T> findAll(Class<T> kind, String collection) {
        MongoCollection<T> coll = db.getCollection(collection, kind);
        FindIterable<T> iterDoc = coll.find();
        List<T> objs = new ArrayList<>();
        iterDoc.into(objs);

        return objs;
    }

    public <T> List<T> findAll(Class<T> kind, String collection, int offset, int limit) {
        MongoCollection<T> coll = db.getCollection(collection, kind);
        FindIterable<T> iterDoc = coll.find().skip(offset).limit(limit);
        List<T> objs = new ArrayList<>();
        iterDoc.into(objs);

        return objs;
    }

    public <T> boolean deleteById(ObjectId id, Class<T> kind, String collection) {
        MongoCollection<T> coll = db.getCollection(collection, kind);
        Document targetDoc = new Document("_id", id);
        DeleteResult result = coll.deleteOne(targetDoc);

        return result.getDeletedCount() > 0;
    }

    public <T> long count(Class<T> kind, String collection) {
        MongoCollection<T> coll = db.getCollection(collection, kind);

        return coll.countDocuments();
    }
}
