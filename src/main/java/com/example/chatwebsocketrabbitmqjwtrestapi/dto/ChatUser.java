package com.example.chatwebsocketrabbitmqjwtrestapi.dto;


import lombok.Data;

@Data
public class ChatUser {
    private String token;
    private String username;
}
