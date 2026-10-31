package com.awm.redis.FirstCallPoint;

import org.springframework.web.bind.annotation.*;


@RestController
public class FirstPoint {
    
    @PostMapping("/")
    public String caller(@RequestBody(required = false) String body){

        if (body == null || body.isBlank()) {
            return "";
        }

        if (body.toLowerCase().contains("hello")) {
            return "hello world";
        }

        return "error";
    }

}
