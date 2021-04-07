FROM maven:3.8-openjdk-11-slim as builder
ADD . /app
WORKDIR /app
RUN ls -l
RUN mvn clean install

FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine
VOLUME /tmp
COPY --from=builder "/app/target/smartcookbook-*-SNAPSHOT.jar" app.jar
CMD java $JAVA_OPTS -jar app.jar