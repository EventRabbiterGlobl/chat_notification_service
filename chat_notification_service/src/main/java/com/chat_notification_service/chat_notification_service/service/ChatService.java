package com.chat_notification_service.chat_notification_service.service;

import com.chat_notification_service.chat_notification_service.Dto.ChatMessage;
import com.chat_notification_service.chat_notification_service.Dto.ChatRoomRequest;
import com.chat_notification_service.chat_notification_service.entity.ChatMessages;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ChatService {

    String createOrOpenChatRoom(ChatRoomRequest request);

    void saveMessage(String roomName,String message);

    ChatMessages processMessage(ChatMessage chatMessage);

    List<ChatMessages> getMessagesForChatRoom(String roomName);


    List<String>  listOfSenderData(String receiverId);


}
