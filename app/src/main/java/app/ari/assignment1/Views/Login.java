package app.ari.assignment1.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import app.ari.assignment1.R;
import app.ari.assignment1.App.TweetApp;

/**
 * Created by ictskills on 27/09/16.
 */
public class Login extends AppCompatActivity implements View.OnClickListener{

    TextView email;
    TextView password;
    private TweetApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        app = (TweetApp) getApplication();

        email = (TextView) findViewById(R.id.emailLog);
        password = (TextView) findViewById(R.id.passwordLog);

        Button button = (Button) findViewById(R.id.signIn);

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String Email = this.email.getText().toString();
        String Password = this.password.getText().toString();
        if(app.findByEmail(Email, Password) == true) {
            startActivity(new Intent(this, Timeline.class));
        }
        else {
            Toast toast = Toast.makeText(Login.this, "Log in Failed", Toast.LENGTH_SHORT);
            toast.show();
            email.setText("");
            password.setText("");
        }
    }
}
