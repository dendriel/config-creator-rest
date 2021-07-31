package main.containers;

import org.springframework.boot.test.context.TestConfiguration;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.wait.strategy.Wait;


@TestConfiguration
public class MongoDBServerContainer {
    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.2")
            .withEnv("MONGO_INITDB_DATABASE", "config_creator");

    static {
        mongoDBContainer.start();
        mongoDBContainer.waitingFor(Wait.forHealthcheck());
        System.setProperty("mongo.db.user", "");
        System.setProperty("mongo.db.pass", "");
        System.setProperty("mongo.db.port", mongoDBContainer.getFirstMappedPort().toString());
    }
}
