FROM maven:3.8-openjdk-11-slim as builder
ADD . /app
WORKDIR /app
RUN ls -l
RUN mvn clean package

FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine
VOLUME /tmp
EXPOSE 8080
COPY --from=builder "/app/target/smartcookbook-*-SNAPSHOT.jar" app.jar
ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/.urandom", "-jar", "/app.jar" ]