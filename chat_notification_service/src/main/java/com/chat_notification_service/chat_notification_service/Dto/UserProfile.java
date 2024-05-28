package com.chat_notification_service.chat_notification_service.Dto;

import lombok.*;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {


    private UUID id;
    private String firstName;
    private String secondName;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String bio;
    private String description;
    private boolean profileVerification;
    private double location;
    private double locationLongitude;
    private String nameOfPlace;
    private String imageUrl;
    private String imagePublicId;
    private boolean activated;
    private boolean deleted;
    private String listOfCategory;
    private String gatherGrove;
    private String individualCategory;
    private String teamCategory;
}