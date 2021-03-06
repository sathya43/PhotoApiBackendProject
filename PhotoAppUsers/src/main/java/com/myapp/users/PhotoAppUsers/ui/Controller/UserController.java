package com.myapp.users.PhotoAppUsers.ui.Controller;

import com.myapp.users.PhotoAppUsers.Model.Request.CreateUserRequest;
import com.myapp.users.PhotoAppUsers.Model.Response.CreateUserResponse;
import com.myapp.users.PhotoAppUsers.Model.Response.UserResponseModel;
import com.myapp.users.PhotoAppUsers.data.AkhilClient;
import com.myapp.users.PhotoAppUsers.service.UserService;
import com.myapp.users.PhotoAppUsers.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private Environment env;

    @Autowired
    private UserService userService;

//    @Autowired
//    private AkhilClient ak;
//
//    @GetMapping("/akhil")
//    public String getAkhil(){
//        return ak.getUser();
//    }

    @GetMapping(path = "/status/check")
    public String status(){

        return "working on port " + env.getProperty("local.server.port") + "With token expiration date = " + env.getProperty("token.expiration_date");
    }


    @GetMapping( path = "/{userId}",
            produces =
                    { MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<UserResponseModel> getUser( @PathVariable  String userId){

        UserDto userDto = userService.getUserById(userId);
        UserResponseModel returnValue = new ModelMapper().map(userDto,UserResponseModel.class);


        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }

    @PostMapping(
            produces =
                    { MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest userDetails){

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = modelMapper.map(userDetails,UserDto.class);
        UserDto createdUser=userService.createUser(userDto);

        CreateUserResponse returnUser = modelMapper.map(createdUser,CreateUserResponse.class);


        return ResponseEntity.status(HttpStatus.CREATED).body(returnUser);
    }
}
