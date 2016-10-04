package app.ari.assignment1.models;

import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ictskills on 03/10/16.
 */
public class TweetList {
    public static ArrayList<Tweet> tweets;
    private TweetListSerializer serializer;

    public TweetList(TweetListSerializer serializer){
        this.serializer = serializer;
        try{
            tweets = serializer.loadTweets();
        } catch (Exception e) {
            tweets = new ArrayList<>();
        }
    }

    public boolean saveTweets(){
        try{
            serializer.saveTweet(tweets);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public static void addTweet(Tweet tweet){
        tweets.add(tweet);
    }

    public Tweet getTweet(Long id){
        for(Tweet t : tweets){
            if(id.equals(t.id)){
                return t;
            }
        }
        return null;
    }

    public void deleteTweet(Tweet tweet){
        tweets.remove(tweet);
        saveTweets();
    }

}
