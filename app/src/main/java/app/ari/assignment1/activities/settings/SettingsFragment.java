package app.ari.assignment1.activities.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import app.ari.assignment1.R;
import app.ari.assignment1.activities.TweeterFragment;
import app.ari.assignment1.activities.TweeterPager;
import app.ari.assignment1.models.Tweet;
import app.ari.assignment1.models.TweetList;

import static app.ari.assignment1.helper.Helper.navigateUp;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener
{
    private SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                navigateUp(getActivity());
                return true;
            case R.id.newTweet:
                Tweet tweet = new Tweet();
                TweetList.addTweet(tweet);
                Intent i = new Intent(getActivity(), TweeterPager.class);
                i.putExtra(TweeterFragment.EXTRA_TWEET_ID, tweet.id);
                startActivityForResult(i, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }
}
