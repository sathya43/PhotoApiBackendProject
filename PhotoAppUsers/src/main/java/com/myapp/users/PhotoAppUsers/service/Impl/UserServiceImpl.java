package com.myapp.users.PhotoAppUsers.service.Impl;

import com.myapp.users.PhotoAppUsers.data.UserEntity;
import com.myapp.users.PhotoAppUsers.data.UserRepository;
import com.myapp.users.PhotoAppUsers.service.UserService;
import com.myapp.users.PhotoAppUsers.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    BCryptPasswordEncoder bCrypt;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCrypt){
        this.userRepository = userRepository;
        this.bCrypt = bCrypt;
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
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        UserEntity userEntity=userRepository.findByEmail(username);

        if(userEntity==null) throw new UsernameNotFoundException(username);

        return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),
                true,true,true,
                true,new ArrayList<>());
    }
}
