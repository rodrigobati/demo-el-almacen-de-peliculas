# Etapa de build
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

COPY src ./src
RUN mvn -q -DskipTests package

# Etapa de runtime
FROM eclipse-temurin:21-jre
WORKDIR /app

ARG JAR_FILE=target/rating-0.0.1-SNAPSHOT.jar

# Copiamos el jar desde la imagen de build
COPY --from=build /app/${JAR_FILE} app.jar

# Si tu app escucha en 8082:
EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
