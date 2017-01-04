package app.ari.assignment1.activities;

import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import app.ari.assignment1.R;
import app.ari.assignment1.app.TweetApp;
import app.ari.assignment1.models.Tweet;
import app.ari.assignment1.models.TweetList;

/**
 * Created by Ari on 10/14/2016.
 */
public class TweeterPager extends AppCompatActivity {

    public PagerAdapter pagerAdapter;
    private List<Tweet> tweets;
    private TweetList tweetList;
    private ViewPager viewPager;
    private static TweeterPager pager;

    /**
     * Loads these when the activity is opened
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        viewPager = new ViewPager(this);
        viewPager.setId(R.id.viewPager);
        setContentView(viewPager);

        pager = this;

        setTweetList();
        pagerAdapter = new PagerAdapter(getFragmentManager(), tweets);
        viewPager.setAdapter(pagerAdapter);

        setCurrentItem();
    }

    private void setTweetList()
    {
        TweetApp app = (TweetApp) getApplication();
        tweetList = app.tweetList;
        tweets = tweetList.tweets;
    }

    private void setCurrentItem() {
        String tweetId = (String) getIntent().getSerializableExtra(TweeterFragment.EXTRA_TWEET_ID);
        for (int i = 0; i < tweets.size(); i++) {
            if (tweets.get(i)._id.equals(tweetId)) {
                viewPager.setCurrentItem(i);
                break;
            }
        }
    }

    /**
     * populated the pagerAdapter which allows the user to scroll through the tweets
     */
    class PagerAdapter extends FragmentStatePagerAdapter
    {
        private List<Tweet>  tweets;

        public PagerAdapter(FragmentManager fm, List<Tweet> tweets)
        {
            super(fm);
            this.tweets = tweets;
        }

        @Override
        public int getCount()
        {
            return tweets.size();
        }

        @Override
        public Fragment getItem(int pos)
        {
            Tweet tweet = tweets.get(pos);
            Bundle args = new Bundle();
            args.putSerializable(TweeterFragment.EXTRA_TWEET_ID, tweet._id);
            TweeterFragment fragment = new TweeterFragment();
            fragment.setArguments(args);
            return fragment;
        }
    }
}
