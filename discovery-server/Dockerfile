# Stage 1: Build the application using Maven
FROM maven:3.9.9-eclipse-temurin-23-alpine AS builder
LABEL authors="Ashutosh"

# Set working directory
WORKDIR /app

# Copy only pom.xml first and resolve dependencies to leverage Docker cache
COPY . .

# Package the application (skip tests for faster build)
RUN mvn -pl discovery-server -am clean package -DskipTests

# Stage 2: Create the runtime image
FROM gcr.io/distroless/java21-debian12

# Set working directory
WORKDIR /app

# Copy only the built jar from the builder stage
COPY --from=builder /app/discovery-server/target/*.jar app.jar

# Expose port
EXPOSE 8761

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
