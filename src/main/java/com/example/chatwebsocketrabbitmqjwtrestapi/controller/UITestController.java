package com.example.chatwebsocketrabbitmqjwtrestapi.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UITestController {
    @GetMapping("/")
    public String welcome(){
        return "index";
    }
}
