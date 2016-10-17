package app.ari.assignment1.activities;

import java.util.Date;

import app.ari.assignment1.helper.ContactHelper;
import app.ari.assignment1.R;
import app.ari.assignment1.app.TweetApp;
import app.ari.assignment1.models.TweetList;
import app.ari.assignment1.models.Tweet;
import app.ari.assignment1.settings.SettingsActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static app.ari.assignment1.helper.ContactHelper.sendEmail;
import static app.ari.assignment1.helper.Helper.navigateUp;

/**
 * Created by ictskills on 10/10/16.
 */
public class TweeterFragment extends Fragment implements OnCheckedChangeListener, OnClickListener, TextWatcher {

    public static   final String  EXTRA_TWEET_ID = "TWEET_ID";
    private static  final int REQUEST_CONTACT = 1;
    public TextView date;
    public TextView counter;
    private Button tweetButton;
    private Button emailTweet;
    private Button selectContact;
    public EditText tweetTweet;
    private Tweet tweet;
    private TweetList tweetList;
    private String emailAddress = " ";
    public int count = 140;
    private String charLeftString;
    private String tweetMessage = " ";
    public TweetApp app;
    public String contactName;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        Long tweetId = (Long)getArguments().getSerializable(EXTRA_TWEET_ID);

        app = TweetApp.getApp();
        tweetList = app.tweetList;
        tweet = tweetList.getTweet(tweetId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        super.onCreateView(inflater,  parent, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_tweeter, parent, false);

        setHasOptionsMenu(true);

        counter = (TextView) v.findViewById(R.id.counter);

        date = (TextView) v.findViewById(R.id.date);
        String today = new Date().toString();
        date.setText(today);

        addListeners(v);
        if(tweet.content != null){
            updateControls(tweet);
        }

        return v;
    }
    public Tweet editTweet;
    private void addListeners(View v)
    {
        tweetButton = (Button) v.findViewById(R.id.tweetButton);
        selectContact = (Button) v.findViewById(R.id.selectContact);
        emailTweet = (Button) v.findViewById(R.id.emailTweet);
        tweetTweet = (EditText) v.findViewById(R.id.tweetTweet);

        tweetTweet.addTextChangedListener(this);
        tweetButton.setOnClickListener(this);
        emailTweet.setOnClickListener(this);
        selectContact.setOnClickListener(this);

    }

    public void updateControls(Tweet tweet)
    {
        tweetTweet.setText(tweet.content);
        date.setText(tweet.date.toString());
        tweetTweet.setEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        tweetMessage = this.tweetTweet.getText().toString();
        switch (item.getItemId())
        {
            case R.id.settings:
                if(tweetMessage.equals("") || tweet.content == null){
                    TweetList.tweets.remove(tweet);
                }
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            case android.R.id.home:
                if(tweetMessage.equals("") || tweet.content == null){
                    TweetList.tweets.remove(tweet);
                }
                navigateUp(getActivity());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        tweetList.saveTweets();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode != Activity.RESULT_OK)
        {
            return;
        }

        switch (requestCode)
        {
            case REQUEST_CONTACT:
                String name = ContactHelper.getContact(getActivity(), data);
                emailAddress = ContactHelper.getEmail(getActivity(), data);
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {}

    @Override
    public void afterTextChanged(Editable c)
    {
        int left = (count - c.length());
        charLeftString = Integer.toString(left);
        counter.setText(charLeftString);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_tweeter, menu);
    }

    @Override
    public void onClick(View v)
    {
        tweetMessage = this.tweetTweet.getText().toString();
        switch (v.getId()){
            case (R.id.tweetButton):
                Toast toast = Toast.makeText(getActivity(), "Tweet Sent", Toast.LENGTH_SHORT);
                toast.show();
                if(tweetMessage.equals("")){
                    TweetList.tweets.remove(tweet);
                }
                else{
                    tweet.content = tweetMessage;
                }
                startActivity(new Intent(getActivity(), Timeline.class));
                break;
            case (R.id.selectContact):
                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(i, REQUEST_CONTACT);
                selectContact.setText(emailAddress);
                break;
            case (R.id.emailTweet):
                if(emailAddress.equals("")){
                    Toast toasty = Toast.makeText(getActivity(), "No contact selected, Select contact!", Toast.LENGTH_SHORT);
                    toasty.show();
                }
                sendEmail(getActivity(), emailAddress, app.currentUser.firstName + " has sent you a tweet", tweetMessage);
                break;
        }
    }
}

