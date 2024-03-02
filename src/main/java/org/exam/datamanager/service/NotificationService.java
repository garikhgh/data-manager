package org.exam.datamanager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.exam.datamanager.domain.NotificationDto;
import org.exam.datamanager.domain.NotificationEntity;
import org.exam.datamanager.repository.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final MapperService mapperService;

    public void storeNotification(NotificationDto notificationDto) {
        NotificationEntity notificationEntity = mapperService.toNotificationEntity(notificationDto);
        NotificationEntity save = notificationRepository.save(notificationEntity);
        log.info("New Notification is saved: {}", save);
    }

}
