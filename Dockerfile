# Use an official Maven image as the base image
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Build the Maven project
RUN mvn clean install

# Use a lightweight base image for the final image
FROM openjdk:23-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built artifact from the build stage
COPY --from=build /app/target/communication-1.jar .
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "communication-1.jar"]
