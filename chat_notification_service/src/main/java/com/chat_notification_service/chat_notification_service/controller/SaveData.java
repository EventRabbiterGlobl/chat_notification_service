package com.chat_notification_service.chat_notification_service.controller;


import com.chat_notification_service.chat_notification_service.entity.UserData;
import com.chat_notification_service.chat_notification_service.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
public class SaveData {



    @Autowired
    private UserRepo userRepo;

    @PostMapping("/saveData")
    public UserData postData(@RequestBody UserData userData){
        UserData userData1=new UserData();
        userData1.setId(userData.getId());
        userData1.setUsername(userData.getUsername());
        return userRepo.save(userData1).block();
    }

    @GetMapping("/getAllData")
    public Flux<UserData> getAllData() {
        return userRepo.findAll();
    }

    @GetMapping("/getDataById")
    public Mono<UserData> getDataById(@RequestParam String id) {
        UUID user= UUID.fromString(id);
        return userRepo.findById(user);
    }


}
