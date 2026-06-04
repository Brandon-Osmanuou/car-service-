# CLAUDE.md — car-service

## What this service does
Owns all Car data. No other service touches the cars database directly.
Part of Phase 2 of a microservices learning journey.

## Tech stack
- Java 21, Spring Boot 3.3.0, Maven
- Spring Data JPA, H2 (in-memory)
- Spring Cloud Eureka Client
- SpringDoc OpenAPI (Swagger)

## Ports and URLs
- Service port: 8081
- Swagger UI:   http://localhost:8081/swagger-ui.html
- H2 Console:   http://localhost:8081/h2-console (jdbc:h2:mem:cardb, user: sa)
- Eureka name:  CAR-SERVICE

## Endpoints
- GET    /cars                         — all cars (optional ?available=true)
- GET    /cars/{id}                    — single car
- POST   /cars                         — create car
- PUT    /cars/{id}                    — update car
- DELETE /cars/{id}                    — delete car
- PATCH  /cars/{id}/availability       — called by lease-service to mark car available/unavailable

## How to run
Start eureka-server first, then:
```bash
./mvnw spring-boot:run
```

## Startup order (always)
1. eureka-server  (port 8761)
2. api-gateway    (port 8080)
3. car-service    (port 8081)  ← this service
4. lease-service  (port 8082)

## Key architectural note
The PATCH /cars/{id}/availability endpoint is called by lease-service via OpenFeign.
In the monolith this was a direct Java call. Now it's HTTP over the network.
That one change is the core difference between monolith and microservices.

## Other services
- lease-service (port 8082) calls this service via OpenFeign
- api-gateway   (port 8080) routes /cars/** to this service
