# Use the Azul Zulu OpenJDK 17 Alpine image
FROM azul/zulu-openjdk-alpine:17.0.6-17.40.19

# Set the working directory in the container
WORKDIR /app

# Copy the compiled JAR file into the container
COPY target/banking-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port that your Spring Boot application will run on
EXPOSE ${SERVER_PORT}

# Define the command to run your application
CMD ["java", "-jar", "/app/app.jar"]
