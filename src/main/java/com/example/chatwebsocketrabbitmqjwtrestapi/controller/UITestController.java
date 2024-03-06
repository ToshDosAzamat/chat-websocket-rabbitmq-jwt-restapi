package com.example.chatwebsocketkafkajwtrestapi.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UITestController {
    @GetMapping("/")
    public String welcome(){
        return "index";
    }
}
