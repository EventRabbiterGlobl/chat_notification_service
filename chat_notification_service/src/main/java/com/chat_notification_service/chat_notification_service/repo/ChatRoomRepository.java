package com.chat_notification_service.chat_notification_service.repo;

import com.chat_notification_service.chat_notification_service.entity.ChatRoom;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface ChatRoomRepository extends ReactiveMongoRepository<ChatRoom,String> {

    Optional<ChatRoom> findBySenderIdAndRecipientId(String senderId, String recipientId);

    Mono<Boolean> existsByChatRoomName(String chatRoomName);

}
