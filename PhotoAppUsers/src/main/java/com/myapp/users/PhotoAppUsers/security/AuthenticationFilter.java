package com.myapp.users.PhotoAppUsers.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.users.PhotoAppUsers.Model.Request.LoginRequestModel;
import com.myapp.users.PhotoAppUsers.service.UserService;
import com.myapp.users.PhotoAppUsers.shared.UserDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserService userService;
    private Environment environment;

    public AuthenticationFilter(UserService userService,
                                Environment environment,
                                AuthenticationManager authenticationManager){
        this.environment=environment;
        this.userService=userService;
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {
        try {
            LoginRequestModel creds = new ObjectMapper().
                    readValue(request.getInputStream(),LoginRequestModel.class);
            return getAuthenticationManager().authenticate(new
                    UsernamePasswordAuthenticationToken(creds.getEmail(),
                    creds.getPassword(),new ArrayList<>()));
        }catch (IOException e){
            throw new RuntimeException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult)
            throws IOException, ServletException {
        String username = ( (User) authResult.getPrincipal()).getUsername();
        UserDto userDetails =userService.getUserByEmail(username);

        String token = Jwts.builder()
                .setSubject(userDetails.getUserId())
                .setExpiration(
                        new Date( System.currentTimeMillis() +
                                Long.parseLong(environment.getProperty("token.expiration_date"))))
                .signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
                .compact();

        response.addHeader("userId",userDetails.getUserId());
        response.addHeader("token",token);
    }
}
