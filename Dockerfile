FROM maven:3.8-openjdk-11-slim as builder
ADD . /app
WORKDIR /app
RUN ls -l
RUN mvn clean package -Dmaven.test.skip=true