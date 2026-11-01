package com.awm.redis.codeToList;

import java.util.ArrayList;

public class listmaker {

    public ArrayList<String> makerForInsertion(String command){
        
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
                
                if(command.charAt(i) == ' ' && prev == "colon"){
                    prev = "space";
                    values.add(sb.toString());
                    sb.setLength(0);
                }

                else if(command.charAt(i) == ':' && prev == "space"){
                        prev = "colon";
                        values.add(sb.toString());
                        sb.setLength(0);
                }
                else{
                  throw new IllegalArgumentException(sb + " is not valid key");
                }
            }
        } 

        if (sb.length() > 0) {           // add the last value
            sb.deleteCharAt(sb.length() - 1);
            values.add(sb.toString());
        }

        return values;
    }
    
    


    public ArrayList<String> makerForViewingOrDeletion(String command){
            StringBuilder sb = new StringBuilder();
            ArrayList<String> values = new ArrayList<String>();


            //break the command into single values
            for(int i=6; i<command.length(); i++){
                if(command.charAt(i) != ' ' && command.charAt(i) != ':'){
                    sb.append(command.charAt(i));
                    continue;
                }
            
                if(command.charAt(i) == ' ' || command.charAt(i) == ':' ){
                    if(command.charAt(i) == ' '){
                      values.add(sb.toString());
                      sb.setLength(0);
                    }
                    else if(command.charAt(i) == ':'){
                      throw new IllegalArgumentException(sb+": is not valid key");
                    }
                
                }
            }
        
            if (sb.length() > 0) {
            
                  sb.deleteCharAt(sb.length() - 1);
                  values.add(sb.toString());
            }
        
        
            return values;
    }
}
