package app.ari.assignment1.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import app.ari.assignment1.R;

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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }
}
