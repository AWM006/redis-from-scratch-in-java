package com.awm.redis.methods;


import java.util.ArrayList;

import com.awm.redis.store.storeData;
import com.awm.redis.codeToList.listmaker;


public class get {
        
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
            sb.append(response.get(i)+":"+value+"\n");
        }
        
        return sb.toString();
    }
}
