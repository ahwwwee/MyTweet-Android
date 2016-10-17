package app.ari.assignment1.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import app.ari.assignment1.R;

/**
 * Created by Ari on 27/09/16.
 */
public class Tweeter extends AppCompatActivity{

    public ActionBar actionBar;

    /**
     * Loads these when the activity is opened
     * Loads a new TweetetFragment where the functionality for the Tweeter page is.
     * @param savedInstanceState

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);
        actionBar = getSupportActionBar();


        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);
        if (fragment == null) {
            fragment = new TweeterFragment();
            manager.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
        }
    }*/
}
