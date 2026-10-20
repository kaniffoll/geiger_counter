FROM gradle:8-jdk17 AS build
WORKDIR /src
COPY build.gradle* settings.gradle* gradle.properties* ./
COPY gradle ./gradle
RUN gradle dependencies --no-daemon > /dev/null 2>&1 || true
COPY src ./src
RUN gradle bootJar --no-daemon -x test

FROM eclipse-temurin:17-jre
RUN useradd --system app
USER app
WORKDIR /app
COPY --from=build /src/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
