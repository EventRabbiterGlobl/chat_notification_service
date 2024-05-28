package com.chat_notification_service.chat_notification_service.repo;

import com.chat_notification_service.chat_notification_service.entity.ChatEntity;
import com.chat_notification_service.chat_notification_service.entity.MessageEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.List;

public interface MessageDAO extends ReactiveMongoRepository<MessageEntity, Long> {

}
