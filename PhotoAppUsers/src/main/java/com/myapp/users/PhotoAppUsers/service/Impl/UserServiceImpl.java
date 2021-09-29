package com.myapp.users.PhotoAppUsers.service.Impl;

import com.myapp.users.PhotoAppUsers.Model.Response.AlbumResponseModel;
import com.myapp.users.PhotoAppUsers.data.AlbumServiceClient;
import com.myapp.users.PhotoAppUsers.data.UserEntity;
import com.myapp.users.PhotoAppUsers.data.UserRepository;
import com.myapp.users.PhotoAppUsers.service.UserService;
import com.myapp.users.PhotoAppUsers.shared.UserDto;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    BCryptPasswordEncoder bCrypt;
    RestTemplate restTemplate;
    AlbumServiceClient albumServiceClient;
    Environment environment;


    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCrypt,
                           RestTemplate restTemplate,
                           Environment environment,
                           AlbumServiceClient albumServiceClient){
        this.userRepository = userRepository;
        this.bCrypt = bCrypt;
        this.restTemplate=restTemplate;
        this.environment=environment;
        this.albumServiceClient=albumServiceClient;
    }

    @Override
    public UserDto createUser( UserDto userDetails) {
    	userDetails.setUserId(UUID.randomUUID().toString());
        userDetails.setEncryptedPassword(bCrypt.encode(userDetails.getPassword()));

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(userDetails,UserEntity.class);
//        userEntity.setEncryptedPassword("test");
    //   userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));

        userRepository.save(userEntity);

        UserDto returnValue = modelMapper.map(userEntity,UserDto.class);

        return returnValue;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        UserEntity userEntity=userRepository.findByEmail(email);

        if(userEntity==null) throw new UsernameNotFoundException(email);
        return new ModelMapper().map(userEntity,UserDto.class);
    }

    @Override
    public UserDto getUserById(String userId) {

        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity == null) throw new UsernameNotFoundException("User not found");

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        logger.info(userId);

        /*
        String albumsUrl = String.format(environment.getProperty("albums.url"), userId);

        ResponseEntity<List<AlbumResponseModel>> albumsListResponse = restTemplate.exchange(albumsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<AlbumResponseModel>>() {
        });
        List<AlbumResponseModel> albumsList = albumsListResponse.getBody();
        */

   //     logger.info("Before calling albums Microservice");
//        List<AlbumResponseModel> albumsList = null;
//        try {
//            albumsList = albumServiceClient.getAlbums(userId);
//        } catch (FeignException e){
//            logger.error(e.getLocalizedMessage());
//        }
        logger.info("Before Calling Albums service");
        List<AlbumResponseModel> albumsList = albumServiceClient.getAlbums(userId);
        logger.info("After Calling Albums service");
       // logger.info("After calling albums Microservice");
      //  logger.info("{}",albumsList);
        // logger.info("{}",userDto);
        userDto.setAlbums(albumsList);
      //  logger.info("{}",userDto);
        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        UserEntity userEntity=userRepository.findByEmail(username);

        if(userEntity==null) throw new UsernameNotFoundException(username);

        return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),
                true,true,true,
                true,new ArrayList<>());
    }
}
