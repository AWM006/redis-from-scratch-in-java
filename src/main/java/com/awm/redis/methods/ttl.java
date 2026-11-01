package com.awm.redis.methods;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.awm.redis.codeToList.listmaker;
import com.awm.redis.store.storeData;

public class ttl {
    
    public String method(String command, storeData inventory){

        ArrayList<String> response;
        ArrayList<String> removeItem = new ArrayList<String>();

        try{
            response = new listmaker().ttlMaker(command);
        }
        catch (IllegalArgumentException e) {
            return e.getMessage();
        }

        if(response.size() % 2 != 0){
            return "wrong data insertion in the end";
        }

        for (String item : response) {
            if (item.isBlank()) {
                return "remove extra spaces";
            }
        }
        

        for (int i = 2; i < response.size(); i+=2) {           
            inventory.put(response.get(i), response.get(i+1));
            removeItem.add(response.get(i));
        }


        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        int TTLtime = Integer.parseInt(response.get(1));
        

        scheduler.schedule(() -> {
            for (int i = 0; i < removeItem.size(); i++) {           
                inventory.delete(removeItem.get(i));
            }

        }, TTLtime, TimeUnit.SECONDS);
        


        return "ttl insertion complete";
    }

    
}
