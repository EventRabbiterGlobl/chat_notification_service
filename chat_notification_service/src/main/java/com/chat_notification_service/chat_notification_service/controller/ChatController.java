package com.chat_notification_service.chat_notification_service.controller;

import com.chat_notification_service.chat_notification_service.Dto.ChatMessage;
import com.chat_notification_service.chat_notification_service.Dto.ChatRoomRequest;
import com.chat_notification_service.chat_notification_service.entity.ChatMessages;
import com.chat_notification_service.chat_notification_service.entity.MessageEntity;
import com.chat_notification_service.chat_notification_service.entity.UserData;
import com.chat_notification_service.chat_notification_service.repo.UserRepo;
import com.chat_notification_service.chat_notification_service.service.ChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.*;

@RestController
public class ChatController {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;


    @Autowired
    private ChatService chatService;


    @Autowired
    private UserRepo userRepo;

    @MessageMapping("/sendAll")
    @SendTo("/all/messages")
    public String sendToAll(String message) {
        System.out.println("Group message: " + message);
        return message;
    }



    @MessageMapping("/create-chatRoom")
    public void createOrOpenChatRoom(@Payload ChatRoomRequest request, SimpMessageHeaderAccessor headerAccessor) {
        System.out.println(request);
        String roomName = chatService.createOrOpenChatRoom(request);
        List<ChatMessages> messages = chatService.getMessagesForChatRoom(roomName);
        System.out.println(roomName + "name");
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        accessor.setSessionId(headerAccessor.getSessionId());
        accessor.setLeaveMutable(true);

        Map<String,Object> messagePayload = new HashMap<>();
        messagePayload.put("roomName",roomName);
        messagePayload.put("messages", messages);



        simpMessagingTemplate.convertAndSendToUser(
                Objects.requireNonNull(headerAccessor.getSessionId()),
                "/queue/messages",
                messagePayload,
                accessor.getMessageHeaders()
        );

    }


    @MessageMapping("/chat")
    public void handleChatMessage(@Payload ChatMessage chatMessage) throws JsonProcessingException {
        ChatMessages chatMessages  =  chatService.processMessage(chatMessage);
        System.out.println(chatMessages+"kk");
        System.out.println(chatMessage.getChatRoomName());
        String jsonMessage = new ObjectMapper().writeValueAsString(chatMessages);
        simpMessagingTemplate.convertAndSend("/topic/" + chatMessages.getChatRoomName(), jsonMessage);
    }


















}
