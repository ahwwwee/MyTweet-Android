package app.ari.assignment1.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import app.ari.assignment1.R;

/**
 * Created by ictskills on 16/12/16.
 */
public class TweetActivity extends AppCompatActivity {
    ActionBar actionBar;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);

        actionBar = getSupportActionBar();

        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);
        if (fragment == null)
        {
            fragment = new TweeterFragment();
            manager.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
        }
    }
}

