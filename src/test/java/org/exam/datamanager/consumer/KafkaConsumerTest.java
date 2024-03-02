package org.exam.datamanager.consumer;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.exam.datamanager.DataManagerApplication;
import org.exam.datamanager.config.KafkaConsumerConfig;
import org.exam.datamanager.domain.NotificationEntity;
import org.exam.datamanager.repository.NotificationRepository;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@Import({KafkaConsumerConfig.class, KafkaConsumerTest.KafkaProducerConfig.class})
@DirtiesContext
@SpringBootTest(classes = DataManagerApplication.class)
class KafkaConsumerTest {

    private static final String NOTIFICATION_TOPIC = "notification";

    @Autowired
    private NotificationRepository notificationRepository;

    @ClassRule
    public static KafkaContainer kafka =
            new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    @Autowired
    private KafkaTemplate<String, Serializable> kafkaTemplate;

    @Autowired
    private KafkaConsumer kafkaConsumer;

    @Test
    void testKafkaConsumer() throws InterruptedException {

        String n = "{\"description\":\"test notification\"}";
        sleep(1000);
        kafkaTemplate.send(NOTIFICATION_TOPIC, n);
        sleep(4000);
        List<NotificationEntity> all = notificationRepository.findAll();

        assertEquals("test notification", all.get(0).getDescription());
    }

    @TestConfiguration
    static class KafkaProducerConfig {
        @Value(value = "${kafka.bootstrapAddress}")
        private String bootstrapAddress;

        @Bean
        public ProducerFactory<String, Serializable> producerFactory() {
            Map<String, Object> configProps = new HashMap<>();
            configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
            configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
            configProps.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, "20971520");
            return new DefaultKafkaProducerFactory<>(configProps, new StringSerializer(), new JsonSerializer<>());
        }

        @Bean
        KafkaTemplate<String, Serializable> jsonKafkaTemplate(ProducerFactory<String, Serializable> jsonProducerFactory) {
            return new KafkaTemplate<>(jsonProducerFactory);
        }

        @Bean
        public KafkaAdmin kafkaAdmin() {
            Map<String, Object> configs = new HashMap<>();
            configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
            return new KafkaAdmin(configs);
        }

        @Bean
        public NewTopic productTopic() {
            return new NewTopic("notification", 1, (short) 1);
        }
    }

}