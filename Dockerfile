FROM maven:3.8.3-openjdk-17-slim@sha256:9c31c89f38703a5ebe23741eb1760d88f9224b9fb5cf5e596df5549237cefd6a as build
RUN mkdir /project
COPY pom.xml /project/
COPY src /project/src
RUN --mount=type=cache,target=/root/.m2 mvn -f /project/pom.xml clean package -DskipTests

FROM arm64v8/eclipse-temurin:17.0.3_7-jre-jammy@sha256:61c5fee7a5c40a1ca93231a11b8caf47775f33e3438c56bf3a1ea58b7df1ee1b
# FROM amd64/eclipse-temurin:17.0.4.1_1-jre-jammy@sha256:30a54dca428760fb4a1e1f8333abc2e1a6f3908b5916591ae77cac94a8453bc3
COPY --from=build /project/target/smartcookbook-*-SNAPSHOT.jar app.jar
CMD "java" "-jar" "app.jar"
