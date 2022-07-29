# SmartCookbook

This project was developed to demonstrate the use of some tools and frameworks, including:

- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [SpringBoot](https://spring.io/projects/spring-boot)
- [Spring Data](https://spring.io/projects/spring-data)
- [Spring Cloud](https://spring.io/projects/spring-cloud)
- [JUnit 5](https://junit.org/junit5/docs/current/user-guide/)
- [Testcontainer](https://www.testcontainers.org/)
- [OpenAPI 3 - Swagger](https://springdoc.org/)
- [Flyway](https://flywaydb.org/)
- [Lombok](https://projectlombok.org/)

The system provides REST APIs to CRUD the recipes and their ingredients.
Registration via Kafka was also made available.

To store the data, the [MySQL](https://www.mysql.com/) database was used.

To run the application it is necessary to have [Docker](https://www.docker.com/) installed.

Just run `docker-compose` and the application and all its dependencies will be started.

```
docker-compose up -d
```

To access the API documentation (Swagger) just access the address:

```
localhost:8080/swagger-ui.html
```
