package com.example.chatwebsocketrabbitmqjwtrestapi.service;

import com.example.chatwebsocketrabbitmqjwtrestapi.dto.ChatUser;
import com.example.chatwebsocketrabbitmqjwtrestapi.dto.SignIn;
import com.example.chatwebsocketrabbitmqjwtrestapi.dto.SignUp;

public interface AuthService {
    ChatUser signin(SignIn signIn);
    String signUp(SignUp singUp);
    String update(SignUp signUp, String token);
    String delete(String usernameOrEmail, String token);
}
