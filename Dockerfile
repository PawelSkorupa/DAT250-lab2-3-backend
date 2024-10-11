# Stage 1: Build the application
FROM gradle:8.8-jdk-21-and-22 AS build

WORKDIR /app

COPY build.gradle.kts settings.gradle.kts gradlew /app/

COPY src /app/src

RUN gradle bootJar --no-daemon

# Stage 2: Create the final image
FROM eclipse-temurin:21-jre AS runtime

WORKDIR /app

RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring

COPY --from=build /app/build/libs/*.jar /app/app.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/app.jar"]
