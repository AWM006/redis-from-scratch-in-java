package com.awm.redis.MethodSelector;

import java.util.ArrayList;


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


        
        return values.toString();
    }
}
