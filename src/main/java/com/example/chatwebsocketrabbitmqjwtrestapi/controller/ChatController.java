package com.example.chatwebsocketkafkajwtrestapi.controller;

import com.example.chatwebsocketkafkajwtrestapi.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;



@Controller
@Slf4j
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public ChatController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/group")
    public void chat(@Payload Message message) {
        log.info("Message received: {}", message);
        simpMessagingTemplate.convertAndSendToUser(message.getTo(), "/topic", message);
    }

}
