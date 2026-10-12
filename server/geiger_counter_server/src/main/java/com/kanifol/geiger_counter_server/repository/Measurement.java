package com.kanifol.geiger_counter_server.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("measurements")
public record Measurement(@Id String id, Instant receivedAt, String geigerValue) {
}
