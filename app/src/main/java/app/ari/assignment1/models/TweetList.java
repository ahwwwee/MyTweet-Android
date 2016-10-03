package app.ari.assignment1.models;

import java.util.ArrayList;

/**
 * Created by ictskills on 03/10/16.
 */
public class TweetList {
    public static ArrayList<Tweet> tweets;

    public TweetList(){
        tweets = new ArrayList<>();
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
}
