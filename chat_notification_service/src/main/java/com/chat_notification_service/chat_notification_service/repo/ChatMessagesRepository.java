package com.chat_notification_service.chat_notification_service.repo;

import com.chat_notification_service.chat_notification_service.entity.ChatMessages;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.List;


public interface ChatMessagesRepository extends ReactiveMongoRepository<ChatMessages,String> {

    Flux<ChatMessages> findByChatRoomName(String chatRoomName);
    Flux<ChatMessages> findByChatRoomName();

    Flux<ChatMessages> findBySenderId(String id);


}
