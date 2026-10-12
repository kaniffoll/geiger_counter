package com.kanifol.geiger_counter_server.service.dto;

import com.kanifol.geiger_counter_server.repository.Measurement;

public class Mappers {
    public static MeasurementDto toDto(Measurement measurement) {
        double geigerValue;
        try {
            geigerValue = Double.parseDouble(measurement.geigerValue());
        } catch (NumberFormatException e) {
            geigerValue = -1.0;
        }
        return new MeasurementDto(measurement.receivedAt(), geigerValue);
    }
}
