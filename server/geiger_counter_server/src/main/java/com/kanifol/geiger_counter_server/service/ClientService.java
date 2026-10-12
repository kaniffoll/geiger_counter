package com.kanifol.geiger_counter_server.service;

import com.kanifol.geiger_counter_server.repository.GeigerDataRepository;
import com.kanifol.geiger_counter_server.service.dto.Mappers;
import com.kanifol.geiger_counter_server.service.dto.MeasurementDto;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Service
public class ClientService {
    private final GeigerDataRepository repository;

    public ClientService(GeigerDataRepository repository) {
        this.repository = repository;
    }

    public List<MeasurementDto> getAllMeasurements() {
        List<MeasurementDto> measurementDtoList = new java.util.ArrayList<>(repository.findAll().stream().map(Mappers::toDto).toList());
        Collections.reverse(measurementDtoList);
        return measurementDtoList;
    }

    public List<MeasurementDto> getAllMeasurementsAfter(Instant after) {
        List<MeasurementDto> measurementDtoList = new java.util.ArrayList<>(repository.findByReceivedAtAfterOrderByReceivedAtAsc(after).stream().map(Mappers::toDto).toList());
        Collections.reverse(measurementDtoList);
        return measurementDtoList;
    }
}
