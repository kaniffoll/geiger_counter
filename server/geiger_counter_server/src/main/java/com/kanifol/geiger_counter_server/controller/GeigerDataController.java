package com.kanifol.geiger_counter_server.controller;

import com.kanifol.geiger_counter_server.service.GeigerDataService;
import com.kanifol.geiger_counter_server.service.dto.GeigerDataDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class GeigerDataController {
    private final GeigerDataService geigerDataService;

    GeigerDataController(GeigerDataService geigerDataService) {
        this.geigerDataService = geigerDataService;
    }

    @PutMapping
    public ResponseEntity<Void> putGeigerData(@RequestBody GeigerDataDto geigerDataDto) {
        geigerDataService.putGeigerData(geigerDataDto.geigerValue());
        return ResponseEntity.accepted().build();
    }
}