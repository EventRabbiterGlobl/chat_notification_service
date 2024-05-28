package com.chat_notification_service.chat_notification_service.serviceImp;

import com.chat_notification_service.chat_notification_service.Dto.ChatMessage;
import com.chat_notification_service.chat_notification_service.Dto.ChatRoomRequest;
import com.chat_notification_service.chat_notification_service.entity.ChatMessages;
import com.chat_notification_service.chat_notification_service.entity.ChatRoom;
import com.chat_notification_service.chat_notification_service.repo.ChatMessagesRepository;
import com.chat_notification_service.chat_notification_service.repo.ChatRoomRepository;
import com.chat_notification_service.chat_notification_service.service.ChatService;
import org.apache.http.client.UserTokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatServiceImp implements ChatService {

    @Autowired
    ChatRoomRepository chatRoomRepository;

    @Autowired
    ChatMessagesRepository chatMessageRepository;

    @Override
    public String createOrOpenChatRoom(ChatRoomRequest request) {
        String chatRoomName = createChatRoomWithUniqueName(request.getSenderId(), request.getReceiverId());
        if (!chatRoomExists(chatRoomName)) {
            createChatRoom(chatRoomName, request.getSenderId(), request.getReceiverId());
        } else {
            openChatRoom(chatRoomName);
        }
        return chatRoomName;
    }

    @Override
    public void saveMessage(String roomName, String message) {
        System.out.println(roomName);
    }

    @Override
    public ChatMessages processMessage(ChatMessage chatMessage) {
        String chatRoomName = chatMessage.getChatRoomName();
        String[] ids = chatRoomName.split("/");
        if (ids.length == 2) {
            String senderId = ids[0];
            String receiverId = ids[1];
            System.out.println(senderId + "senderId");
            System.out.println(receiverId + "receiverId");
            ChatMessages chatMessages = ChatMessages.builder()
                    .chatRoomName(chatMessage.getChatRoomName())
                    .content(chatMessage.getMessage())
                    .senderId(chatMessage.getSenderId())
                    .recipientId(receiverId)
                    .build();
            ChatMessages saveDat = chatMessageRepository.save(chatMessages).block();
            System.out.println(saveDat+"---------------------------------------");
          return saveDat;
        } else {
            return null;
        }
    }

    @Override
    public List<ChatMessages> getMessagesForChatRoom(String roomName) {
        return chatMessageRepository.findByChatRoomName(roomName).collectList().block();
    }

    @Override
    public List<String> listOfSenderData(String id) {

        Flux<ChatMessages> chatMessagesFlux = chatMessageRepository.findAll();


        Mono<Set<String>> chatRoomNamesList=chatMessagesFlux
                .map(ChatMessages::getChatRoomName)
                .distinct()
                .collect(Collectors.toSet());


        return chatRoomNamesList
                .flatMap(set -> Flux.fromIterable(set)
                        .filter(room -> {
                            String[] parts = room.split("/");
                            return parts.length == 2 && (parts[0].equals(id) || parts[1].equals(id));
                        })
                        .map(room -> {
                            String[] parts = room.split("/");
                            return parts[0].equals(id) ? parts[1] : parts[0];
                        })
                        .collect(Collectors.toList()))
                .block();
    }











    private String createChatRoomWithUniqueName(String senderId, String receiverId) {
        String[] idArray = {senderId, receiverId};
        Arrays.sort(idArray);
        String firstId = idArray[0];
        String secondId = idArray[1];
        return firstId + "/" + secondId;
    }

    private boolean chatRoomExists(String chatRoomName) {
        return Boolean.TRUE.equals(chatRoomRepository.existsByChatRoomName(chatRoomName).block());
    }

    private void openChatRoom(String chatRoomName) {
        System.out.println("opened" + chatRoomName);
    }

    private void createChatRoom(String chatRoomName, String senderId, String receiverId) {
        ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomName(chatRoomName)
                .senderId(senderId)
                .recipientId(receiverId)
                .build();
        chatRoomRepository.save(chatRoom);
    }

    private static List<String> extractSenderIdsFromChatRoomNames(Set<String> chatRoomNames) {
        List<String> senderIds = new ArrayList<>();
        for (String chatRoomName : chatRoomNames) {
            senderIds.add(extractSenderIdFromChatRoomName(chatRoomName));
        }
        return senderIds;
    }

    private static String extractSenderIdFromChatRoomName(String chatRoomName) {
        String[] idArray = chatRoomName.split("/");
        return idArray[0];
    }




}
