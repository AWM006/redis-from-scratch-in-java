package com.awm.redis.FirstCallPoint;

import org.springframework.web.bind.annotation.*;
import com.awm.redis.codeValidator.Validator;

@RestController
public class FirstPoint {
    
    @PostMapping("/")
    public String caller(@RequestBody(required = false) String body){

        if (body == null || body.isBlank()) {
            return "";
        }

        if (body.toLowerCase().contains("")) {

            Validator ValidOrNot = new Validator();
            String response = ValidOrNot.validating(body.toLowerCase());

            return response;
        }

        return "error";
    }

}
