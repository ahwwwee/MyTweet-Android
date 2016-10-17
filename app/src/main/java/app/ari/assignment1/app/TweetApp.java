package app.ari.assignment1.app;

import android.app.Application;

import java.util.ArrayList;

import app.ari.assignment1.models.TweetListSerializer;
import app.ari.assignment1.models.User;
import app.ari.assignment1.models.TweetList;

/**
 * Created by Ari on 27/09/16.
 */
public class TweetApp extends Application {
    public ArrayList<User> users = new ArrayList<>();
    private static final String FILENAME = "portfolio.json";
    public TweetList tweetList;
    public User currentUser;
    protected static TweetApp app;

    /**
     * Loads these when the application is opened
     */
    @Override
    public void onCreate(){
        super.onCreate();
        TweetListSerializer serializer = new TweetListSerializer(this, FILENAME);
        tweetList = new TweetList(serializer);
        app = this;
    }

    /**
     * adds a user to the arraylist.
     * @param user
     */
    public void addUser(User user){
        users.add(user);
    }

    /**
     * verifying that a users email and password match
     * @param email
     * @param password
     * @return
     */
    public boolean findByEmail(String email, String password){
        for(User u : users){
            if(email.equals(u.email) && password.equals(u.password)){
                currentUser = u;
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return
     */
    public static TweetApp getApp(){
        return app;
    }

}
