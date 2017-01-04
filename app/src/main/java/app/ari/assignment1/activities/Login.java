package app.ari.assignment1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.ari.assignment1.R;
import app.ari.assignment1.TweetService;
import app.ari.assignment1.TweetServiceOpen;
import app.ari.assignment1.app.TweetApp;
import app.ari.assignment1.helper.RetrofitServiceFactory;
import app.ari.assignment1.models.Token;
import app.ari.assignment1.models.Tweet;
import app.ari.assignment1.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ari on 27/09/16.
 */
public class Login extends AppCompatActivity implements View.OnClickListener, Callback<Token>{

    private TextView email;
    private TextView password;
    private TweetApp app;
    private TweetServiceOpen tweetServiceOpen;
    private TweetService tweetService;
    String Password;
    String Email;
    User user = null;

    /**
     * Loads these when the activity is opened
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        app = (TweetApp) getApplication();

        email = (TextView) findViewById(R.id.emailLog);
        password = (TextView) findViewById(R.id.passwordLog);
        app.currentUser = null;


        Button button = (Button) findViewById(R.id.signIn);

        button.setOnClickListener(this);
    }

    /**
     * OnClickListener method
     * When a button is pressed this method is listening and will be invoked.
     * @param view
     */
    @Override
    public void onClick(View view) {
        Email = this.email.getText().toString();
        Password = this.password.getText().toString();
        user = new User(null, null, Email, Password, 600000, 10);
        if (user != null) {
            Call<Token> call = (Call<Token>) app.tweetServiceOpen.auth(user);
            call.enqueue(this);
        }
    }

    @Override
    public void onResponse(Call<Token> call, Response<Token> response) {
        app.tweetServiceAvailable = true;
        Token auth = response.body();
        user = auth.user;
        if(auth != null && auth.success != false) {
            app.tweetList.addUser(user);
            app.currentUser = user;
            app.userCurrent = auth.user;
            if(user.limit > 0){
                app.tweetList.tweetLimit = user.limit;
            } else {
                app.currentUser.limit = 10;
                app.tweetList.addUser(user);
                app.tweetList.tweetLimit = 10;
            }
            app.tweetService = RetrofitServiceFactory.createService(TweetService.class, auth.token);
            app.serviceAvailableMessage();
            startActivity(new Intent(this, Timeline.class));
        } else {
            if (app.verify(Email, Password)) {
                app.tweetServiceAvailable = false;
                app.serviceUnavailableMessage();
                app.currentUser = user;
                app.tweetList.addUser(user);
                startActivity(new Intent(this, Timeline.class));
            } else {
                Toast toast = Toast.makeText(Login.this, "Log in Failed", Toast.LENGTH_SHORT);
                toast.show();
                email.setText("");
                password.setText("");
            }
        }
    }

    @Override
    public void onFailure(Call<Token> call, Throwable t) {
        app.tweetServiceAvailable = false;
        Email = this.email.getText().toString();
        Password = this.password.getText().toString();
        if (app.verify(Email, Password) == true) {
            app.currentUser = user;
            app.tweetList.addUser(user);
            startActivity(new Intent(this, Timeline.class));
        } else {
            Toast toast = Toast.makeText(Login.this, "Log in Failed", Toast.LENGTH_SHORT);
            toast.show();
            email.setText("");
            password.setText("");
        }
    }
}
