package com.stridelabs.emissions.dto;

import java.util.List;

public class EmissionSummaryResponse {

    private int year;
    private double totalEmissions;
    private List<SectorEmission> sectors;

    // --- Constructors ---
    public EmissionSummaryResponse() {
    }

    public EmissionSummaryResponse(int year, double totalEmissions, List<SectorEmission> sectors) {
        this.year = year;
        this.totalEmissions = totalEmissions;
        this.sectors = sectors;
    }

    // --- Getters & Setters ---
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getTotalEmissions() {
        return totalEmissions;
    }

    public void setTotalEmissions(double totalEmissions) {
        this.totalEmissions = totalEmissions;
    }

    public List<SectorEmission> getSectors() {
        return sectors;
    }

    public void setSectors(List<SectorEmission> sectors) {
        this.sectors = sectors;
    }

    // --- Manual Builder ---
    public static class Builder {
        private int year;
        private double totalEmissions;
        private List<SectorEmission> sectors;

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Builder totalEmissions(double totalEmissions) {
            this.totalEmissions = totalEmissions;
            return this;
        }

        public Builder sectors(List<SectorEmission> sectors) {
            this.sectors = sectors;
            return this;
        }

        public EmissionSummaryResponse build() {
            return new EmissionSummaryResponse(year, totalEmissions, sectors);
        }
    }

    // --- Nested static class ---
    public static class SectorEmission {
        private String sector;
        private double emissions;
        private double percentage;

        // --- Constructors ---
        public SectorEmission() {
        }

        public SectorEmission(String sector, double emissions, double percentage) {
            this.sector = sector;
            this.emissions = emissions;
            this.percentage = percentage;
        }

        // --- Getters & Setters ---
        public String getSector() {
            return sector;
        }

        public void setSector(String sector) {
            this.sector = sector;
        }

        public double getEmissions() {
            return emissions;
        }

        public void setEmissions(double emissions) {
            this.emissions = emissions;
        }

        public double getPercentage() {
            return percentage;
        }

        public void setPercentage(double percentage) {
            this.percentage = percentage;
        }

        // --- Manual Builder ---
        public static class Builder {
            private String sector;
            private double emissions;
            private double percentage;

            public Builder sector(String sector) {
                this.sector = sector;
                return this;
            }

            public Builder emissions(double emissions) {
                this.emissions = emissions;
                return this;
            }

            public Builder percentage(double percentage) {
                this.percentage = percentage;
                return this;
            }

            public SectorEmission build() {
                return new SectorEmission(sector, emissions, percentage);
            }
        }
    }
}
