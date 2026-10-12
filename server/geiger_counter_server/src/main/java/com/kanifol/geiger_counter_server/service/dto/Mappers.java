package com.kanifol.geiger_counter_server.service.dto;

import com.kanifol.geiger_counter_server.repository.Measurement;

public class Mappers {
    public static MeasurementDto toDto(Measurement measurement) {
        return new MeasurementDto(measurement.receivedAt(), measurement.geigerValue());
    }
}
