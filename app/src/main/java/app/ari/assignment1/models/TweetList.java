package app.ari.assignment1.models;

import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Ari on 03/10/16.
 */
public class TweetList {
    public static ArrayList<Tweet> tweets;
    private TweetListSerializer serializer;

    /**
     * constructor for a Tweetlist. for keeping track of all of all tweets.
     * @param serializer
     */
    public TweetList(TweetListSerializer serializer){
        this.serializer = serializer;
        try{
            tweets = serializer.loadTweets();
        } catch (Exception e) {
            tweets = new ArrayList<>();
        }
    }

    /**
     * saving tweets, so that they will be available when the app is next opened
     * @return
     */
    public boolean saveTweets(){
        try{
            serializer.saveTweet(tweets);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * adds a tweet to the tweets arraylist
     * @param tweet
     */
    public static void addTweet(Tweet tweet){
        tweets.add(tweet);
    }

    /**
     * finds the tweet in the arrayList
     * @param id
     * @return
     */
    public Tweet getTweet(Long id){
        for(Tweet t : tweets){
            if(id.equals(t._id)){
                return t;
            }
        }
        return null;
    }

    /**
     * removes tweet from the arrayList
     * @param tweet
     */
    public void deleteTweet(Tweet tweet){
        tweets.remove(tweet);
        saveTweets();
    }

}
