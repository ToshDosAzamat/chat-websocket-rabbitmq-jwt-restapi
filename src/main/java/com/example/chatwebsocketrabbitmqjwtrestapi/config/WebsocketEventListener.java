package com.example.chatwebsocketrabbitmqjwtrestapi.config;


import com.example.chatwebsocketrabbitmqjwtrestapi.dto.Status;
import com.example.chatwebsocketrabbitmqjwtrestapi.exception.NotFoundException;
import com.example.chatwebsocketrabbitmqjwtrestapi.model.User;
import com.example.chatwebsocketrabbitmqjwtrestapi.repository.UserRepository;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebsocketEventListener {
    private UserRepository userRepository;

    public WebsocketEventListener(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event){
        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = stompHeaderAccessor.getUser().getName();
        if(username != null){
            User user = userRepository.findByUsername(username).orElseThrow(
                    ()-> new NotFoundException("User not found!")
            );
            user.setStatus(Status.OFFLINE);
            userRepository.save(user);
        }
    }
}
