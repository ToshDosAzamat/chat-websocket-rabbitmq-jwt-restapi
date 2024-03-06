package com.example.chatwebsocketrabbitmqjwtrestapi.controller;


import com.example.chatwebsocketrabbitmqjwtrestapi.dto.ChatUser;
import com.example.chatwebsocketrabbitmqjwtrestapi.dto.SignIn;
import com.example.chatwebsocketrabbitmqjwtrestapi.dto.SignUp;
import com.example.chatwebsocketrabbitmqjwtrestapi.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<ChatUser> loginIn(@RequestBody SignIn signIn){
        return new ResponseEntity<>(authService.signin(signIn), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> loginUp(@RequestBody SignUp signUp){
        return new ResponseEntity<>(authService.signUp(signUp), HttpStatus.CREATED);
    }

    @PostMapping("/update/{token}")
    public ResponseEntity<String> updateLogin(@RequestBody SignUp signUp, @PathVariable String token){
        return new ResponseEntity<>(authService.update(signUp, token), HttpStatus.CREATED);
    }

    @PostMapping("/delete/{username}/{token}")
    public ResponseEntity<String> deleteAccount(@PathVariable String usernameOrEmail, @PathVariable String token){
        return new ResponseEntity<>(authService.delete(usernameOrEmail, token), HttpStatus.OK);
    }
}
