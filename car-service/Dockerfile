# ── Stage 1: Build ────────────────────────────────────────────────────────────
# We use a multi-stage build — this keeps the final image small.
# Stage 1 uses the full JDK to compile and package the app.
# Stage 2 uses only the JRE to run it (no compiler needed at runtime).

FROM eclipse-temurin:21-jdk-alpine AS builder

# Set working directory inside the container
WORKDIR /app

# Copy Maven wrapper and pom.xml first.
# Docker caches layers — by copying pom.xml before source code,
# Maven dependencies are only re-downloaded when pom.xml changes,
# not every time you change a Java file. Big time saver.
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies (this layer gets cached)
RUN ./mvnw dependency:go-offline -B

# Now copy the source code
COPY src src

# Build the app, skip tests (tests run in a separate pipeline step)
RUN ./mvnw package -DskipTests

# ── Stage 2: Run ──────────────────────────────────────────────────────────────
# Only the JRE — much smaller image than JDK
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy only the built JAR from the builder stage
# The fat JAR contains everything needed to run
COPY --from=builder /app/target/*.jar app.jar

# Expose the port car-service runs on
EXPOSE 8081

# Run the app
# -Djava.security.egd speeds up Spring Boot startup on Linux containers
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
