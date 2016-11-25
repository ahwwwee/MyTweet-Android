package app.ari.assignment1.app;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import app.ari.assignment1.TweetService;
import app.ari.assignment1.models.User;
import app.ari.assignment1.models.TweetList;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ari on 27/09/16.
 */
public class TweetApp extends Application {
    public TweetService tweetService;
    public boolean tweetServiceAvailable = false;
    public String service_url  = "https://mytweet-ari.herokuapp.com/";
    public List<User> users = new ArrayList<>();
    private static final String FILENAME = "TweetList.json";
    public TweetList tweetList;
    public User currentUser;
    protected static TweetApp app;

    /**
     * Loads these when the application is opened
     */
    @Override
    public void onCreate(){
        super.onCreate();
        tweetList = new TweetList(getApplicationContext());
        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(service_url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        tweetService = retrofit.create(TweetService.class);
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
