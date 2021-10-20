package com.myapp.users.PhotoAppUsers.data;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "akhil-ws")
public interface AkhilClient {

    @GetMapping("/myService")
    public String getUser();
}
