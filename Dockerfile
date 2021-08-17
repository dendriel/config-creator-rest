FROM gradle:7.0.2-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:11.0.4-jre-slim

EXPOSE 8081

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/config-creator-rest.jar

ENTRYPOINT ["java", "-jar", "/app/config-creator-rest.jar"]
