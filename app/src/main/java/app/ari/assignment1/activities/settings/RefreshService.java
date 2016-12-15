package app.ari.assignment1.activities.settings;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;


import java.io.IOException;
import java.util.List;

import app.ari.assignment1.TweetService;
import app.ari.assignment1.activities.Timeline;
import app.ari.assignment1.activities.TimelineFragment;
import app.ari.assignment1.app.TweetApp;
import app.ari.assignment1.models.Tweet;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ari on 15/12/16.
 */

public class RefreshService extends IntentService
{
    private String tag = "MyTweet";
    TweetApp app;

    public RefreshService()
    {
        super("RefreshService");
        app = TweetApp.getApp();
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        Intent localIntent = new Intent(TimelineFragment.BROADCAST_ACTION);
        Call<List<Tweet>> call = (Call<List<Tweet>>) app.tweetService.getFollowing(app.currentUser._id);
        try
        {
            Response<List<Tweet>> response = call.execute();
            app.tweetList.refreshTweets(response.body());
            LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
        }
        catch (IOException e)
        {

        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.i(tag, "RefreshService instance destroyed");
    }

}
