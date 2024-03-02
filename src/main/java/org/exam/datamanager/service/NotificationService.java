package org.exam.datamanager.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exam.datamanager.domain.NotificationDto;
import org.exam.datamanager.domain.NotificationEntity;
import org.exam.datamanager.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final MapperService mapperService;
    private final ObjectMapper objectMapper;

    public void storeNotification(JsonNode jsonNode) throws IOException {
        NotificationDto notificationDto = objectMapper.readerFor(NotificationDto.class).readValue(jsonNode);
        NotificationEntity notificationEntity = mapperService.toNotificationEntity(notificationDto);
        NotificationEntity save = notificationRepository.save(notificationEntity);
        log.info("New Notification is saved: {}", save);
    }

}
