package app.ari.assignment1.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;

import app.ari.assignment1.R;
import app.ari.assignment1.app.TweetApp;
import app.ari.assignment1.models.Tweet;
import app.ari.assignment1.models.TweetList;

import static app.ari.assignment1.helper.CameraHelper.showPhoto;


/**
 * Created by ictskills on 14/12/16.
 */
public class GalleryActivity extends AppCompatActivity
{

    public static   final String  EXTRA_PHOTO_FILENAME = "org.wit.myrent.photo.filename";
    private ImageView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        photoView = (ImageView) findViewById(R.id.GalleryImage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        showPicture();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home  : onBackPressed();
                return true;
            default                 : return super.onOptionsItemSelected(item);
        }
    }

    private void showPicture()
    {
        String tweetId = (String)getIntent().getSerializableExtra(TweeterFragment.EXTRA_TWEET_ID);
        TweetApp app = (TweetApp) getApplication();
        TweetList tweetlist = app.tweetList;
        Tweet tweet = tweetlist.getTweet(tweetId);
        showPhoto(this, tweet,  photoView);
    }
}
