package com.myapp.users.PhotoAppUsers.shared;

import lombok.*;

import java.io.Serializable;

@Getter @Setter
public class UserDto implements Serializable {
    private static final long serialVersionUID= 6913892342310497914L;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String userId;
    private String encryptedPassword;
}
