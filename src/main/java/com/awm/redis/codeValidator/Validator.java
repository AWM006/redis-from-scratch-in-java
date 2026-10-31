package com.awm.redis.codeValidator;


import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import com.awm.redis.MethodSelector.SelectMethod;


public class Validator {

    public String validating(String argument){

        String encoded = argument;
        String decoded = URLDecoder.decode(encoded, StandardCharsets.UTF_8); //here string came like "hello+set+pp%3aAWM" this decode into "hello set pp:awm"
        StringBuilder sb = new StringBuilder();

        if(decoded.length() <= 6){
            return "no argument found";
        }
        
        int i=0;
        
        while(true){
            
            //get the first word
            if(decoded.charAt(i) != ' '){ 
                sb.append(decoded.charAt(i));
                i++;
                continue;
            }

            //after finish getting the first word check the word is "hello" or not if yes then selector method check which method to call
            if(sb.toString().equals("hello")){
                sb = null;
                return new SelectMethod().slector(decoded);
            } 
            else{
                break;
            }
            
        }

        return decoded+"\n has error at ->"+sb;
    }
}
