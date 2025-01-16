FROM openjdk:17-jdk-slim-buster
MAINTAINER lprada
COPY backend/target/fire-calls-1.0-SNAPSHOT.jar fire-calls-1.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/fire-calls-1.0-SNAPSHOT.jar"]
