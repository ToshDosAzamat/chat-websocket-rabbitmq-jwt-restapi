package com.example.chatwebsocketrabbitmqjwtrestapi.dto;


import lombok.Data;

@Data
public class SignUp {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
}
