package com.example.chatwebsocketrabbitmqjwtrestapi.model;


import com.example.chatwebsocketrabbitmqjwtrestapi.dto.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name ="USER_CHAT")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="first_name", length = 20, nullable = false)
    private String firstName;

    @Column(name="last_name", length = 20, nullable = false)
    private String lastName;

    @Column(name="username", length = 20, nullable = false)
    private String username;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;


}
