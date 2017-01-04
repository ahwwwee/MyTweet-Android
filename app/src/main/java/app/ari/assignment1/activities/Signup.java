package app.ari.assignment1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.ari.assignment1.models.Tweet;
import app.ari.assignment1.models.User;
import app.ari.assignment1.R;
import app.ari.assignment1.app.TweetApp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ari on 27/09/16.
 */
public class Signup extends AppCompatActivity implements View.OnClickListener, Callback<User> {

    EditText firstName;
    EditText lastName;
    EditText email;
    EditText password;
    private TweetApp app;
    User user;

    /**
     * Loads these when the activity is opened
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        app = (TweetApp) getApplication();

        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        Button button = (Button) findViewById(R.id.register);

        button.setOnClickListener(this);
    }

    /**
     * OnClickListener method
     * When a button is pressed this method is listening and will be invoked.
     * @param view
     */
    @Override
    public void onClick(View view) {

        String FirstName = this.firstName.getText().toString();
        String LastName = this.lastName.getText().toString();
        String Email = this.email.getText().toString();
        String Password = this.password.getText().toString();

        user = new User(FirstName, LastName, Email, Password, 600000, 10);

        Call<User> call= (Call<User>)app.tweetServiceOpen.createUser(user);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        user._id = response.body()._id;
        app.addUser(user);
        startActivity(new Intent(this, Login.class));
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        app.tweetServiceAvailable = false;
        Toast toast = Toast.makeText(this, "Tweet Service Unavailable. Try again later", Toast.LENGTH_LONG);
        toast.show();
        startActivity (new Intent(this, Welcome.class));
    }
}
