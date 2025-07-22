Banking API – Getting Started Guide
===================================

This guide explains how to clone, configure, build, run and test the Banking API (Spring Boot) project.

Prerequisites
-------------

- Java 21
- Maven 3.8+
- Git
- IDE: IntelliJ IDEA / VSCode / Eclipse

Technologies Used
-----------------

| Layer / Purpose    | Technology / Library                           |
|--------------------|------------------------------------------------|
| Core Framework     | **Spring Boot 3.5.3** (Starter Web, Actuator)  |
| HTTP Client        | **Spring Cloud OpenFeign 2025.0.0**            |
| Validation         | Hibernate Validator 8 (JSR-380)                |
| Persistence (opt.) | Spring Data JPA + H2 (in-memory, sandbox only) |
| Logging            | SLF4J 1.7 + Logback (console pattern)          |
| Boilerplate Cuts   | Lombok 1.18                                    |
| Build Tool         | Maven                                          |
| Tests              | JUnit 5 + Mockito + JaCoCo (coverage ≥ 80 %)   |

Clone the repo
--------------
git clone https://github.com/Daniele410/banking-api.git
cd banking-api/banking-api

Configuration
-------------
Edit src/main/resources/application.properties:

fabrick.api.base-url=https://sandbox.platfr.io
fabrick.auth-schema=S2S
fabrick.api-key=FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP
fabrick.account-id=14537780

Optional profile example:
mvn spring-boot:run -Dspring-boot.run.profiles=dev

Build & Run
-----------

1. Build & tests
   mvn clean verify
2. Run
   mvn spring-boot:run
   # or
   java -jar target/banking-api-0.1.0.jar

Service runs on http://localhost:8080

Testing
-------
Run: mvn test
JaCoCo coverage report: target/site/jacoco/index.html ( ≥80% required )

Key Endpoints
-------------

1. GET /balance
   http://localhost:8080/balance

2. GET /transactions
   http://localhost:8080/transactions?from=YYYY-MM-DD&to=YYYY-MM-DD

3. POST /transfer
   http://localhost:8080/transfer
   Body example:
   {
   "creditor": {
   "name": "Mario Rossi",
   "account": { "accountCode": "IT23A0336844430152923804660" }
   },
   "description": "Test transfer",
   "amount": 100.0,
   "currency": "EUR",
   "executionDate": "2025-07-21"
   }

## Postman Collection

You’ll find a ready-to-import collection (v2.1) under  
`banking-api/docs/postman/Banking-API.postman_collection.json`.

The collection contains three pre-configured requests that hit **localhost:8080**:

1. **Get Balance** – `GET /balance`
2. **List Transactions** – `GET /transactions?from=…&to=…`
3. **Create Transfer** – `POST /transfer` (body with example payload)

Simply open Postman → *Import* → *File* → select the JSON file above and you’re ready to test the local API.

Logging
-------
SLF4J; customize via logback.xml if needed.

