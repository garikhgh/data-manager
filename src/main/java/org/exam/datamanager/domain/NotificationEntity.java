package org.exam.datamanager.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class NotificationEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String description;
    @CreationTimestamp
    private Instant createdAt;

}
