package app.ari.assignment1.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Date;

import app.ari.assignment1.App.TweetApp;
import app.ari.assignment1.R;

/**
 * Created by ictskills on 27/09/16.
 */
public class Tweeter extends AppCompatActivity implements View.OnClickListener,TextWatcher{

    private TweetApp app;
    public TextView date;
    public TextView counter;
    public int count = 140;
    public EditText tweetTweet;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweeter);

        app = (TweetApp) getApplication();


        tweetTweet = (EditText) findViewById(R.id.tweetTweet);
        Button tweet = (Button)findViewById(R.id.tweetButton);
        Button selectContact = (Button)findViewById(R.id.selectContact);
        Button emailTweet = (Button)findViewById(R.id.emailTweet);

        TextView counter = (TextView)findViewById(R.id.counter);

        tweetTweet.addTextChangedListener(this);

        date = (TextView) findViewById(R.id.date);
        String today = new Date().toString();

        tweet.setOnClickListener(this);
        selectContact.setOnClickListener(this);
        emailTweet.setOnClickListener(this);

        date.setText(today);
        counter.setText("" + count);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case (R.id.tweetButton):
                Toast toast = Toast.makeText(Tweeter.this, "Message Sent", Toast.LENGTH_SHORT);
                toast.show();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence tweetTweet, int i, int i1, int i2) {
        if(tweetTweet.length() > 0){
            count -= tweetTweet.length();
            counter.setText("" + count);
        }
        return;
    }

    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable s){
        count -= s.length();
        counter.setText("" + count);
    }
}
