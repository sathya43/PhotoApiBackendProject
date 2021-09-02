package com.myapp.users.PhotoAppUsers.Model.Request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



@Getter @Setter
public class CreateUserRequest {
    @NotNull(message = "First Name cannot be null")
    @Size(min = 2,max = 50,message = "Name should contian atleast two characters")
    private String firstName;

    @NotNull(message = "Last Name cannot be null")
    private String lastName;

    @NotNull(message = "Email cannot be null")
    @Email
    private String email;

    @NotNull(message = "Password Cannot be null")
    @Size(min = 5,max = 20,message = "Password should contain atleast 5 characters")
    private String password;
}
