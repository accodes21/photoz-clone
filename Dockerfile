# Use Maven with Java 17
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Set working directory inside container
WORKDIR /app

# Copy project files
COPY . .

# Build the application
RUN mvn clean package -DskipTests

# Use a lightweight JDK 17 runtime for the final image
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]