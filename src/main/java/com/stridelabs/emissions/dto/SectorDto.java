package com.stridelabs.emissions.dto;

public class SectorDto {
    private String name;
    private String description;

    // --- Constructors ---
    public SectorDto() {
    }

    public SectorDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // --- Getters & Setters ---
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // --- Manual Builder ---
    public static class Builder {
        private String name;
        private String description;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public SectorDto build() {
            return new SectorDto(name, description);
        }
    }
}
