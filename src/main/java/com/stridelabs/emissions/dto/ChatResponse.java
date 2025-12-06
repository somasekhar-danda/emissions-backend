package com.stridelabs.emissions.dto;

public class ChatResponse {
    private String source; // local-data / internet
    private String answer;
    private String link;

    // --- Constructors ---
    public ChatResponse() {
    }

    public ChatResponse(String source, String answer, String link) {
        this.source = source;
        this.answer = answer;
        this.link = link;
    }

    // --- Getters & Setters ---
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    // --- Manual Builder ---
    public static class Builder {
        private String source;
        private String answer;
        private String link;

        public Builder source(String source) {
            this.source = source;
            return this;
        }

        public Builder answer(String answer) {
            this.answer = answer;
            return this;
        }

        public Builder link(String link) {
            this.link = link;
            return this;
        }

        public ChatResponse build() {
            return new ChatResponse(source, answer, link);
        }
    }
}
