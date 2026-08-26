FROM maven:3.9.6-amazoncorretto-21 AS build

WORKDIR /app
COPY .mvn .mvn
COPY mvnw mvnw
COPY pom.xml .
COPY src src

RUN ./mvnw clean package -DskipTests -Dspring.flyway.enabled=false

FROM amazoncorretto:21-alpine-jdk
COPY --from=build /app/target/gestrun-0.0.1-SNAPSHOT.jar /app/app.jar
WORKDIR /app
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
