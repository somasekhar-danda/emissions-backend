package com.stridelabs.emissions.service;

import com.stridelabs.emissions.dto.EmissionSummaryResponse;
import com.stridelabs.emissions.dto.EmissionTrendPointDto;
import com.stridelabs.emissions.dto.SectorDto;
import com.stridelabs.emissions.model.EmissionRecord;
import com.stridelabs.emissions.repository.EmissionInMemoryRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmissionService {

    private final EmissionInMemoryRepository repo;

    public EmissionService(EmissionInMemoryRepository repo) {
        this.repo = repo;
    }

    public List<Integer> years() {
        return repo.getRecords().stream()
                .map(EmissionRecord::getYear)
                .distinct()
                .sorted()
                .toList();
    }

    public List<SectorDto> sectors() {
        return repo.getRecords().stream()
                .map(EmissionRecord::getSector)
                .distinct()
                .sorted()
                .map(s -> new SectorDto(s, s + " sector emissions"))
                .toList();
    }

    public EmissionSummaryResponse summary(int year) {
        List<EmissionRecord> list = repo.getRecords().stream()
                .filter(r -> r.getYear() == year)
                .toList();

        double total = list.stream()
                .mapToDouble(EmissionRecord::getEmissions)
                .sum();

        List<EmissionSummaryResponse.SectorEmission> sectors = list.stream()
                .map(r -> new EmissionSummaryResponse.SectorEmission.Builder()
                        .sector(r.getSector())
                        .emissions(r.getEmissions())
                        .percentage((r.getEmissions() / total) * 100)
                        .build())
                .toList();

        return new EmissionSummaryResponse.Builder()
                .year(year)
                .totalEmissions(total)
                .sectors(sectors)
                .build();
    }

    public List<EmissionTrendPointDto> trend(String sector) {
        return repo.getRecords().stream()
                .filter(r -> r.getSector().equalsIgnoreCase(sector))
                .map(r -> new EmissionTrendPointDto(r.getYear(), r.getEmissions()))
                .sorted(Comparator.comparingInt(EmissionTrendPointDto::getYear))
                .toList();
    }

	public List<EmissionRecord> getAllRecords() {
		
		return repo.getRecords();
	}

	 public List<EmissionRecord> getEmissions(Integer year, String sector) {
	        return repo.getRecords().stream()
	                .filter(r -> (year == null || r.getYear() == year))
	                .filter(r -> (sector == null || r.getSector().equalsIgnoreCase(sector)))
	                .collect(Collectors.toList());
	    }
}
