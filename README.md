# SmartCookbook

## Cooking recipe system

This project (CRUD) was developed to demonstrate the use of some tools and frameworks, including:

- Java and Spring
  - [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
  - [Lombok](https://projectlombok.org/)
  - [SpringBoot](https://spring.io/projects/spring-boot)
  - [Spring Data](https://spring.io/projects/spring-data)
  - [Spring Cloud](https://spring.io/projects/spring-cloud)

- Unit and integration tests
  - [JUnit 5](https://junit.org/junit5/docs/current/user-guide/)
  - [Mockito](https://site.mockito.org/)
  - [Testcontainer](https://www.testcontainers.org/) - [Kafka](https://www.testcontainers.org/modules/kafka/) and [MySQL](https://www.testcontainers.org/modules/databases/mysql/)

- API documentation
  - [OpenAPI 3 - Swagger](https://springdoc.org/)

- Database Versioning
  - [Flyway](https://flywaydb.org/)

- Containerization
  - [Docker](https://docs.docker.com/)
  - [Docker Compose](https://docs.docker.com/compose/)
  
- CI/CD
  - [Github Actions](https://docs.github.com/en/actions)

The system provides REST APIs to CRUD the recipes and their ingredients.
Registration via Kafka was also made available.

To store the data, the [MySQL](https://www.mysql.com/) database was used.

To run the application it is necessary to have [Docker](https://docs.docker.com/desktop/install/linux-install/) and [Docker Compose](https://docs.docker.com/compose/install/) installed.

Just run `docker-compose` and the application and all its dependencies will be started.

```
docker-compose up -d
```

To access the API documentation (Swagger) just access the address:

```
localhost:8080/swagger-ui.html
```
