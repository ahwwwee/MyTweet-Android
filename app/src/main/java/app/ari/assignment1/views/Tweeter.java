package app.ari.assignment1.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static app.ari.assignment1.helper.Helper.*;

import java.util.Date;

import app.ari.assignment1.app.TweetApp;
import app.ari.assignment1.R;
import app.ari.assignment1.models.Tweet;
import app.ari.assignment1.models.TweetList;

/**
 * Created by ictskills on 27/09/16.
 */
public class Tweeter extends AppCompatActivity implements View.OnClickListener,TextWatcher{

    private TweetApp app;
    public TextView date;
    public TextView counter;
    public int count = 140;
    public EditText tweetTweet;
    private String charLeftString;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweeter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        app = (TweetApp) getApplication();

        counter = (TextView) findViewById(R.id.counter);
        tweetTweet = (EditText) findViewById(R.id.tweetTweet);
        Button tweet = (Button)findViewById(R.id.tweetButton);
        Button selectContact = (Button)findViewById(R.id.selectContact);
        Button emailTweet = (Button)findViewById(R.id.emailTweet);

        tweetTweet.addTextChangedListener(this);

        date = (TextView) findViewById(R.id.date);
        String today = new Date().toString();

        tweet.setOnClickListener(this);
        selectContact.setOnClickListener(this);
        emailTweet.setOnClickListener(this);

        date.setText(today);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                navigateUp(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case (R.id.tweetButton):
                Toast toast = Toast.makeText(Tweeter.this, "Message Sent", Toast.LENGTH_SHORT);
                toast.show();
                String tweetMessage = this.tweetTweet.getText().toString();
                Tweet tweet = new Tweet(tweetMessage);
                TweetList.addTweet(tweet);
                startActivity(new Intent(this, Timeline.class));
        }
    }

    @Override
    public void beforeTextChanged(CharSequence tweetTweet, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable s){
        int left = (count - s.length());
        charLeftString = Integer.toString(left);
        counter.setText(charLeftString);
    }
}
