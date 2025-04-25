# Step 1: Use a base image with Java
FROM openjdk:17-jdk-slim AS builder
LABEL authors="Ashutosh"

# Step 2: Set the working directory in the container
WORKDIR /app

# Step 3: Copy the application JAR file into the container
COPY /target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway-0.0.1-SNAPSHOT.jar

# Step 4: Expose the application port
EXPOSE 8081

# Step 5: Define the entry point to run the application
ENTRYPOINT ["java", "-jar", "api-gateway-0.0.1-SNAPSHOT.jar"]

# Stage 1: Build the application
# Step 1: Use a base image with Java
FROM maven:3.9.9-eclipse-temurin-23-alpine as builder
# Step 2: Set the working directory in the container
WORKDIR /app
# Step 3: Copy the application POM fileand source code into the container
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "api-gateway-0.0.1-SNAPSHOT.jar"]