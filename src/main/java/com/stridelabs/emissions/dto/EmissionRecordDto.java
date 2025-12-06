package com.stridelabs.emissions.dto;

public class EmissionRecordDto {
    private int year;
    private String sector;
    private double emissions;

    // --- Constructors ---
    public EmissionRecordDto() {
    }

    public EmissionRecordDto(int year, String sector, double emissions) {
        this.year = year;
        this.sector = sector;
        this.emissions = emissions;
    }

    // --- Getters & Setters ---
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

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

    // --- Manual Builder ---
    public static class Builder {
        private int year;
        private String sector;
        private double emissions;

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Builder sector(String sector) {
            this.sector = sector;
            return this;
        }

        public Builder emissions(double emissions) {
            this.emissions = emissions;
            return this;
        }

        public EmissionRecordDto build() {
            return new EmissionRecordDto(year, sector, emissions);
        }
    }
}
