package com.kanifol.geiger_counter_server.service.dto;

import java.time.Instant;

public record MeasurementDto(Instant receivedAt, String geigerValue) {
}
