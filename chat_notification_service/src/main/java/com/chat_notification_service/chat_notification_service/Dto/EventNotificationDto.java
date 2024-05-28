package com.chat_notification_service.chat_notification_service.Dto;


import com.chat_notification_service.chat_notification_service.entity.EventNotification;
import com.mongodb.annotations.NotThreadSafe;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventNotificationDto {



    private String id;
    private String eventPerformers;
    private String creatorId;
    private String membersId;
    private String notificationType;
    private String eventSetup;


}
