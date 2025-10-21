#
# Build stage
#
FROM gradle:8.14.3-jdk21 AS build
WORKDIR /app

# Copy the Gradle project files
COPY --chown=gradle:gradle . .

# Build the Spring Boot jar (using the bootJar task)
RUN gradle clean bootJar --no-daemon

#
# Package stage
#
FROM openjdk:21-jdk-slim
WORKDIR /app

# Copy the built jar from Gradle build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose application port
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]
