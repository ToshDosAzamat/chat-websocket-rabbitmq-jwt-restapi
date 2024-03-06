package com.example.chatwebsocketkafkajwtrestapi.controller;


import com.example.chatwebsocketkafkajwtrestapi.component.JwtTokenProvider;
import com.example.chatwebsocketkafkajwtrestapi.exception.NotFoundException;
import com.example.chatwebsocketkafkajwtrestapi.model.User;
import com.example.chatwebsocketkafkajwtrestapi.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jwt")
public class JwtTestController {
    private JwtTokenProvider jwtTokenProvider;
    private UserRepository userRepository;

    public JwtTestController(JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @GetMapping("/get/{token}")
    public ResponseEntity<String> getFromToken(@PathVariable String token){
        String username = jwtTokenProvider.getUsername(token);
        return new ResponseEntity<>(username, HttpStatus.OK);
    }
}
