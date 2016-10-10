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
import app.ari.assignment1.helper.ContactHelper;
import app.ari.assignment1.models.Tweet;
import app.ari.assignment1.models.TweetList;
import app.ari.assignment1.models.User;

import static app.ari.assignment1.helper.ContactHelper.sendEmail;
/**
 * Created by ictskills on 27/09/16.
 */
public class Tweeter extends AppCompatActivity implements View.OnClickListener,TextWatcher{

    private TweetApp app;
    private Button selectContact;
    private Button emailTweet;
    public TextView date;
    public TextView counter;
    public int count = 140;
    public EditText tweetTweet;
    private String charLeftString;
    private TweetList tweetList;
    public Tweet editTweet;
    private static final int REQUEST_CONTACT = 1;
    private String emailAddress;
    private String tweetMessage;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweeter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        app = (TweetApp) getApplication();

        tweetList = app.tweetList;
        counter = (TextView) findViewById(R.id.counter);
        tweetTweet = (EditText) findViewById(R.id.tweetTweet);
        Button tweet = (Button)findViewById(R.id.tweetButton);
        selectContact = (Button)findViewById(R.id.selectContact);
        emailTweet = (Button)findViewById(R.id.emailTweet);

        user = app.currentUser;
        tweetTweet.addTextChangedListener(this);

        date = (TextView) findViewById(R.id.date);
        String today = new Date().toString();

        tweet.setOnClickListener(this);
        selectContact.setOnClickListener(this);
        emailTweet.setOnClickListener(this);

        date.setText(today);

        Long tweetId = (Long) getIntent().getExtras().getSerializable("Tweet_ID");
        editTweet = tweetList.getTweet(tweetId);
        if(tweet != null){
            updateDetails(editTweet);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        tweetList.saveTweets();
    }

    public void updateDetails(Tweet tweet){
        tweetTweet.setText(tweet.content);
        date.setText(tweet.date.toString());
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                if(editTweet != null) {
                    TweetList.tweets.remove(editTweet);
                }
                navigateUp(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        tweetMessage = this.tweetTweet.getText().toString();
        switch (view.getId()){
            case (R.id.tweetButton):
                Toast toast = Toast.makeText(Tweeter.this, "Tweet Sent", Toast.LENGTH_SHORT);
                toast.show();
                if(tweetMessage.equals("")){
                    TweetList.tweets.remove(editTweet);
                }
                else{
                    editTweet.content = tweetMessage;
                }
                startActivity(new Intent(this, Timeline.class));
                break;
            case (R.id.selectContact):
                selectContact(this, REQUEST_CONTACT);
                break;
            case (R.id.emailTweet):
                if(emailAddress == null) {
                    emailAddress = "ahwwwee@gmail.com";
                    sendEmail(this, emailAddress, user + " has sent you a tweet", tweetMessage);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode){
            case REQUEST_CONTACT:
                String name = ContactHelper.getContact(this, data);
                emailAddress = ContactHelper.getEmail(this, data);
                selectContact.setText(name + " : " + emailAddress);
                break;
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
