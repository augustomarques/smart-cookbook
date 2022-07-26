FROM maven:3.8.3-openjdk-17-slim as builder
ADD . /app
WORKDIR /app
RUN ls -l
RUN mvn clean package

FROM arm64v8/eclipse-temurin:17.0.3_7-jre-jammy
VOLUME /tmp
COPY --from=builder "/app/target/smartcookbook-*-SNAPSHOT.jar" app.jar
ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/.urandom", "-jar", "/app.jar" ]