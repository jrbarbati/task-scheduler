# syntax=docker/dockerfile:1.6

# ---------- Build stage ----------
FROM eclipse-temurin:25-jdk AS build

WORKDIR /workspace

# Copy Gradle wrapper + build scripts first for layer caching
COPY gradlew gradlew.bat settings.gradle build.gradle gradle.properties ./
COPY gradle ./gradle

# Prime the Gradle cache (downloads wrapper + dependencies before sources change)
RUN ./gradlew --no-daemon dependencies || true

# Copy sources and build
COPY src ./src
RUN ./gradlew --no-daemon build -x test

# ---------- Runtime stage ----------
FROM eclipse-temurin:25-jre

ENV LANG=C.UTF-8 \
    JAVA_OPTS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"

WORKDIR /deployments

# Quarkus fast-jar layout: lib/ + quarkus-run.jar + app/ + quarkus/
COPY --from=build /workspace/build/quarkus-app/lib/      ./lib/
COPY --from=build /workspace/build/quarkus-app/*.jar     ./
COPY --from=build /workspace/build/quarkus-app/app/      ./app/
COPY --from=build /workspace/build/quarkus-app/quarkus/  ./quarkus/

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /deployments/quarkus-run.jar"]
