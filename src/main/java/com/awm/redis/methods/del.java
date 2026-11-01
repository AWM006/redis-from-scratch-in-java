package com.awm.redis.methods;


import java.util.ArrayList;

import com.awm.redis.codeToList.listmaker;
import com.awm.redis.store.storeData;

public class del {
        
    public String method(String command, storeData inventory){

        ArrayList<String> response;
        StringBuilder sb = new StringBuilder();

        try{
           response = new listmaker().makerForViewingOrDeletion(command);
        }
        catch (IllegalArgumentException e) {
            return e.getMessage();
        }


        for (int i = 1; i < response.size(); i++) {
            
            inventory.delete(response.get(i));
        } 

        return "deletion complete";
    }
}
