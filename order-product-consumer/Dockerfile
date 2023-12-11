# build
FROM gradle:8.4-jdk11 as builder
WORKDIR /build
COPY --chown=gradle:gradle src /build/src
COPY --chown=gradle:gradle build.gradle settings.gradle ./gradlew /build/
RUN gradle --no-daemon build -x test && gradle bootJar

# runtime
FROM openjdk:11 as runtime
WORKDIR /app
COPY --from=builder /build/build/libs/*.jar app.jar
ENTRYPOINT java -jar app.jar