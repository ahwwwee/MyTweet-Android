package app.ari.assignment1.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import app.ari.assignment1.R;
import app.ari.assignment1.app.TweetApp;

/**
 * Created by Ari on 27/09/16.
 */
public class Welcome extends AppCompatActivity implements View.OnClickListener {

    /**
     * Loads these when the activity is opened
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TweetApp app = (TweetApp) getApplication();

        Button logButton = (Button) findViewById(R.id.login_button);
        Button signButton = (Button) findViewById(R.id.signup_button);

        logButton.setOnClickListener(this);
        signButton.setOnClickListener(this);
    }

    /**
     * When a button is pressed this is invokes, the switch brings the user to the appropriate activity
     * @param view
     */
    @Override
    public void onClick(View view) {
       switch(view.getId()){
           case R.id.login_button:
               startActivity(new Intent(this, Login.class));
               break;
           case R.id.signup_button:
               startActivity(new Intent(this, Signup.class));
               break;
       }
    }
}