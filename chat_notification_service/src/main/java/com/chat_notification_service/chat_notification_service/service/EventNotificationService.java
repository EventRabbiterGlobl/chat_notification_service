package com.chat_notification_service.chat_notification_service.service;

import com.chat_notification_service.chat_notification_service.Dto.EventNotificationDto;
import com.chat_notification_service.chat_notification_service.Dto.NotificationId;
import com.chat_notification_service.chat_notification_service.Dto.UserProfile;
import com.chat_notification_service.chat_notification_service.entity.EventNotification;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;

public interface EventNotificationService {


    EventNotificationDto setNotification(EventNotificationDto eventNotification);

    Flux<Tuple2<UserProfile, EventNotification>> getEventNotification(String id);

    EventNotificationDto statusSet(EventNotificationDto status);


    List<EventNotification> conformNotification(NotificationId id);

}
