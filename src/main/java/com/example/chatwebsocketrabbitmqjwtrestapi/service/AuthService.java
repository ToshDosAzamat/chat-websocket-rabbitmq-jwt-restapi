package com.example.chatwebsocketkafkajwtrestapi.service;

import com.example.chatwebsocketkafkajwtrestapi.dto.ChatUser;
import com.example.chatwebsocketkafkajwtrestapi.dto.SignIn;
import com.example.chatwebsocketkafkajwtrestapi.dto.SignUp;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService {
    ChatUser signin(SignIn signIn);
    String signUp(SignUp singUp);
    String update(SignUp signUp, String token);
    String delete(String usernameOrEmail, String token);
}
