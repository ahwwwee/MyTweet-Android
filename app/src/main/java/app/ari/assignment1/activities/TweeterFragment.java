package app.ari.assignment1.activities;

import java.util.Date;

import app.ari.assignment1.activities.settings.SettingsActivity;
import app.ari.assignment1.helper.ContactHelper;
import app.ari.assignment1.R;
import app.ari.assignment1.app.TweetApp;
import app.ari.assignment1.models.TweetList;
import app.ari.assignment1.models.Tweet;
import app.ari.assignment1.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
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

import static app.ari.assignment1.helper.CameraHelper.showPhoto;
import android.widget.ImageView;

import static app.ari.assignment1.helper.ContactHelper.sendEmail;
import static app.ari.assignment1.helper.Helper.navigateUp;

/**
 * Created by Ari on 10/10/16.
 */
public class TweeterFragment extends Fragment implements OnCheckedChangeListener, OnClickListener, TextWatcher, Callback<Tweet>, View.OnLongClickListener {

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
    private User user;
    TextView welcome;
    public TweetAdapter tweetAdapter;
    private static final int REQUEST_PHOTO = 0;
    public TweeterPager tweeterPager;
    public TweeterPager.PagerAdapter pagerAdapter;

    private ImageView cameraButton;
    private ImageView photoView;

    /**
     * Loads these when the activity is opened
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        String tweetId = (String)getArguments().getSerializable(EXTRA_TWEET_ID);

        app = TweetApp.getApp();
        user = app.currentUser;
        tweetList = app.tweetList;
        tweet = app.currentTweet;
        tweeterPager = TweeterPager.get();
        pagerAdapter = tweeterPager.pagerAdapter;
    }

    /**
     * Loads these when the activity is opened
     * loads the items on the page so that they are readable in other methods.
     * needs to be used when fragments are implemented
     * @param savedInstanceState
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        super.onCreateView(inflater,  parent, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_tweeter, parent, false);

        welcome = (TextView) v.findViewById(R.id.welcome);
        welcome.setText("Hey " + user.firstName);

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

    /**
     * adds listeners to buttons and textWatcher
     * @param v
     */
    private void addListeners(View v)
    {
        tweetButton = (Button) v.findViewById(R.id.tweetButton);
        selectContact = (Button) v.findViewById(R.id.selectContact);
        emailTweet = (Button) v.findViewById(R.id.emailTweet);
        tweetTweet = (EditText) v.findViewById(R.id.tweetTweet);
        cameraButton = (ImageView) v.findViewById(R.id.imageButton);
        photoView = (ImageView) v.findViewById(R.id.imageView);

        cameraButton.setOnClickListener(this);
        photoView.setOnLongClickListener(this);

        tweetTweet.addTextChangedListener(this);
        tweetButton.setOnClickListener(this);
        emailTweet.setOnClickListener(this);
        selectContact.setOnClickListener(this);

    }

    /**
     * If tweet an old tweet is being viewed on this page this method is invoked
     * @param tweet
     */
    public void updateControls(Tweet tweet)
    {
        tweetTweet.setText(tweet.content);
        date.setText(tweet.date);
        tweetTweet.setEnabled(false);
        if(tweet.picture != null){
            showPhoto(getActivity(), tweet, photoView);
        }
    }

    /**
     * For menu Items, If the up button is pressed or if the settings in the menu is selected.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        tweetMessage = this.tweetTweet.getText().toString();
        switch (item.getItemId())
        {
            case R.id.settings:
                if(tweetMessage.equals("") || tweet.content == null){
                    TweetList.tweets.remove(tweet);
                    pagerAdapter.notifyDataSetChanged();
                }
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            case android.R.id.home:
                if(tweetMessage.equals("") || tweet.content == null){
                    TweetList.tweets.remove(tweet);
                    pagerAdapter.notifyDataSetChanged();
                }
                navigateUp(getActivity());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * when the activity is exited the array in tweetlist is saved, making it persistant
     */
    @Override
    public void onPause()
    {
        super.onPause();
    }

    /**
     * a method to access the contacts for emailing a tweet.
     * @param requestCode
     * @param resultCode
     * @param data
     */
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
                selectContact.setText(emailAddress);
                break;
            case REQUEST_PHOTO:
                String filename = data.getStringExtra(CameraActivity.EXTRA_PHOTO_FILENAME);
                if (filename != null)
                {
                    tweet.picture = filename;
                    showPhoto(getActivity(), tweet, photoView );
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {}

    /**
     * a method taken from the TextWatcher, used to invoke the counter.
     * sets color as the counter goes below 10.
     * @param c
     */
    @Override
    public void afterTextChanged(Editable c)
    {
        int left = (count - c.length());
        charLeftString = Integer.toString(left);
        if(left > 10){
            counter.setTextColor(Color.parseColor("#161803"));
            counter.setText(charLeftString);
        }
        else if(left <= 10) {
            counter.setTextColor(Color.parseColor("#F6402C"));
            counter.setText(charLeftString);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
    }

    /**
     * populates menu on this activity
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_tweeter, menu);
    }

    /**
     * OnClickListener method
     * When a button is pressed this method is listening and will be invoked.
     * @param v
     */
    @Override
    public void onClick(View v)
    {
        tweetMessage = this.tweetTweet.getText().toString();
        switch (v.getId()){
            case (R.id.tweetButton):
                Toast toast = Toast.makeText(getActivity(), "Tweet Sent", Toast.LENGTH_SHORT);
                toast.show();
                if(tweetMessage.equals("")){
                    tweetList.deleteTweet(tweet);
                    pagerAdapter.notifyDataSetChanged();
                    Toast toasty = Toast.makeText(getActivity(), "Tweet must have some content", Toast.LENGTH_SHORT);
                    toasty.show();
                }
                else{
                    tweetList.deleteTweet(tweet);
                    pagerAdapter.notifyDataSetChanged();
                    tweet.content = tweetMessage;
                    if(tweet.picture != null){
                    }
                    tweet._id = null;
                    Call<Tweet> call = (Call<Tweet>) app.tweetService.createTweet(user._id, tweet);
                    call.enqueue(this);
                }
                startActivity(new Intent(getActivity(), Timeline.class));
                break;
            case (R.id.selectContact):
                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(i, REQUEST_CONTACT);
                break;
            case (R.id.emailTweet):
                if(emailAddress.equals("")){
                    Toast toasty = Toast.makeText(getActivity(), "No contact selected, Select contact!", Toast.LENGTH_SHORT);
                    toasty.show();
                }
                sendEmail(getActivity(), emailAddress, app.currentUser.firstName + " has sent you a tweet", tweetMessage);
                break;
            case R.id.imageButton:
                Intent ic = new Intent(getActivity(), CameraActivity.class);
                startActivityForResult(ic, REQUEST_PHOTO);
                break;
        }
    }

    @Override
    public void onResponse(Call<Tweet> call, Response<Tweet> response) {
        tweet = response.body();
        tweetList.addTweet(tweet);
        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(Call<Tweet> call, Throwable t) {
    }

    @Override
    public boolean onLongClick(View view) {
        Intent i = new Intent(getActivity(), GalleryActivity.class);
        i.putExtra(EXTRA_TWEET_ID, tweet._id);
        startActivity(i);
        return true;
    }
}

