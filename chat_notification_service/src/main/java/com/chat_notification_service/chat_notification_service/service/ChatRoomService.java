package com.chat_notification_service.chat_notification_service.service;

import com.chat_notification_service.chat_notification_service.Dto.ChatRoomRequest;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.Optional;

public interface ChatRoomService {

    Optional<String> getChatId(
            String senderId, String recipientId, boolean createIfNotExist);

}
