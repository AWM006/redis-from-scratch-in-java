package com.awm.redis.MethodSelector;

import java.util.ArrayList;
import com.awm.redis.methods.*;

public class SelectMethod {
    
    public String slector(String command){

        StringBuilder sb = new StringBuilder();
        ArrayList<String> values = new ArrayList<String>();

        String prev = "colon";

        for(int i=6; i<command.length(); i++){
            
            //break the command into single values
            if(command.charAt(i) != ' ' && command.charAt(i) != ':'){
                sb.append(command.charAt(i));
                continue;
            }

            //validating each values then insert into list
            if(command.charAt(i) == ' ' || command.charAt(i) == ':'){
              System.out.println(sb.toString()+" came");
                
                if(command.charAt(i) == ' ' && prev == "colon"){
                  System.out.println(sb.toString()+" came in space");
                    prev = "space";
                    values.add(sb.toString());
                    sb.setLength(0);
                }

                else if(command.charAt(i) == ':' && prev == "space"){
                  System.out.println(sb.toString()+" came in colon");
                        prev = "colon";
                        values.add(sb.toString());
                        sb.setLength(0);
                }
                else{
                  return sb.toString()+" is not valid key";
                }
            }
        } 




        if (sb.length() > 0) {           // add the last value
            sb.deleteCharAt(sb.length() - 1);
            values.add(sb.toString());
        }


        

        switch(values.get(0)){
            
            case "ping":
                return new ping().method(values);
            case "set":
                return new set().method(values);
            case "get":
                return new get().method(values);
            case "exists":
                return new exists().method(values);
            case "del":
                return new del().method(values);
            case "hset":
                return new hset().method(values);
            case "hget":
                return new hget().method(values);
            case "hgetall":
                return new hgetall().method(values);
            case "hexists":
                return new hexists().method(values);
            case "hdel":
                return new hdel().method(values);
            default:
                return "no "+values.get(0)+" found";
        }


    }
}
