package com.chat_notification_service.chat_notification_service.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Data
@Builder
@Document
@AllArgsConstructor
@NoArgsConstructor

public class ChatRoom {
    @Id
    private String id;
    private String chatId;
    private String senderId;
    private String recipientId;
    private String companyId;
    private String chatRoomName;
}
