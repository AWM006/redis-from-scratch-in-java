package com.awm.redis.FirstCallPoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.awm.redis.codeValidator.Validator;

@RestController
public class FirstPoint {


    @Autowired
    public Validator validator; //@Component make the class store in spring head using @Autowired we can get the address of the class from heap

    @PostMapping("/")
    public String caller(@RequestBody(required = false) String body) {

        if (body == null || body.isBlank()) {
            return "";
        }

        return validator.validating(body.toLowerCase());
    }
}