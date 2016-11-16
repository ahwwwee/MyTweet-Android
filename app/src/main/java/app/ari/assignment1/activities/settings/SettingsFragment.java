package app.ari.assignment1.activities.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.Toast;

import app.ari.assignment1.R;
import app.ari.assignment1.activities.TweeterFragment;
import app.ari.assignment1.activities.TweeterPager;
import app.ari.assignment1.app.TweetApp;
import app.ari.assignment1.models.Tweet;
import app.ari.assignment1.models.TweetList;
import app.ari.assignment1.models.User;

import static app.ari.assignment1.helper.Helper.navigateUp;

/**
 * Created by Ari on 20/10/16.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener
{
    private SharedPreferences prefs;
    public EditTextPreference username;
    public EditTextPreference password;
    public User user;
    private TweetApp app;

    /**
     * Loads these when the activity is opened
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        setHasOptionsMenu(true);

        app = TweetApp.getApp();
        user = app.currentUser;
        username = (EditTextPreference) findPreference("username");
        password = (EditTextPreference) findPreference("password");

        PreferenceManager.setDefaultValues(getActivity(), R.xml.settings, true);

    }

    /**
     * When this view is started this method is invoked
     */
    @Override
    public void onStart()
    {
        super.onStart();
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    /**
     * when stopped this method is invoked
     */
    @Override
    public void onStop()
    {
        super.onStop();
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }

    /**
     * For menu Items, If the up button is pressed or if the settings in the menu is selected.
     * @param item
     * @return
     */
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
                i.putExtra(TweeterFragment.EXTRA_TWEET_ID, tweet._id);
                startActivityForResult(i, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * when text is changed inside one of the menu options the method is invoked
     * @param sharedPreferences
     * @param key
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        String newName = username.getText();
        String newPassword = password.getText();
        Toast toast;
        if(!newName.isEmpty()){
            user.firstName = newName;
            toast = Toast.makeText(getActivity(), "username changed to " + newName, Toast.LENGTH_SHORT);
            toast.show();
        }
        if(!newPassword.isEmpty()) {
            user.password = newPassword;
            toast = Toast.makeText(getActivity(), "username changed to " + newPassword, Toast.LENGTH_SHORT);
            toast.show();
        }
        else{
            toast = Toast.makeText(getActivity(), "You have changed a setting", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}