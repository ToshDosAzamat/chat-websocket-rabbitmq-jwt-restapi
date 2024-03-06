package com.example.chatwebsocketrabbitmqjwtrestapi.dto;


import lombok.Data;

@Data
public class SignIn {
    private String email;
    private String password;
}
