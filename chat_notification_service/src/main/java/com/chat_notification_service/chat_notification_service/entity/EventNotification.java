package com.chat_notification_service.chat_notification_service.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class EventNotification {

    @Id
    private String id;
    private String eventPerformers;
    private String creatorId;
    private NotificationType notificationType;
    private String eventSetup;



    public enum NotificationType {
        EVENT_CREATED,
        EVENT_REJECTED,
        EVENT_CONFORM
    }








}
