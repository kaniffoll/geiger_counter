package com.kanifol.geiger_counter_server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface GeigerDataRepository extends MongoRepository<Measurement, String> {
    List<Measurement> findByReceivedAtAfterOrderByReceivedAtAsc(Instant after);
}
