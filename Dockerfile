# ---- Stage 1: Build the JAR ----
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .

# ✅ Add execute permission to mvnw
RUN chmod +x mvnw

# ✅ Then run the build
RUN ./mvnw clean package -DskipTests

# ---- Stage 2: Run the JAR ----
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/getwarranty-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]
