package app.ari.assignment1.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import app.ari.assignment1.R;
import app.ari.assignment1.app.TweetApp;
import app.ari.assignment1.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ari on 27/09/16.
 */
public class Welcome extends AppCompatActivity implements View.OnClickListener{

    private User user;
    public TweetApp app;

    /**
     * Loads these when the activity is opened
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        app = TweetApp.getApp();
        Button logButton = (Button) findViewById(R.id.login_button);
        Button signButton = (Button) findViewById(R.id.signup_button);

        logButton.setOnClickListener(this);
        signButton.setOnClickListener(this);
    }

    public void onCreateView(View v){

    }

    @Override
    public void onResume(){
        super.onResume();
        app.currentUser = null;
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
