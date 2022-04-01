package team5.risc.client.controller;

import java.util.ArrayList;

public class Users {
    private ArrayList<User> users;
    
    public Users(){
        users = new ArrayList<User>();
    }

    //Method to register user
    public void add_user(String user, String pass) throws IllegalArgumentException{
        if(!is_unique(user)){
            throw new IllegalArgumentException("Username is not unique");
        }
        users.add(new User(user, pass));
    }

    //Method to check if username is unique
    private boolean is_unique(String username){
        for(User u : users){
            if(u.match_name(username)){
                return true;
            }
        }
        return false;
    }

    //Method to authenticate user
    public boolean authenticate(String username, String pass){
        for(User u : users){
            //Match username
            if(u.match_name(username)){
                //Authenticate user
                if(u.auth(username, pass)){
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}
