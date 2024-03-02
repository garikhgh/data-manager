package org.exam.datamanager.service;

import javax.annotation.processing.Generated;
import org.exam.datamanager.domain.NotificationDto;
import org.exam.datamanager.domain.NotificationEntity;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-02T19:22:03+0400",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.5.jar, environment: Java 17.0.8 (GraalVM Community)"
)
@Component
public class MapperServiceImpl implements MapperService {

    @Override
    public NotificationDto toNotificationDto(NotificationEntity notification) {
        if ( notification == null ) {
            return null;
        }

        NotificationDto notificationDto = new NotificationDto();

        notificationDto.setDescription( notification.getDescription() );

        return notificationDto;
    }

    @Override
    public NotificationEntity toNotificationEntity(NotificationDto notificationDto) {
        if ( notificationDto == null ) {
            return null;
        }

        NotificationEntity notificationEntity = new NotificationEntity();

        notificationEntity.setDescription( notificationDto.getDescription() );

        return notificationEntity;
    }
}
