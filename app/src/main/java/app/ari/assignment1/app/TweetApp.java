package app.ari.assignment1.app;

import android.app.Application;

import java.util.ArrayList;

import app.ari.assignment1.models.TweetListSerializer;
import app.ari.assignment1.models.User;
import app.ari.assignment1.models.TweetList;

/**
 * Created by ictskills on 27/09/16.
 */
public class TweetApp extends Application {
    public ArrayList<User> users = new ArrayList<>();
    private static final String FILENAME = "portfolio.json";
    public TweetList tweetList;
    public User currentUser;
    protected static TweetApp app;

    @Override
    public void onCreate(){
        super.onCreate();
        TweetListSerializer serializer = new TweetListSerializer(this, FILENAME);
        tweetList = new TweetList(serializer);
        app = this;
    }

    public void addUser(User user){
        users.add(user);
    }

    public boolean findByEmail(String email, String password){
        for(User u : users){
            if(email.equals(u.email) && password.equals(u.password)){
                currentUser = u;
                return true;
            }
        }
        return false;
    }

    public static TweetApp getApp(){
        return app;
    }

}
