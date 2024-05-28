package com.chat_notification_service.chat_notification_service.Dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatMessage {
    String chatRoomName;
    String message;
    String receiverId;
    String senderEmail;
    String senderId;

    @JsonCreator
    public ChatMessage(@JsonProperty("chatRoomName") String chatRoomName,
                       @JsonProperty("message") String message,
                       @JsonProperty("receiverId") String receiverId,
                       @JsonProperty("senderId") String senderId) {
        this.chatRoomName = chatRoomName;
        this.message = message;
        this.receiverId = receiverId;
        this.senderId = senderId;


    }
}