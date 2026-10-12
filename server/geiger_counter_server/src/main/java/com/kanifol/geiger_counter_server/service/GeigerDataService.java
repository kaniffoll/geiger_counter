package com.kanifol.geiger_counter_server.service;

import com.kanifol.geiger_counter_server.repository.GeigerDataRepository;
import com.kanifol.geiger_counter_server.repository.Measurement;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class GeigerDataService {
    private final GeigerDataRepository repository;

    public GeigerDataService(GeigerDataRepository repository) {
        this.repository = repository;
    }

    public void putGeigerData(String geigerValue) {
        repository.save(new Measurement(null, Instant.now(), geigerValue));
    }
}
