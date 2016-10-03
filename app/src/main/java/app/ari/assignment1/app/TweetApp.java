package app.ari.assignment1.app;

import android.app.Application;

import java.util.ArrayList;

import app.ari.assignment1.models.User;

/**
 * Created by ictskills on 27/09/16.
 */
public class TweetApp extends Application {
    public ArrayList<User> users = new ArrayList<>();

    @Override
    public void onCreate(){
        super.onCreate();
    }

    public void addUser(User user){
        users.add(user);
    }

    public boolean findByEmail(String email, String password){
        for(User u : users){
            if(email.equals(u.email) && password.equals(u.password)){
                return true;
            }
        }
        return false;
    }
}
