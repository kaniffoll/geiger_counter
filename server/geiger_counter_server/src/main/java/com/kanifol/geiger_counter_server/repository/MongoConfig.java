package com.kanifol.geiger_counter_server.repository;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    @Bean
    ApplicationRunner createCappedCollection(MongoTemplate template) {
        return args -> {
            if (!template.collectionExists(Measurement.class)) {
                template.createCollection(
                        Measurement.class,
                        CollectionOptions.empty()
                                .capped()
                                .size(1_048_576)
                                .maxDocuments(50)
                );
            }
        };
    }
}
