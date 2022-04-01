package team5.risc.client.controller;

public class User {
    private String username;
    private String password;

    public User(String user, String pass){
        this.username = user;
        this.password = pass;
    }

    //Method to authenticate username, password
    public boolean auth(String user, String pass){
        return username.equals(user) && password.equals(pass);
    }

    //Method to match username
    public boolean match_name(String username){
        return username.equals(username);
    }
}
