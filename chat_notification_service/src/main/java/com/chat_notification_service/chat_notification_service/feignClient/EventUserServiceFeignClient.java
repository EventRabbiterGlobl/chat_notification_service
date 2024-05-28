package com.chat_notification_service.chat_notification_service.feignClient;


import com.chat_notification_service.chat_notification_service.Dto.UserProfile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("USER-SERVICE")
public interface EventUserServiceFeignClient {

    @GetMapping("api/v1/user/get/userProfile/{uuid}")
    ResponseEntity<UserProfile> getProfile(@PathVariable String uuid);


}
