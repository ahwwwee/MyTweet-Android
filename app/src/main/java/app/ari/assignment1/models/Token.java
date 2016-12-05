package app.ari.assignment1.models;

/**
 * Created by ictskills on 01/12/16.
 */
public class Token {

    public boolean success;
    public String token;
    public User user;

    public Token(boolean success, String token){
        this.success = success;
        this.token = token;
    }
}
