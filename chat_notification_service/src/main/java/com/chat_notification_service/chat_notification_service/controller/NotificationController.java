package com.chat_notification_service.chat_notification_service.controller;


import com.chat_notification_service.chat_notification_service.Dto.EventNotificationDto;
import com.chat_notification_service.chat_notification_service.Dto.NotificationId;
import com.chat_notification_service.chat_notification_service.Dto.UserProfile;
import com.chat_notification_service.chat_notification_service.entity.EventNotification;
import com.chat_notification_service.chat_notification_service.service.ChatService;
import com.chat_notification_service.chat_notification_service.service.EventNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import javax.print.FlavorException;
import java.util.List;

@RestController
@RequestMapping("api/v1/notification-chat")
public class NotificationController {


    @Autowired
    private ChatService chatService;




    @Autowired
    private EventNotificationService eventNotificationService;


    @PostMapping("/set/event/notification")
    public ResponseEntity<EventNotificationDto> setEventNotification(@RequestBody EventNotificationDto eventNotificationDto){
        return ResponseEntity.ok().body(eventNotificationService.setNotification(eventNotificationDto));
    }

    @GetMapping("/get/Notification/{userId}")
    public ResponseEntity<Flux<Tuple2<UserProfile, EventNotification>>> getNotification(@PathVariable String userId){
        return  ResponseEntity.ok().body(eventNotificationService.getEventNotification(userId));

    }

    @PostMapping("/event/notification/reaction")
    public ResponseEntity<EventNotificationDto> eventStatus(@RequestBody EventNotificationDto eventNotificationDto){
        return ResponseEntity.ok().body(eventNotificationService.statusSet(eventNotificationDto));

    }

    @PostMapping("/notification/status")
    public ResponseEntity<Flux<EventNotification>> conformation(@RequestBody NotificationId userId){
        List<EventNotification> notificationsList = eventNotificationService.conformNotification(userId);
        Flux<EventNotification> notifications = Flux.fromIterable(notificationsList);
        return ResponseEntity.ok().body(notifications);
    }

    @GetMapping("/list-of-sender/{id}")
    public List<String> listOfSenderId(@PathVariable String id){
        List<String> data= chatService.listOfSenderData(id);
        return ResponseEntity.ok().body(data).getBody();
    }




}
