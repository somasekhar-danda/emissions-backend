🌍 Emissions Insight Backend

Stride Labs – HackForward 2025 (Round 2 Submission)

A lightweight analytics API built using Spring Boot, designed to serve emissions data and power an AI-assisted chat system.


---

📌 Overview

This backend provides:

📊 Emissions analytics API

🔍 Year-wise and sector-wise summaries

📈 Trend analysis endpoints

🤖 AI-powered Chat API (dataset + internet-assisted responses)

⚡ H2 in-memory database (fast deploy, no external DB required)

🌐 Fully CORS-enabled to support deployed React frontend

🚀 Cloud-ready Spring Boot JAR running on Render/Railway


The goal is to deliver a robust API for the Emissions Dashboard frontend and support intelligent chat-based insights.


---

🚀 Features

🔹 Emissions Analytics

Get list of available years

Get list of sectors

Get summary for any selected year

Get trend for any selected sector

Get sector summary + trend summary (used by Chat Assistant)


🔹 AI Chat Assistant ( /api/chat )

The chat service:

Uses regular expressions to interpret natural-language questions

Fetches local dataset insights

Optionally fetches information from trusted internet sources

Returns structured responses:

text

tables

trends

links (if internet data included)




---

📂 Project Structure

emissions-backend/
│
├── src/main/java/com/stridelabs/emissions/
│   ├── EmissionsInsightApplication.java        # Spring Boot main class
│   │
│   ├── config/
│   │   └── SecurityConfig.java                 # CORS + API exposure
│   │
│   ├── controllers/
│   │   ├── EmissionController.java             # /api/emissions/*
│   │   └── ChatController.java                 # /api/chat
│   │
│   ├── dto/
│   │   ├── ChatRequest.java
│   │   ├── ChatResponse.java
│   │   ├── EmissionRecordDto.java
│   │   ├── EmissionSummaryResponse.java
│   │   ├── EmissionTrendPointDto.java
│   │   └── SectorDto.java
│   │
│   ├── model/
│   │   └── EmissionRecord.java                 # In-memory dataset model
│   │
│   ├── repository/                             # Not used (H2 only)
│   │
│   ├── service/
│   │   ├── EmissionService.java                # Analytics logic
│   │   └── ChatService.java                    # Chat reasoning logic
│   │
│   └── util/
│
├── src/main/resources/
│   ├── application.properties                  # H2 + server config
│   └── data/                                   # emission CSV/JSON dataset
│
└── pom.xml                                     # Build configuration


---

⚙️ Tech Stack

Java 17

Spring Boot 3

H2 In-Memory Database

Spring Web

Spring Validation

Maven



---

🔌 API Endpoints

📁 Base Path:

/api/emissions


---

📜 Get all years

GET /api/emissions/years

Response:

[2010, 2015, 2019]


---

📜 Get all sectors

GET /api/emissions/sectors


---

📊 Get summary for a year

GET /api/emissions/summary?year=2015


---

📉 Get trend for a sector

GET /api/emissions/trend?sector=Transport


---

🤖 Chat API

POST /api/chat

Request:

{
  "message": "Show me trend of electricity sector"
}

Response:

{
  "answer": "The Electricity & Heat sector has shown increasing emissions from 2010 to 2019.",
  "source": "local-data",
  "data": [...],
  "link": null
}


---

🗄️ application.properties

spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=none
spring.h2.console.enabled=true

# Deployment
server.port=${PORT:8080}


---

🧪 Run Locally

1️⃣ Clone repo

git clone https://github.com/your-username/emissions-backend.git
cd emissions-backend

2️⃣ Build the JAR

mvn clean package -DskipTests

3️⃣ Run

java -jar target/emissions-0.0.1-SNAPSHOT.jar

Server runs at:

http://localhost:8080


---

🌐 Deployment (Render / Railway)

The backend deploys with zero configuration because:

✔ Uses H2 (no external DB env vars needed)
✔ Uses ${PORT} injection automatically
✔ Runs using a single command:

java -jar target/emissions-0.0.1-SNAPSHOT.jar


---

🏁 Final Notes for Evaluators

API is fully stateless and lightweight.

Designed for quick evaluation and reliability.

Compatible with modern frontend frameworks (React).

Ideal for scalable cloud deployments.
