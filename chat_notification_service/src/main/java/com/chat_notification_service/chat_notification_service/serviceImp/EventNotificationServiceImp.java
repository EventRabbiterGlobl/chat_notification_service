package com.chat_notification_service.chat_notification_service.serviceImp;

import com.chat_notification_service.chat_notification_service.Dto.EventNotificationDto;
import com.chat_notification_service.chat_notification_service.Dto.NotificationId;
import com.chat_notification_service.chat_notification_service.Dto.UserProfile;
import com.chat_notification_service.chat_notification_service.entity.EventNotification;
import com.chat_notification_service.chat_notification_service.feignClient.EventUserServiceFeignClient;
import com.chat_notification_service.chat_notification_service.repo.EventNotificationRepo;
import com.chat_notification_service.chat_notification_service.service.EventNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class EventNotificationServiceImp implements EventNotificationService {


    @Autowired
    private EventNotificationRepo eventNotificationRepo;

    @Autowired
    private ModelMapper modelMapper;



    @Autowired
    private EventUserServiceFeignClient eventUserServiceFeignClient;


    @Override
    public EventNotificationDto setNotification(EventNotificationDto eventNotificationDto) {
        System.out.println("==========");
        try {
            EventNotification eventNotification = EventNotification.builder()
                    .eventPerformers(eventNotificationDto.getEventPerformers())
                    .creatorId(eventNotificationDto.getCreatorId())
                    .eventSetup(eventNotificationDto.getEventSetup())
                    .notificationType(EventNotification.NotificationType.EVENT_CREATED)
                    .build();
            EventNotification savedEventNotification = eventNotificationRepo.save(eventNotification).block();
            return modelMapper.map(savedEventNotification, EventNotificationDto.class);
        } catch (Exception e) {

            throw new RuntimeException("Failed to set notification", e);
        }
    }


    @Override
    public Flux<Tuple2<UserProfile, EventNotification>> getEventNotification(String id) {
        Flux<EventNotification> eventNotificationFlux = eventNotificationRepo.findByEventPerformers(id);

        return eventNotificationFlux.flatMap(eventNotification -> {
            String creatorId = eventNotification.getCreatorId();
            UserProfile userProfile = eventUserServiceFeignClient.getProfile(creatorId).getBody();
            assert userProfile != null;
            return Flux.just(Tuples.of(userProfile, eventNotification));
        });
    }


    @Override
    public EventNotificationDto statusSet(EventNotificationDto status) {


        System.out.println(status);
        Optional<EventNotification> optionalEventNotification = eventNotificationRepo.
                findById(status.getId()).blockOptional();

        if (optionalEventNotification.isPresent()) {
            EventNotification eventNotification = optionalEventNotification.get();
            eventNotification.setNotificationType(EventNotification.NotificationType.valueOf(status.getNotificationType()));
            eventNotificationRepo.save(eventNotification).block();
            return status;
        } else {
            return null;
        }
    }




    public List<EventNotification> conformNotification(NotificationId id) {
        Flux<EventNotification> eventNotificationFlux = eventNotificationRepo.findByEventSetup(id.getId());
        System.out.println(eventNotificationFlux);
        return eventNotificationFlux.collectList().block();
    }








}
