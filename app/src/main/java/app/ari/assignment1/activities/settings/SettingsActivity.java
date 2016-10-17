package app.ari.assignment1.activities.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import app.ari.assignment1.R;

/**
 * Created by Ari on 14/10/16.
 */
public class SettingsActivity extends AppCompatActivity
{
    /**
     * Loads these when the activity is opened
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            SettingsFragment fragment = new SettingsFragment();
            getFragmentManager().beginTransaction()
                    .add(android.R.id.content, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }
    }

    /**
     * populates menu on this activity
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

}
