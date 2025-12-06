package com.stridelabs.emissions.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stridelabs.emissions.dto.ChatRequest;
import com.stridelabs.emissions.dto.ChatResponse;
import com.stridelabs.emissions.dto.EmissionSummaryResponse;
import com.stridelabs.emissions.model.EmissionRecord;
import com.stridelabs.emissions.repository.EmissionInMemoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;

@Service
public class ChatService {

    private final EmissionInMemoryRepository repo;
    private final EmissionService emissionService;
    private final RestTemplate http = new RestTemplate();

    public ChatService(EmissionInMemoryRepository repo,
                       EmissionService emissionService) {
        this.repo = repo;
        this.emissionService = emissionService;
    }

    // ========== 1. Detect data queries ==========
    private boolean isDataQuery(String msg) {
        String lowerMsg = msg.toLowerCase();

        // Year or emission keywords
        boolean hasYearOrKeyword = lowerMsg.matches(".*\\b(\\d{4}|sector|highest|emission)\\b.*");

        // Sector detection: allow partial matches (e.g. "industry" vs "industry sector")
        boolean hasSector = repo.getRecords().stream()
                .map(r -> r.getSector().toLowerCase())
                .anyMatch(sector -> lowerMsg.contains(sector) || sector.contains(lowerMsg));

        return hasYearOrKeyword || hasSector;
    }

    // ========== 2. Answer for sector trend ==========
    private ChatResponse answerForSectorTrend(String sector) {
        List<EmissionRecord> records = repo.getRecords().stream()
                .filter(r -> r.getSector().equalsIgnoreCase(sector))
                .toList();

        if (records.isEmpty()) {
            return new ChatResponse.Builder()
                    .source("local-data")
                    .answer("No data available for sector: " + sector)
                    .link(null)
                    .build();
        }

        var min = records.stream()
                .min(Comparator.comparingDouble(EmissionRecord::getEmissions))
                .orElse(null);

        var max = records.stream()
                .max(Comparator.comparingDouble(EmissionRecord::getEmissions))
                .orElse(null);

        StringBuilder trend = new StringBuilder("Trend for " + sector + " sector:\n");
        records.forEach(r -> trend.append(r.getYear())
                .append(": ").append(r.getEmissions()).append(" Gt CO₂e\n"));

        String answer = "The " + sector + " sector had its lowest emissions in "
                + min.getYear() + " (" + min.getEmissions() + " Gt CO₂e) and highest in "
                + max.getYear() + " (" + max.getEmissions() + " Gt CO₂e).\n\n"
                + trend;

        return new ChatResponse.Builder()
                .source("local-data")
                .answer(answer)
                .link(null)
                .build();
    }

    // ========== 3. Answer for year summary ==========
    private ChatResponse answerForYear(int year) {
        var summary = emissionService.summary(year);

        var max = summary.getSectors().stream()
                .max(Comparator.comparingDouble(EmissionSummaryResponse.SectorEmission::getEmissions))
                .orElse(null);

        String answer = "In " + year + ", the highest emitting sector is "
                + max.getSector() + " with " + max.getEmissions() + " Gt CO₂e.";

        return new ChatResponse.Builder()
                .source("local-data")
                .answer(answer)
                .link(null)
                .build();
    }

    // ========== 4. Answer for year + sector ==========
    private ChatResponse answerForYearAndSector(int year, String sector) {
        var summary = emissionService.summary(year);

        var sectorData = summary.getSectors().stream()
                .filter(s -> s.getSector().equalsIgnoreCase(sector))
                .findFirst()
                .orElse(null);

        if (sectorData == null) {
            return new ChatResponse.Builder()
                    .source("local-data")
                    .answer("No data for " + sector + " in " + year)
                    .link(null)
                    .build();
        }

        String answer = "In " + year + ", the " + sector + " sector emitted "
                + sectorData.getEmissions() + " Gt CO₂e, contributing "
                + String.format("%.2f", sectorData.getPercentage()) + "% of total emissions.";

        return new ChatResponse.Builder()
                .source("local-data")
                .answer(answer)
                .link(null)
                .build();
    }

    // ========== 5. Wikipedia lookup ==========
    private ChatResponse answerFromInternet(String message) {
        try {
            String url = "https://en.wikipedia.org/w/api.php" +
                    "?action=query&list=search&srsearch=" +
                    message.replace(" ", "%20") +
                    "&format=json";

            String json = http.getForObject(url, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            JsonNode first = root.path("query").path("search").get(0);

            String title = first.path("title").asText();
            String snippet = first.path("snippet").asText()
                    .replace("<span class=\"searchmatch\">", "")
                    .replace("</span>", "");

            String link = "https://en.wikipedia.org/wiki/" + title.replace(" ", "_");

            return new ChatResponse.Builder()
                    .source("internet")
                    .answer(snippet)
                    .link(link)
                    .build();

        } catch (Exception e) {
            return new ChatResponse.Builder()
                    .source("internet")
                    .answer("Could not fetch online data.")
                    .link(null)
                    .build();
        }
    }

    // ========== 6. Main handler ==========
    public ChatResponse respond(ChatRequest req) {
        String msg = req.getMessage().toLowerCase();

        if (isDataQuery(msg)) {
            // Extract year if present
            int year = -1;
            var matcher = java.util.regex.Pattern.compile("\\b(\\d{4})\\b").matcher(msg);
            if (matcher.find()) {
                year = Integer.parseInt(matcher.group(1));
            }

            // Detect sector
            String sector = repo.getRecords().stream()
                    .map(r -> r.getSector())
                    .filter(s -> msg.contains(s.toLowerCase()) || s.toLowerCase().contains(msg))
                    .findFirst()
                    .orElse(null);

            // Case 1: Year + Sector
            if (year != -1 && sector != null) {
                return answerForYearAndSector(year, sector);
            }

            // Case 2: Sector only
            if (sector != null) {
                return answerForSectorTrend(sector);
            }

            // Case 3: Year only
            if (year != -1) {
                return answerForYear(year);
            }
        }

        // Fallback: Wikipedia
        return answerFromInternet(req.getMessage());
    }
}
