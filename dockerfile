# # Stage 1: build
# # Start with a Gradle image that includes JDK 21
# FROM gradle:8.10.0-jdk21 AS build

# # Copy source code and build.gradle file to /app folder
# WORKDIR /app
# COPY build.gradle settings.gradle ./
# COPY src ./src

# # Build source code with Gradle
# RUN gradle build -x test

# # Stage 2: create image
# # Start with Amazon Correto JDK 21
# FROM amazoncorretto:21.0.4

# # Set working folder to App and copy compiled file from above step
# WORKDIR /app
# COPY --from=build /app/build/libs/*.jar app.jar

# # Command to run the application
# ENTRYPOINT ["java", "-jar", "app.jar"]

# Use an official OpenJDK runtime as a parent image
FROM amazoncorretto:21.0.4

# Set the working directory in the container
WORKDIR /app

# Copy the jar file into the container at /app
COPY build/libs/*.jar /app.jar

# Expose the port that the Spring Boot app will run on
EXPOSE 8080

# Command to run the jar file
ENTRYPOINT ["java", "-jar", "/app.jar"]
