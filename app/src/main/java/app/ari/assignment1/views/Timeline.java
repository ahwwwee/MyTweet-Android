package app.ari.assignment1.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import app.ari.assignment1.R;

/**
 * Created by ictskills on 03/10/16.
 */
public class Timeline extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_tweeter, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.settings: Toast toast = Toast.makeText(Timeline.this, "Setting pressed", Toast.LENGTH_SHORT);
                toast.show();
                return true;
            case R.id.newTweet: startActivity(new Intent(this, Tweeter.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
