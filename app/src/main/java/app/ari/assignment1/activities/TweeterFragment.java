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

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import android.support.v13.app.FragmentCompat;
import android.support.v4.content.ContextCompat;


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
    public String tweetId;
    Intent data;

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

        app = TweetApp.getApp();
        user = app.currentUser;
        tweetList = app.tweetList;
        if (getArguments() != null) {
            tweetId = (String) getArguments().getSerializable(EXTRA_TWEET_ID);
            tweet = tweetList.getTweet(tweetId);
        } else {
            tweet = app.currentTweet;
        }
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
        if(tweet.content.equals("")){
            tweetTweet.setText(tweet.path);
        }else {
            tweetTweet.setText(tweet.content);
        }
        date.setText(tweet.date);
        tweetTweet.setEnabled(false);
        if(tweet.path != null){
            showPhoto(getActivity(), tweet, photoView);
        }
        cameraButton.setVisibility(View.GONE);
        tweetButton.setVisibility(View.GONE);
        counter.setVisibility(View.GONE);
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
                if(!tweetMessage.equals("") || tweet.content != null || tweet.data != null) {
                    if(tweetId != null){
                        startActivity(new Intent(getActivity(), SettingsActivity.class));
                        return true;
                    }else {
                        TweetList.tweets.remove(tweet);
                    }
                } else {
                    TweetList.tweets.remove(tweet);
                }
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;
            case android.R.id.home:
                if(!tweetMessage.equals("") || tweet.content != null || tweet.data != null) {
                    if(tweetId != null){
                        navigateUp(getActivity());
                        return true;
                    }else {
                        TweetList.tweets.remove(tweet);
                    }
                } else {
                    TweetList.tweets.remove(tweet);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != Activity.RESULT_OK)
        {
            return;
        }
        switch (requestCode)
        {
            case REQUEST_CONTACT:
                this.data = data;
                checkContactsReadPermission();
                break;
            case REQUEST_PHOTO:
                String filename = data.getStringExtra(CameraActivity.EXTRA_PHOTO_FILENAME);
                if (filename != null)
                {
                    tweet.path = filename;
                    app.currentTweet = tweet;

                    showPhoto(getActivity(), tweet, photoView );
                }
                break;
        }
    }

    /**
     * http://stackoverflow.com/questions/32714787/android-m-permissions-onrequestpermissionsresult-not-being-called
     * This is an override of FragmentCompat.onRequestPermissionsResult
     *
     * @param requestCode Example REQUEST_CONTACT
     * @param permissions String array of permissions requested.
     * @param grantResults int array of results for permissions request.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CONTACT: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    readContact();
                }
                break;
            }
        }
    }

    private void readContact() {
        String name = ContactHelper.getContact(getActivity(), data);
        emailAddress = ContactHelper.getEmail(getActivity(), data);
        selectContact.setText(name + ": "+emailAddress);
    }

    /**
     * Bespoke method to check if read contacts permission exists.
     * If it exists then the contact sought is read.
     * Otherwise, the method FragmentCompat.request permissions is invoked and
     * The response is via the callback onRequestPermissionsResult.
     * In onRequestPermissionsResult, on successfully being granted permission then the sought contact is read.
     */
    private void checkContactsReadPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {

            readContact();
        }
        else {
            // Invoke callback to request user-granted permission
            FragmentCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    REQUEST_CONTACT);
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
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
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
    public void onClick(View v) {
        tweetMessage = this.tweetTweet.getText().toString();
        switch (v.getId()){
            case (R.id.tweetButton):
                Toast toast = Toast.makeText(getActivity(), "Tweet Sent", Toast.LENGTH_SHORT);
                toast.show();
                if(!tweetMessage.equals("") || app.currentTweet.data != null){
                    tweet = app.currentTweet;
                    tweetList.deleteTweet(tweet);
                    tweet.content = tweetMessage;
                    Call<Tweet> call = (Call<Tweet>) app.tweetService.createTweet(user._id, tweet);
                    call.enqueue(this);
                }
                else{
                    tweetList.deleteTweet(tweet);
                    pagerAdapter.notifyDataSetChanged();
                    Toast toasty = Toast.makeText(getActivity(), "Tweet must have some content", Toast.LENGTH_SHORT);
                    toasty.show();
                }
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
        if(response.body() != null) {
            tweet = response.body();
            tweetList.addTweet(tweet);
        }
        startActivity(new Intent(getActivity(), Timeline.class));

    }

    @Override
    public void onFailure(Call<Tweet> call, Throwable t) {
        Toast toasty = Toast.makeText(getActivity(), "an error occured", Toast.LENGTH_SHORT);
        toasty.show();
        startActivity(new Intent(getActivity(), Timeline.class));

    }

    @Override
    public boolean onLongClick(View view) {
        Intent i = new Intent(getActivity(), GalleryActivity.class);
        i.putExtra(EXTRA_TWEET_ID, tweet._id);
        startActivity(i);
        return true;
    }
}

