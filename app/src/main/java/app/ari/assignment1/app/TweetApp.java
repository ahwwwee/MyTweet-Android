package app.ari.assignment1.app;

import android.app.Application;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import app.ari.assignment1.TweetService;
import app.ari.assignment1.TweetServiceOpen;
import app.ari.assignment1.helper.RetrofitServiceFactory;
import app.ari.assignment1.models.Token;
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
    public TweetService tweetService;
    public TweetServiceOpen tweetServiceOpen;
    public boolean tweetServiceAvailable = false;
    public String service_url  = "https://mytweet-ari.herokuapp.com/";
    public List<User> users;
    private static final String FILENAME = "TweetList.json";
    public TweetList tweetList;
    public User currentUser;
    public User userCurrent;
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
        tweetServiceOpen = retrofit.create(TweetServiceOpen.class);

        app = this;
    }

    /**
     * adds a user to the arraylist.
     * @param user
     */
    public void addUser(User user){
        tweetList.addUser(user);
    }

    /**
     * verifying that a users email and password match
     * @param email
     * @param password
     * @return
     */
    public boolean verify(String email, String password){
        for(User u : tweetList.users){
            if(email.equals(u.email) && password.equals(u.password)){
                currentUser = u;
                return true;
            }
        }
        return false;
    }


    public User findByEmail(String email){
        for(User u: tweetList.users){
            if(email.equals(u.email)){
                return u;
            }
        }
        return null;
    }

    public User findById(String id){
        for(User u: tweetList.users){
            if(id.equals(u._id)){
                return u;
            }
        }
        return null;
    }

    /**
     *
     * @return
     */
    public static TweetApp getApp(){
        return app;
    }

    /*@Override
    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
        serviceAvailableMessage();
        for(User u : response.body()) {
            app.tweetList.addUser(u);
        }
        tweetServiceAvailable = true;
    }

    @Override
    public void onFailure(Call<List<User>> call, Throwable t) {
        serviceUnavailableMessage();
        tweetServiceAvailable = false;
    }*/

    public void serviceUnavailableMessage()
    {
        Toast toast = Toast.makeText(this, "Tweet Service Unavailable. Only local information available", Toast.LENGTH_LONG);
        toast.show();
    }

    public void serviceAvailableMessage()
    {
        Toast toast = Toast.makeText(this, "Tweet Contacted Successfully", Toast.LENGTH_LONG);
        toast.show();
    }
}
