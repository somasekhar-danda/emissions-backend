package com.stridelabs.emissions.controllers;

import com.stridelabs.emissions.dto.EmissionSummaryResponse;
import com.stridelabs.emissions.dto.EmissionTrendPointDto;
import com.stridelabs.emissions.dto.SectorDto;
import com.stridelabs.emissions.model.EmissionRecord;
import com.stridelabs.emissions.service.EmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emissions")
public class EmissionController {

    private final EmissionService service;

    public EmissionController(EmissionService service) {
        this.service = service;
    }
    
//    @GetMapping
//    public ResponseEntity<List<EmissionRecord>> getEmissions(
//            @RequestParam(required = false) Integer year,
//            @RequestParam(required = false) String sector) {
//        List<EmissionRecord> records = service.getEmissions(year, sector);
//        return ResponseEntity.ok(records);
//    }

    @GetMapping
    public ResponseEntity<List<EmissionRecord>> allEmissions(){
    	return ResponseEntity.ok(service.getAllRecords());
    }
    
    @GetMapping("/years")
    public ResponseEntity<List<Integer>> years() {
        return ResponseEntity.ok(service.years());
    }

    @GetMapping("/sectors")
    public ResponseEntity<List<SectorDto>> sectors() {
        return ResponseEntity.ok(service.sectors());
    }

    
    @GetMapping("/summary")
    public ResponseEntity<EmissionSummaryResponse> summary(
            @RequestParam int year) {
        return ResponseEntity.ok(service.summary(year));
    }

    @GetMapping("/trend")
    public ResponseEntity<List<EmissionTrendPointDto>> trend(
            @RequestParam String sector) {
        return ResponseEntity.ok(service.trend(sector));
    }
}
