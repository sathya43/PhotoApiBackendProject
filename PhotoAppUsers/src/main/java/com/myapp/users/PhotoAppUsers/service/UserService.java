package com.myapp.users.PhotoAppUsers.service;

import com.myapp.users.PhotoAppUsers.shared.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDetails);
    UserDto getUserByEmail(String email);

}
