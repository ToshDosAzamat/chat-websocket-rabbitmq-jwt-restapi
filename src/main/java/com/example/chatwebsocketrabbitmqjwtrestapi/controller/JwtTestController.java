package com.example.chatwebsocketrabbitmqjwtrestapi.controller;


import com.example.chatwebsocketrabbitmqjwtrestapi.component.JwtTokenProvider;
import com.example.chatwebsocketrabbitmqjwtrestapi.repository.UserRepository;
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
