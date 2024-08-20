# Description: Dockerfile for building the application
# This is a multi-stage build Dockerfile
# The first stage is to build the application
# The second stage is to run the application

# First stage uses maven image to build the application
FROM maven:latest as build

# WORKDIR sets the working directory for any RUN, CMD,
# ENTRYPOINT, COPY and ADD instructions that follow it in the Dockerfile
WORKDIR /app

# Copy the pom.xml file to the working directory
COPY pom.xml .

# Copy the source code to the working directory
COPY src ./src

# Run the maven build and skip the tests
RUN mvn clean install package -DskipTests

# Second stage uses openjdk image to run the application
FROM openjdk:21-slim

# Copy the jar file from the first stage to the working directory
COPY --from=build /app/target/*.jar /app/app.jar

# Expose port 8000
EXPOSE 8000

# Run the application
CMD ["java", "-jar", "/app/app.jar"]