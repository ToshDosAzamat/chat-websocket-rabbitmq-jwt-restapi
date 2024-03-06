package com.example.chatwebsocketrabbitmqjwtrestapi.controller;

import com.example.chatwebsocketrabbitmqjwtrestapi.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.print.attribute.standard.Destination;


@Controller
@Slf4j
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public ChatController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }
    @MessageMapping("/public/{destination}")
    public void publicmessage(@Payload Message message,
                              @DestinationVariable String destination){
        log.info("Message public: {}",message);
        simpMessagingTemplate.convertAndSend(destination, message);
    }
    @MessageMapping("/group/{destination}")
    public void groupmessage(@Payload Message message,
                             @DestinationVariable String destination){
        simpMessagingTemplate.convertAndSend(destination, message);
    }
    @MessageMapping("/user/{destination}")
    public void usermessage(@Payload Message message,
                            @DestinationVariable String destination){
        simpMessagingTemplate.convertAndSendToUser(message.getTo(), destination, message);
    }
    @MessageMapping("/group")
    public void chat(@Payload Message message) {
        log.info("Message received: {}", message);
        simpMessagingTemplate.convertAndSendToUser(message.getTo(), "/topic", message);
    }

}
