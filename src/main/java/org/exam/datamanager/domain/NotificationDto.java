package org.exam.datamanager.domain;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NotificationDto implements Serializable {

    private String description;

}
