package org.exam.datamanager.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exam.datamanager.domain.NotificationDto;
import org.exam.datamanager.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "notification", containerFactory = "kafkaListenerContainerFactory")
    public void notificationListener(JsonNode notificationDto) throws IOException {
        log.info("New Notification received {}", notificationDto.toString());
        notificationService.storeNotification(notificationDto);
    }
}
