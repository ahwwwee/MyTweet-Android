package app.ari.assignment1.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import app.ari.assignment1.models.User;
import app.ari.assignment1.R;
import app.ari.assignment1.app.TweetApp;

/**
 * Created by ictskills on 27/09/16.
 */
public class Signup extends AppCompatActivity implements View.OnClickListener{

    EditText firstName;
    EditText lastName;
    EditText email;
    EditText password;
    private TweetApp app;

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

    @Override
    public void onClick(View view) {

        String FirstName = this.firstName.getText().toString();
        String LastName = this.lastName.getText().toString();
        String Email = this.email.getText().toString();
        String Password = this.password.getText().toString();

        User user = new User(FirstName, LastName, Email, Password);
        app.addUser(user);

        startActivity(new Intent(this, Login.class));
    }
}