package main.containers;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.rozsa.dao.persistence.impl.MongoConnection;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.boot.test.context.TestConfiguration;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.util.Collections;


@TestConfiguration
public class MongoDBServerContainer {
    private static final String DB_NAME = "config_creator";

    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.2")
            .withEnv("MONGO_INITDB_DATABASE", DB_NAME);

    private static MongoClient client;

    static {
        mongoDBContainer.start();
        mongoDBContainer.waitingFor(Wait.forHealthcheck());
        Integer port = mongoDBContainer.getFirstMappedPort();

        System.setProperty("mongo.db.user", "");
        System.setProperty("mongo.db.pass", "");
        System.setProperty("mongo.db.port", port.toString());

        client = createClient("localhost", port);
    }

    public static void clearDatabase() {
        MongoDatabase plainDb = client.getDatabase(DB_NAME);
        plainDb.drop();
    }

    private static MongoClient createClient(String host, Integer port) {
        final ServerAddress serverAddress = new ServerAddress(host, port);

        MongoClientSettings.Builder builder = MongoClientSettings.builder()
                .applyToClusterSettings(b -> b.hosts(Collections.singletonList(serverAddress)))
                .writeConcern(WriteConcern.JOURNALED);

        MongoClientSettings settings = builder.build();

        return MongoClients.create(settings);
    }
}
