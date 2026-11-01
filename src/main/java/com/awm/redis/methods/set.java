package com.awm.redis.methods;

import java.util.ArrayList;

import com.awm.redis.codeToList.listmaker;
import com.awm.redis.store.storeData;

public class set {
    
    public String method(String command , storeData inventory){

        ArrayList<String> response;


        //making the list
        try{
            response = new listmaker().makerForInsertion(command);
        }
        catch (IllegalArgumentException e) {
            return e.getMessage();
        }

        //check array size odd or not
        if(response.size() % 2 == 0){
            return "wrong data insertion in the end";
        }





        //put values
        for (int i = 1; i < response.size(); i+=2) {
            
            inventory.put(response.get(i), response.get(i+1));
        } 



        return "insertion done";
    }
}
