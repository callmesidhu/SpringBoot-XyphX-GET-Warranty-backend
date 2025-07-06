# Start from an OpenJDK image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy built jar from target/
COPY target/getwarranty-0.0.1-SNAPSHOT.jar app.jar

# Expose port (Render will set $PORT automatically)
EXPOSE 8080

# Run the app (Render sets PORT env, use it if available)
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]
