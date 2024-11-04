# Stage 1: Build the application with Gradle
FROM gradle:8.10.2-jdk21 AS build

# Set the working directory
WORKDIR /app

# Copy only the Gradle build files to cache dependencies first
COPY build.gradle settings.gradle ./

# Download dependencies
RUN gradle build -x test --no-daemon || return 0

# Copy the source code
COPY src ./src

# Build the application
RUN gradle build -x test --no-daemon

# Stage 2: Create the final image
# Use Amazon Corretto JDK 21 as the base image for running the app
FROM amazoncorretto:21.0.5

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the default port (adjust if your app uses a different port)
EXPOSE 8080

# Set environment variable for Spring Boot profile
ENV SPRING_PROFILES_ACTIVE=prod

# Command to run the application with the specified profile
ENTRYPOINT ["java", "-jar", "app.jar"]
