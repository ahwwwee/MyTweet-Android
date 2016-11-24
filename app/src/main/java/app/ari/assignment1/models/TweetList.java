package app.ari.assignment1.models;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.ari.assignment1.helper.DbHelper;

/**
 * Created by Ari on 03/10/16.
 */
public class TweetList {
    public static ArrayList<Tweet> tweets;
    public DbHelper dbHelper;

    /**
     * constructor for a Tweetlist. for keeping track of all of all tweets.
     */
    public TweetList(Context context){
        try{
            dbHelper = new DbHelper(context);
            tweets = (ArrayList<Tweet>) dbHelper.selectTweets();
        } catch (Exception e) {
            tweets = new ArrayList<>();
        }
    }


    /**
     * adds a tweet to the tweets arraylist
     * @param tweet
     */
    public void addTweet(Tweet tweet){
        tweets.add(tweet);
        dbHelper.addTweet(tweet);
    }

    /**
     * finds the tweet in the arrayList
     * @param id
     * @return
     */
    public Tweet getTweet(String id){
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
        dbHelper.deleteTweet(tweet);
    }


    public void updateTweet(Tweet tweet) {
        dbHelper.updateTweet(tweet);
        updateLocalTweets(tweet);
    }

    public void refreshResidences(List<Tweet> tweets)
    {
        dbHelper.deleteTweets();
        this.tweets.clear();

        dbHelper.addTweets(tweets);

        for (int i = 0; i < tweets.size(); i += 1) {
            this.tweets.add(tweets.get(i));
        }
    }

    /**
     * Search the list of residences for argument residence
     * If found replace it with argument residence.
     * If not found just add the argument residence.
     *
     * @param tweet The Tweet object with which the list of tweets to be updated.
     */
    private void updateLocalTweets(Tweet tweet) {
        for (int i = 0; i < tweets.size(); i += 1) {
            Tweet t = tweets.get(i);
            if (t._id.equals(tweet._id)) {
                tweets.remove(i);
                tweets.add(tweet);
                return;
            }
        }
    }

}
