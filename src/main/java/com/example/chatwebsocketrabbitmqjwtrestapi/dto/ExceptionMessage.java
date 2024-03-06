package com.example.chatwebsocketrabbitmqjwtrestapi.dto;


import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ExceptionMessage {
    private Date date;
    private String message;
}
