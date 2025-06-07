# Stage 1: Build the application
FROM gradle:8.10.0-jdk21 AS build

# Set working directory
WORKDIR /app

# Copy Gradle configuration files
COPY build.gradle settings.gradle ./
COPY gradle ./gradle

# Copy source code
COPY src ./src

# Build the application, skipping tests
RUN gradle build -x test

# Stage 2: Create the runtime image
FROM amazoncorretto:21.0.4

# Set working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the port that the Spring Boot app will run on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]