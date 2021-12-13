package br.com.amarques.smartcookbook.it;

import br.com.amarques.smartcookbook.SmartCookbookApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.google.common.collect.ImmutableMap;
import org.testcontainers.utility.DockerImageName;

@Slf4j
@Testcontainers
@ActiveProfiles("integration-test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SmartCookbookApplication.class)
public abstract class BaseIT {

    private static final boolean REUSE_CONTAINER = true;

    @Container
    static final KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.0.0"));

    @Container
    static final GenericContainer<?> zookeeper = new GenericContainer<>("confluentinc/cp-zookeeper:7.0.0");

    @Container
    static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.23");

    //    public static KafkaProducer<String, String> producer;
    public static KafkaConsumer<String, String> consumer;

    static {
        final var network = Network.SHARED;

        zookeeper
                .withNetwork(network)
                .withNetworkAliases("zookeeper")
                .withExposedPorts(2181)
                .withEnv("ZOOKEEPER_CLIENT_PORT", "2181")
                .withReuse(REUSE_CONTAINER)
                .start();

        kafka
                .withNetwork(network)
                .withNetworkAliases("kafka")
                .withExposedPorts(9092, 9093)
                .withExternalZookeeper("zookeeper:2181")
                .withReuse(REUSE_CONTAINER)
                .start();

        mysql
                .withNetwork(network)
                .withNetworkAliases("mysql")
                .withReuse(REUSE_CONTAINER)
                .start();
    }

    @DynamicPropertySource
    static void dynamicProperties(final DynamicPropertyRegistry registry) {
//        producer = new KafkaProducer<>(
//                ImmutableMap.of(
//                        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers(),
//                        ProducerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString()),
//                new StringSerializer(),
//                new StringSerializer());

        consumer = new KafkaConsumer<>(
                ImmutableMap.of(
                        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers(),
                        ConsumerConfig.GROUP_ID_CONFIG, "smartcookbook",
                        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"),
                new StringDeserializer(),
                new StringDeserializer());

        log.info(">>> Overriding Spring Properties for Kafka <<< " + kafka.getBootstrapServers());

        registry.add("spring.cloud.stream.kafka.binder.brokers", kafka::getBootstrapServers);

        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

}
