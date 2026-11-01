package com.awm.redis.methods;

import java.util.ArrayList;

import com.awm.redis.codeToList.listmaker;
import com.awm.redis.store.storeData;

public class exists {
        
    public String method(String command, storeData inventory){

        ArrayList<String> response;
        StringBuilder sb = new StringBuilder();
        String value;

        try{
            response = new listmaker().makerForViewingOrDeletion(command);
        }
        catch (IllegalArgumentException e) {
            return e.getMessage();
        }
        
        for (int i = 1; i < response.size(); i++) {
            
            value = inventory.get(response.get(i));
            
            if(value != null){
                sb.append(response.get(i)+": 1\n");
            }
            else{
                sb.append(response.get(i)+": 0\n");
            }
        }
        
        return sb.toString();
    }
}
