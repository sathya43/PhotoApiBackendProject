package com.myapp.users.PhotoAppUsers.data;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity @Table(name = "users")
public class UserEntity implements Serializable {
    private static final long serialVersionUID= 8414097893037811755L;

    @Id @GeneratedValue
    private long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 120)
    private String email;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column( nullable = false, unique = true)
    private String encryptedPassword;
}
