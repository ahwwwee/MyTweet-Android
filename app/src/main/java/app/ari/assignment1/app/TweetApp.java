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
public class TweetApp extends Application implements Callback<Token> {
    public TweetService tweetService;
    public TweetServiceOpen tweetServiceOpen;
    public boolean tweetServiceAvailable = false;
    public String service_url  = "https://mytweet-ari.herokuapp.com/";
    public List<User> users;
    private static final String FILENAME = "TweetList.json";
    public TweetList tweetList;
    public User currentUser;
    protected static TweetApp app;
    public boolean verified;

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
        /*Call<List<User>> call = (Call<List<User>>) tweetServiceOpen.getAllUsers();
        call.enqueue(this);*/
        users = tweetList.users;
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
    public boolean verify(String email, String password){
        for(User u : users){
            if(email.equals(u.email) && password.equals(u.password)){
                currentUser = u;
                Call<Token> call = (Call<Token>) tweetServiceOpen.auth(u);
                call.enqueue(this);
            }
        }
        return verified;
    }

    public void authenticate(String email, String password){
        for(User u : users){
            if(email.equals(u.email) && password.equals(u.password)){
                currentUser = u;
                Call<Token> call = (Call<Token>) tweetServiceOpen.auth(u);
                call.enqueue(this);
            }
        }
    }

    @Override
    public void onResponse(Call<Token> call, Response<Token> response) {
        tweetServiceAvailable = true;
        Token auth = response.body();
        currentUser = auth.user;
        verified = true;
        tweetService = RetrofitServiceFactory.createService(TweetService.class, auth.token);
        serviceAvailableMessage();

    }

    @Override
    public void onFailure(Call<Token> call, Throwable t) {
        tweetServiceAvailable = false;
        verified = false;
        serviceUnavailableMessage();


    }

    public User findByEmail(String email){
        for(User u: users){
            if(email.equals(u.email)){
                return u;
            }
        }
        return null;
    }

    public User findById(String id){
        for(User u: users){
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

    void serviceUnavailableMessage()
    {
        Toast toast = Toast.makeText(this, "Tweet Service Unavailable. Only local information available", Toast.LENGTH_LONG);
        toast.show();
    }

    void serviceAvailableMessage()
    {
        Toast toast = Toast.makeText(this, "Tweet Contacted Successfully", Toast.LENGTH_LONG);
        toast.show();
    }
}
