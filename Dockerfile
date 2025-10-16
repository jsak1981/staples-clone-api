# --- Stage 1: Build Stage ---
# Use an official OpenJDK 17 image that includes Maven to build our project.
# We give this stage a name, "build", so we can reference it later.
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory inside the container.
WORKDIR /app

# Copy the Maven project object model file first. This is a key optimization.
# Docker caches layers, so if the pom.xml hasn't changed, it won't re-download dependencies.
COPY pom.xml .

# Download all project dependencies.
RUN mvn dependency:go-offline

# Copy the rest of the source code into the container.
COPY src ./src

# Build the application, creating the executable .jar file.
# We skip the tests here for a faster build; tests should be run in a separate CI step.
RUN mvn package -DskipTests




# --- Stage 2: Final Runtime Stage ---
# Use a much smaller, official OpenJDK 17 runtime image.
# This does NOT include Maven or any build tools, making our final image lean and secure.
FROM openjdk:17-jdk-slim

# Set the working directory.
WORKDIR /app

# Copy only the compiled .jar file from the "build" stage.
# This is the magic of multi-stage builds. We discard all the source code and build tools,
# resulting in a much smaller final image.
COPY --from=build /app/target/staples-clone-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the Spring Boot application will run on.
EXPOSE 8080

# The command to run when the container starts.
ENTRYPOINT ["java", "-jar", "app.jar"]
