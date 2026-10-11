package com.kanifol.geiger_counter_server.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GeigerDataDto(@JsonProperty("geiger_value") String geigerValue) {
}
