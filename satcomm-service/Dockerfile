FROM maven:3.9-eclipse-temurin-11-alpine

RUN mkdir -p /home/app

COPY . /home/app

WORKDIR /home/app

RUN mvn package

ENTRYPOINT ["java", "-cp", "target/RESTSample-1.0.1.jar", "Main"]
