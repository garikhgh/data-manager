package org.exam.datamanager.domain;


import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NotificationDto implements Serializable {

    private String description;

    private List<String> stringList;

}
