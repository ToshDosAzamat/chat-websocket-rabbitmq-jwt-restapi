package com.example.chatwebsocketrabbitmqjwtrestapi.service.implement;


import com.example.chatwebsocketrabbitmqjwtrestapi.component.JwtTokenProvider;
import com.example.chatwebsocketrabbitmqjwtrestapi.dto.ChatUser;
import com.example.chatwebsocketrabbitmqjwtrestapi.dto.SignIn;
import com.example.chatwebsocketrabbitmqjwtrestapi.dto.SignUp;
import com.example.chatwebsocketrabbitmqjwtrestapi.dto.Status;
import com.example.chatwebsocketrabbitmqjwtrestapi.exception.NotFoundException;
import com.example.chatwebsocketrabbitmqjwtrestapi.model.Role;
import com.example.chatwebsocketrabbitmqjwtrestapi.model.User;
import com.example.chatwebsocketrabbitmqjwtrestapi.repository.RoleRepository;
import com.example.chatwebsocketrabbitmqjwtrestapi.repository.UserRepository;
import com.example.chatwebsocketrabbitmqjwtrestapi.service.AuthService;
import com.example.chatwebsocketrabbitmqjwtrestapi.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class AuthServiceImplement implements AuthService {
    @Value("${test.url}")
    private String testurl;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private EmailService emailService;
    private JwtTokenProvider jwtTokenProvider;

    public AuthServiceImplement(AuthenticationManager authenticationManager,
                                PasswordEncoder passwordEncoder, UserRepository userRepository,
                                RoleRepository roleRepository, EmailService emailService,
                                JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.emailService = emailService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public ChatUser signin(SignIn signIn) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                signIn.getEmail(), signIn.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByEmail(signIn.getEmail()).orElseThrow(
                ()-> new NotFoundException("User not found!")
        );
        String token = jwtTokenProvider.generateToken(user);
        emailService.sendEmail(
                user.getEmail(),
                "We have identified access to your account!",
                "You have successfully signed up. To visit the chat, enter using the link below: " +testurl+token);
        ChatUser chatUser = new ChatUser();
        chatUser.setToken(token);
        chatUser.setUsername(user.getUsername());
        return chatUser;
    }

    @Override
    public String signUp(SignUp singUp) {
        if(userRepository.findByUsernameOrEmail(singUp.getUsername(), singUp.getEmail()).isPresent()){
            throw  new NotFoundException("Enter other username or Email Already register!");
        }
        Set<Role> roleSet = new HashSet<>();
        Role role = roleRepository.findByName("USER").get();
        roleSet.add(role);
        User user = User.builder()
                .firstName(singUp.getFirstName())
                .lastName(singUp.getLastName())
                .username(singUp.getUsername())
                .email(singUp.getEmail())
                .password(passwordEncoder.encode(singUp.getPassword()))
                .status(Status.OFFLINE)
                .roles(roleSet)
                .build();
        userRepository.save(user);
        String token = jwtTokenProvider.generateToken(user);
        emailService.sendEmail(
                user.getEmail(),
                "We have identified access to your account!",
                "You have successfully signed up. To visit the chat, enter using the link below: " +testurl+token);
        return "We have sent your login address to your email!";
    }

    @Override
    public String update(SignUp singUp, String token) {
        if(
                jwtTokenProvider.validateToken(token) ||
                jwtTokenProvider.getUsername(token) != singUp.getUsername() ||
                !userRepository.findByUsernameOrEmail(singUp.getUsername(), singUp.getEmail()).isPresent()){
            throw new NotFoundException("Please enter in your account!");
        }
        Set<Role> roleSet = new HashSet<>();
        Role role = roleRepository.findByName("USER").get();
        roleSet.add(role);
        User user = User.builder()
                .firstName(singUp.getFirstName())
                .lastName(singUp.getLastName())
                .username(singUp.getUsername())
                .email(singUp.getEmail())
                .password(passwordEncoder.encode(singUp.getPassword()))
                .status(Status.OFFLINE)
                .roles(roleSet)
                .build();
        userRepository.save(user);
        String new_token = jwtTokenProvider.generateToken(user);
        emailService.sendEmail(
                user.getEmail(),
                "We have identified access to your account!",
                "You have successfully signed up. To visit the chat, enter using the link below: " +testurl+new_token);
        return "We have sent your login address to your email!";

    }

    @Override
    public String delete(String email, String token) {
        if(jwtTokenProvider.validateToken(token) ||
                        jwtTokenProvider.getUsername(token) != email ||
                        !userRepository.findByEmail(email).isPresent()){
            throw new NotFoundException("Please enter in your account!");
        }
        if(userRepository.deleteByEmail(email)){
            return "Deleting successful!";
        }else {
            return "Failed!";
        }
    }
}
