package com.example.PhotoAppResetPassword.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/account")
public class AccountController {

    @GetMapping(path = "/status/check")
    public String status(){
        return "It is working";
    }
}
