# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the executable JAR file into the container at /app
COPY target/series-ranking-0.0.1-SNAPSHOT.jar app.jar

# Make port 8081 available to the world outside this container
EXPOSE 8081

# Define a volume for the H2 database data
VOLUME /app/data

# Run the jar file
ENTRYPOINT ["java","-jar","app.jar"]
