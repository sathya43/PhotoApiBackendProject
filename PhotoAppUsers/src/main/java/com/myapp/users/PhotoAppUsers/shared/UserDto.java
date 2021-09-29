package com.myapp.users.PhotoAppUsers.shared;

import com.myapp.users.PhotoAppUsers.Model.Response.AlbumResponseModel;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter @Setter @Data @NoArgsConstructor
public class UserDto implements Serializable {
    private static final long serialVersionUID= 6913892342310497914L;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String userId;
    private String encryptedPassword;
    private List<AlbumResponseModel> albums;
}
