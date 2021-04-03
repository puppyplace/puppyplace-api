# This file is a template, and might need editing before it works on your project.
FROM maven:3.5-jdk-11 as BUILD

COPY . /usr/src/app
RUN mvn --batch-mode -f /usr/src/app/pom.xml clean package

FROM openjdk:11-jdk
COPY --from=BUILD /usr/src/app/target/backend-0.0.1-SNAPSHOT.jar backend.jar

ENTRYPOINT ["java","-jar","backend.jar"]
