package app.ari.assignment1.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import app.ari.assignment1.App.TweetApp;
import app.ari.assignment1.R;

/**
 * Created by ictskills on 27/09/16.
 */
public class Tweeter extends AppCompatActivity {

    private TweetApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweeter);

        app = (TweetApp) getApplication();

        Button tweet = (Button)findViewById(R.id.tweetButton);
        Button selectContact = (Button)findViewById(R.id.selectContact);
        Button emailTweet = (Button)findViewById(R.id.emailTweet);

    }
}
