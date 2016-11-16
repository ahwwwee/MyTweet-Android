package app.ari.assignment1.app;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import app.ari.assignment1.helper.DBhelper;
import app.ari.assignment1.models.Tweet;
import app.ari.assignment1.models.TweetListSerializer;
import app.ari.assignment1.models.User;
import app.ari.assignment1.models.TweetList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ari on 27/09/16.
 */
public class TweetApp extends Application {
    public List<User> users = new ArrayList<User>();
    public List<Tweet> tweets = new ArrayList<Tweet>();
    private static final String FILENAME = "portfolio.json";
    public TweetList tweetList;
    public User currentUser;
    protected static TweetApp app;
    public DBhelper DBhelper = null;

    public TweetService tweetService;
    public boolean tweetServiceAvailable = false;
    public String service_url  = "https://mytweet-ari.herokuapp.com/";


    /**
     * Loads these when the application is opened
     */
    @Override
    public void onCreate(){
        super.onCreate();
        DBhelper = new DBhelper(getApplicationContext());

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(service_url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        tweetService = retrofit.create(TweetService.class);

        TweetListSerializer serializer = new TweetListSerializer(this, FILENAME);
        tweetList = new TweetList(serializer);
        app = this;
    }

    /**
     * adds a user to the arraylist.
     * @param user
     */
    public void addUser(User user){ users.add(user); }

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
