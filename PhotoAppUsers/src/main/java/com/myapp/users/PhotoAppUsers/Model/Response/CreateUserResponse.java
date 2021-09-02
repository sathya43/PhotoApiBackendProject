package com.myapp.users.PhotoAppUsers.Model.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
public class CreateUserResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String encryptedPassword;
}
