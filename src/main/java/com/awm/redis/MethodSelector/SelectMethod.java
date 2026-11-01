package com.awm.redis.MethodSelector;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.awm.redis.methods.*;
import com.awm.redis.store.storeData;

@Component
public class SelectMethod {


    @Autowired
    public storeData inventory;
    
    

    
    public String slector(String command){
        StringBuilder sb = new StringBuilder();
        
        for(int i=6; i<command.length(); i++){
            
            //break the command into single command
            if(command.charAt(i) != ' '){
                sb.append(command.charAt(i));
                continue;
            }

            //validating each command then insert into list
            if(command.charAt(i) == ' '){
                break;        
            }
        }
        
        if (sb.charAt(sb.length()-1) == '=') {
            sb.deleteCharAt(sb.length()-1);
        }


        switch(sb.toString()){
            
            case "ping":
                return new ping().method(command);
            case "set":
                return new set().method(command , inventory);
            case "get":
                return new get().method(command, inventory);
            case "exists":
                return new exists().method(command, inventory);
            case "del":
                return new del().method(command, inventory);
            case "ttl":
                return new ttl().method(command, inventory);
            default:
                return "no "+sb.toString()+" found";
        }


    }
}
