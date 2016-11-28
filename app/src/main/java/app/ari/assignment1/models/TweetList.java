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
    public static List<Tweet> tweets;
    public static List<User> users;
    public DbHelper dbHelper;

    /**
     * constructor for a Tweetlist. for keeping track of all of all tweets.
     */
    public TweetList(Context context){
        try{
            dbHelper = new DbHelper(context);
            tweets = dbHelper.selectTweets();
            users = (List<User>) dbHelper.selectUsers();
        } catch (Exception e) {
            tweets = new ArrayList<>();
        }
    }


    /**
     * adds a tweet to the tweets arraylist
     * @param tweet
     */
    public void addTweet(Tweet tweet){
        if (tweets.size() !=0) {
            for(Tweet t : tweets) {
                if (t._id.equals(tweet._id)) {
                    tweets.remove(t);
                    dbHelper.deleteTweet(tweet);
                    tweets.add(tweet);
                    dbHelper.addTweet(tweet);
                    return;
                }else{
                    tweets.add(tweet);
                    dbHelper.addTweet(tweet);
                    return;
                }
            }
        } else {
            tweets.add(tweet);
            dbHelper.addTweet(tweet);
            return;
        }
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

    public void deleteTweets(){
        dbHelper.deleteTweets();
        this.tweets.clear();
    }

    public void addUser(User user) {
        if (users.size() !=0) {
            for(User u : users){
                if (u._id.equals(user._id)) {
                    users.remove(u);
                    dbHelper.deleteUser(user);
                    users.add(user);
                    dbHelper.addUser(user);
                    return;
                }else{
                    users.add(user);
                    dbHelper.addUser(user);
                    return;
                }
            }
        }else{
            users.add(user);
            dbHelper.addUser(user);
            return;
        }
    }

    public void updateTweet(Tweet tweet) {
        dbHelper.updateTweet(tweet);
        updateLocalTweets(tweet);
    }

    public void refreshTweets(List<Tweet> tweets)
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
            if (t._id.equals(tweet._id) && tweet._id != "123") {
                tweets.remove(i);
                tweets.add(tweet);
                return;
            } else {
                tweets.add(tweet);
            }
        }
    }

}
