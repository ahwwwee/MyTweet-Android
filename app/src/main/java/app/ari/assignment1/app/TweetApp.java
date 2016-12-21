package app.ari.assignment1.app;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;

import app.ari.assignment1.TweetService;
import app.ari.assignment1.TweetServiceOpen;
import app.ari.assignment1.activities.CameraActivity;
import app.ari.assignment1.models.Tweet;
import app.ari.assignment1.models.User;
import app.ari.assignment1.models.TweetList;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static app.ari.assignment1.helper.FileIOHelper.writeBitmap;

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
    public Tweet currentTweet;
    public CameraActivity camera;

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

    public Tweet nodeImageConvert(Tweet tweet){
        /*byte[] array = Base64.decode(tweet.picture.toString().getBytes(), Base64.DEFAULT);
        Bitmap decoded = BitmapFactory.decodeByteArray(array, 0, array.length);*/
        String filename = UUID.randomUUID().toString() + ".png";
        tweet.path = filename;
        /*Bitmap pic = tweet.picture;
        int size = pic.getHeight() * pic.getWidth();
        ByteBuffer buffer = ByteBuffer.allocate(size);
        pic.copyPixelsFromBuffer(buffer);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        pic.compress(Bitmap.CompressFormat.PNG, 50, bos);
        tweet.picture = pic;*/
        return tweet;
    }
}
