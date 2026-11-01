package com.awm.redis.codeToList;

import java.util.ArrayList;

import com.awm.redis.methods.ttl;

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

                else if(command.charAt(i) == ' ') {
                  throw new IllegalArgumentException("remove extra spaces");
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
        
            if(sb.length() > 0) {            
                  sb.deleteCharAt(sb.length() - 1);
                  values.add(sb.toString());
            }
        
        
            return values;
    }









    public ArrayList<String> ttlMaker(String command){



      ArrayList<String> list = new ArrayList<String>();
      StringBuilder sb = new StringBuilder();
      
      int count = 0;
      int i = 6; 

      while(count < 2){

        if(command.charAt(i) != ' ' ){
          if(command.charAt(i) == ':'){
            throw new IllegalArgumentException(": is not an argument");
          }
          sb.append(command.charAt(i));
          i++;
        }

        if(command.charAt(i) == ' '){
          list.add(sb.toString());
          sb.setLength(0);
          i++;
          count++;
        }
        
      }




      sb.setLength(0);
      boolean colon = false;
      boolean space = true;

      for(int j=i; j<command.length(); j++){
        
            if(command.charAt(j) != ' ' && command.charAt(j) != ':'){
              sb.append(command.charAt(j));
            }

            if(command.charAt(j)== ' ' && sb.toString() != " "){
              if(space == false && colon == true){
                list.add(sb.toString());
                sb.setLength(0);
                space = true;
                colon = false;
              }
              else if(command.charAt(j) == ' '){
                    throw new IllegalArgumentException("remove extra spaces");
                }
              else{
                    throw new IllegalArgumentException(sb.toString()+" invalid argument");
                }
            }

            if(command.charAt(j) == ':'){
              if(space == true && colon == false && sb.toString() != " "){
                    list.add(sb.toString());
                    sb.setLength(0);
                    space = false;
                    colon = true;
                }
                else if(command.charAt(j) == ' '){
                    throw new IllegalArgumentException("remove extra spaces");
                }
                else{
                    throw new IllegalArgumentException(sb.toString()+" invalid argument");
                }
            }


        } 
      if(sb.length() > 0) {            
          sb.deleteCharAt(sb.length() - 1);
          list.add(sb.toString());
      }

      return list;
    }
}
