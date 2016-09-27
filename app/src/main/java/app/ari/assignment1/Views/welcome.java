package app.ari.assignment1.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import app.ari.assignment1.R;

public class welcome extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button logButton = (Button) findViewById(R.id.login_button);
        Button signButton = (Button) findViewById(R.id.signup_button);

        logButton.setOnClickListener(this);
        signButton.setOnClickListener(this);
    }

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
