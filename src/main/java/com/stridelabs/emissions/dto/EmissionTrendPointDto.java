package com.stridelabs.emissions.dto;

public class EmissionTrendPointDto {
    private int year;
    private double emissions;

    // --- Constructors ---
    public EmissionTrendPointDto() {
    }

    public EmissionTrendPointDto(int year, double emissions) {
        this.year = year;
        this.emissions = emissions;
    }

    // --- Getters & Setters ---
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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
        private double emissions;

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Builder emissions(double emissions) {
            this.emissions = emissions;
            return this;
        }

        public EmissionTrendPointDto build() {
            return new EmissionTrendPointDto(year, emissions);
        }
    }
}
