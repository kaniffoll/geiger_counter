package com.kanifol.geiger_counter_server.controller;

import com.kanifol.geiger_counter_server.service.dto.GeigerDataDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class GeigerDataCounter {
    GeigerDataCounter() {
    }

    @PutMapping
    public ResponseEntity<Void> putGeigerData(@RequestBody GeigerDataDto geigerDataDto) {
        System.out.println(geigerDataDto.geigerValue());
        return ResponseEntity.accepted().build();
    }
}