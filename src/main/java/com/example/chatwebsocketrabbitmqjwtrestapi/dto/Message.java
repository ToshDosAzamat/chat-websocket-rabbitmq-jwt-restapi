package com.example.chatwebsocketkafkajwtrestapi.dto;


import lombok.Data;

@Data
public class Message {
    private String to;
    private String message;
    private String from;
}
