package com.kanifol.geiger_counter_server.controller;

import com.kanifol.geiger_counter_server.service.GeigerDataService;
import com.kanifol.geiger_counter_server.service.dto.GeigerDataDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

@RestController
class GeigerDataController {
    private final GeigerDataService geigerDataService;
    private final byte[] expected;

    GeigerDataController(GeigerDataService geigerDataService, @Value("${api-key}") String apiKey) {
        this.geigerDataService = geigerDataService;
        this.expected = apiKey.getBytes(StandardCharsets.UTF_8);
    }

    @PutMapping
    public ResponseEntity<Void> putGeigerData(@RequestHeader(value = "X-API-Key", required = false) String key, @RequestBody GeigerDataDto geigerDataDto) {
        if (key == null || !MessageDigest.isEqual(expected, key.getBytes(StandardCharsets.UTF_8))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        geigerDataService.putGeigerData(geigerDataDto.geigerValue());
        return ResponseEntity.accepted().build();
    }
}