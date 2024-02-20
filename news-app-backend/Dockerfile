# Use the official Maven image to create a build artifact
FROM maven:3.6.3-jdk-11 as builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests

# Use OpenJDK for running the application
FROM openjdk:11-jre-slim
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
