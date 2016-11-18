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
public class Welcome extends AppCompatActivity implements View.OnClickListener, Callback<List<User>> {

    private User user;
    private TweetApp app;


    /**
     * Loads these when the activity is opened
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button logButton = (Button) findViewById(R.id.login_button);
        Button signButton = (Button) findViewById(R.id.signup_button);

        app = TweetApp.getApp();
        Call<List<User>> call = (Call<List<User>>) app.tweetService.getAllUsers();
        call.enqueue(this);

        app.currentUser = null;
        logButton.setOnClickListener(this);
        signButton.setOnClickListener(this);
    }

    @Override
    public void onResume()
    {
        super.onResume();

    }

    public void onCreateView(View v){

    }

    /**
     * When a button is pressed this is invokes, the switch brings the user to the appropriate activity
     * @param view
     */
    @Override
    public void onClick(View view) {
       switch(view.getId()){
           case R.id.login_button:
               if(app.tweetServiceAvailable) {
                   startActivity(new Intent(this, Login.class));
                   break;
               }
               serviceUnavailableMessage();
               break;
           case R.id.signup_button:
               if(app.tweetServiceAvailable) {
                   startActivity(new Intent(this, Signup.class));
                   break;
               }
               serviceUnavailableMessage();
               break;
       }
    }

    @Override
    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
        serviceAvailableMessage();
        app.users = response.body();
        app.tweetServiceAvailable = true;
    }

    @Override
    public void onFailure(Call<List<User>> call, Throwable t) {
        app.tweetServiceAvailable = false;
        serviceUnavailableMessage();
    }

    void serviceUnavailableMessage()
    {
        Toast toast = Toast.makeText(this, "Tweet Service Unavailable. Try again later", Toast.LENGTH_LONG);
        toast.show();
    }

    void serviceAvailableMessage()
    {
        Toast toast = Toast.makeText(this, "Tweet Contacted Successfully", Toast.LENGTH_LONG);
        toast.show();
    }
}
