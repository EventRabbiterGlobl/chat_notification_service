package com.chat_notification_service.chat_notification_service.repo;

import com.chat_notification_service.chat_notification_service.entity.ChatEntity;
import com.chat_notification_service.chat_notification_service.entity.UserData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.List;
import java.util.UUID;

public interface ChatDAO extends ReactiveMongoRepository<ChatEntity, Long> {

}
