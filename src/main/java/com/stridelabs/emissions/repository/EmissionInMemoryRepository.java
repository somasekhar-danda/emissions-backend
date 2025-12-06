package com.stridelabs.emissions.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stridelabs.emissions.model.EmissionRecord;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmissionInMemoryRepository {

    private static final Logger log = LoggerFactory.getLogger(EmissionInMemoryRepository.class);

    private final ObjectMapper objectMapper;

    private List<EmissionRecord> records = new ArrayList<>();

    public EmissionInMemoryRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void load() {
        try {
            InputStream is = new ClassPathResource("data/emissions.json").getInputStream();
            this.records = objectMapper.readValue(is, new TypeReference<>() {});
            log.info("Loaded {} emission rows.", records.size());
        } catch (Exception e) {
            log.error("Failed to load emissions data", e);
        }
    }

    // --- Getter for records ---
    public List<EmissionRecord> getRecords() {
        return records;
    }
}
