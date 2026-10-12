package com.kanifol.geiger_counter_server.controller;

import com.kanifol.geiger_counter_server.service.ClientService;
import com.kanifol.geiger_counter_server.service.dto.MeasurementDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<MeasurementDto>> getAllMeasurements() {
        return ResponseEntity.ok(clientService.getAllMeasurements());
    }

    @GetMapping("/after")
    public ResponseEntity<List<MeasurementDto>> getAllMeasurementsAfter(Instant after) {
        return ResponseEntity.ok(clientService.getAllMeasurementsAfter(after));
    }
}
