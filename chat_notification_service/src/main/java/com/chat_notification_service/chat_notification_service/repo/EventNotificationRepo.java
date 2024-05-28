package com.chat_notification_service.chat_notification_service.repo;

import com.chat_notification_service.chat_notification_service.entity.EventNotification;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface EventNotificationRepo extends ReactiveMongoRepository<EventNotification,String> {

    @Query("{ 'eventPerformers' : ?0 }")
    Flux<EventNotification> findByEventPerformers(String id);


    @Query("{ 'eventSetup' : ?0 }")
    Flux<EventNotification> findByEventSetup(String eventSetupId);

}
